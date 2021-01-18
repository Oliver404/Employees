package com.oliverbotello.employeslocation.utils

import android.os.Environment
import java.io.File

const val URL_API: String = "https://dl.dropboxusercontent.com/"
val APP_PATH: String = Environment.getExternalStorageDirectory().path + File.separator
const val DATA_ZIP_FILE_NAME: String = "data.zip"
const val DATA_EMPLOYEES_FILE: String = "employees_data.json"
const val REGER_PASSWORD: String = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"
const val EMPLOYEE_ID: String = "employee id"