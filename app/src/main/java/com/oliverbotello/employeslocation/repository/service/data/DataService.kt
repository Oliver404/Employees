package com.oliverbotello.employeslocation.repository.service.data

import com.oliverbotello.employeslocation.R


abstract class DataService {
    enum class ERROR(val idMessage: Int) {
        DOWNLOAD_DATA_EXCEPTION(R.string.error_download_data)
    }

    var dataListener: IDataListener? = null

    abstract fun getEmployees(): Unit
}