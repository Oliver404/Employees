package com.oliverbotello.employeslocation.utils

import okhttp3.ResponseBody
import java.io.*
import java.lang.Exception
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

fun writeFileToDisk(response: ResponseBody): Boolean {
    var written: Boolean = false

    try {
        val fileToDownload = File(APP_PATH + DATA_ZIP_FILE_NAME)
        var inputStream: InputStream? = null
        var outputStream: OutputStream? = null

        if (fileToDownload.exists())
            fileToDownload.delete()

        try {
            val fileReader = ByteArray(4096)
            val fileSize: Long = response.contentLength()
            var fileSizeDownloaded: Long = 0
            inputStream = response.byteStream()
            outputStream = FileOutputStream(fileToDownload)

            while (true) {
                val read: Int = inputStream.read(fileReader)

                if (read == -1)
                    break

                outputStream.write(fileReader, 0, read)

                fileSizeDownloaded += read.toLong()
            }

            outputStream.flush()

            written = true
        }
        catch (e: IOException) {
            written = false
        }
        finally {
            if (inputStream != null)
                inputStream.close()

            if (outputStream != null)
                outputStream.close()
        }
    } catch (e: IOException) {
        written = false
    }

    return written
}

fun unzipFile(filePath: String): Boolean {
    try {
        val zip = ZipFile(filePath)
        val entries: Enumeration<out ZipEntry> = zip.entries()

        while (entries.hasMoreElements()) {
            val entry: ZipEntry = entries.nextElement()
            val stream: InputStream = zip.getInputStream(entry)
            val bytes: ByteArray = stream.readBytes()
            val newFile: File = File(APP_PATH + entry.name)

            if (!newFile.exists()) {
                val outputStream: OutputStream = FileOutputStream(newFile)

                outputStream.write(bytes)
            }
        }

        return true
    }
    catch (e: Exception) {
        return false
    }
}