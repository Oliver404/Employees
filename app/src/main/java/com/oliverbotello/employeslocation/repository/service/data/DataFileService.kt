package com.oliverbotello.employeslocation.repository.service.data

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.oliverbotello.employeslocation.entities.Employee
import com.oliverbotello.employeslocation.utils.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileReader

class DataFileService : DataService() {
    companion object {
        private var service: IDataFileAPI? = null
    }

    init {
        if (service == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            service = retrofit.create(IDataFileAPI::class.java)
        }
    }

    override fun getEmployees() {
        val callback: Callback<JsonObject> = object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                val data: JsonObject? = response.body()

                if (data != null) {
                    val url: String = data.getAsJsonObject("data").get("file").asString
                    this@DataFileService.downloadFile(url)
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                this@DataFileService.dataListener?.onFailedGetData(ERROR.DOWNLOAD_DATA_EXCEPTION)
            }
        }

        DataFileService.service?.getFileURL()?.enqueue(callback)
    }

    private fun downloadFile(url: String): Unit {
        if (!File(APP_PATH + DATA_ZIP_FILE_NAME).exists()) {
            val callback: Callback<ResponseBody> = object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful && response.body() != null) {
                        val writtenFile: Boolean = writeFileToDisk(response.body()!!)

                        if (writtenFile)
                            this@DataFileService.extractData()
                        else
                            this@DataFileService.dataListener?.onFailedGetData(ERROR.DOWNLOAD_DATA_EXCEPTION)
                    }
                    else
                        this@DataFileService.dataListener?.onFailedGetData(ERROR.DOWNLOAD_DATA_EXCEPTION)
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    this@DataFileService.dataListener?.onFailedGetData(ERROR.DOWNLOAD_DATA_EXCEPTION)
                }
            }

            DataFileService.service?.downloadFile(url)?.enqueue(callback)
        }
        else
            this.extractData()
    }

    private fun extractData() {
        val pathFile: String = APP_PATH + DATA_EMPLOYEES_FILE
        var isDataFile: Boolean =
            if (File(pathFile).exists())
                true
            else
                unzipFile(APP_PATH + DATA_ZIP_FILE_NAME)

        if (isDataFile) {
            val gson: Gson = Gson()
            val data: JsonObject = gson.fromJson(
                FileReader(pathFile).readText(),
                JsonObject::class.java
            )
            val lstEmployees: List<Employee> = gson.fromJson(
                data.getAsJsonObject("data").getAsJsonArray("employees"),
                object: TypeToken<List<Employee>>(){}.type
            )

            this.dataListener?.onSuccessGetData(lstEmployees)
        }
        else
            this.dataListener?.onFailedGetData(ERROR.DOWNLOAD_DATA_EXCEPTION)
    }
}