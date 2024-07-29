package com.example.a20mis0158

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import java.io.OutputStream

object FileUtils {

    fun createFile(context: Context, fileName: String, mimeType: String): Uri? {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, mimeType)
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }

        return context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)?.also { uri ->
            context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                fun writeDataToFile(outputStream: OutputStream) {
                    // Example: Write some data to the outputStream
                    val data = "Sample Data".toByteArray()
                    outputStream.write(data)
                    outputStream.flush()
                }
            }
        }
    }

    fun readFile(context: Context, uri: Uri): String? {
        val stringBuilder = StringBuilder()
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            inputStream.bufferedReader().use { reader ->
                stringBuilder.append(reader.readText())
            }
        }
        return stringBuilder.toString()
    }
}
