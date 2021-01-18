package com.oliverbotello.employeslocation.repository.service.data

import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface IDataFileAPI {
    @GET("s/5u21281sca8gj94/getFile.json")
    fun getFileURL(): Call<JsonObject>

    @GET
    fun downloadFile(@Url fileUrl: String): Call<ResponseBody>
}