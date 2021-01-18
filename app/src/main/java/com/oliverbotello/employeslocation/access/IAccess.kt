package com.oliverbotello.employeslocation.access

interface IAccess {
    fun onStartDownloadData()
    fun onSuccessDownloadData()
    fun onFailedDownloadData()
}