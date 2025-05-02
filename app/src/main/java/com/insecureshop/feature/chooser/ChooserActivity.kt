package com.insecureshop.feature.chooser

import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import androidx.appcompat.app.AppCompatActivity
import com.insecureshop.R
import com.insecureshop.util.ext.getParcelableExtraCompat
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

// Inlined (no static field) when decompiled to Java with JADX
private const val TEMP_FILE_NAME = "tmp"
private const val BUFFER_SIZE = 8192
private const val EOF = -1

@AndroidEntryPoint
class ChooserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chooser)

        if (intent.extras != null) {
            val uri = requireNotNull(
                intent.getParcelableExtraCompat<Uri>("android.intent.extra.STREAM")
            )

            makeTempCopy(uri, getFilename(uri))
        }
    }

    private fun makeTempCopy(fileUri: Uri, originalFilename: String): Uri? {
        try {
            val fileTemp = File.createTempFile(originalFilename, null, cacheDir)

            val out = Uri.fromFile(fileTemp)

            contentResolver.openInputStream(fileUri)?.use { inputStream ->
                contentResolver.openOutputStream(out)?.use { outputStream ->
                    val buffer = ByteArray(BUFFER_SIZE)

                    while (true) {
                        val len = inputStream.read(buffer).takeIf { it != EOF } ?: break
                        outputStream.write(buffer, 0, len)
                    }
                }
            }

            return out
        } catch (e: Exception) {
            return null
        }
    }

    private fun getFilename(uri: Uri): String = when (uri.scheme) {
        "file" -> uri.lastPathSegment ?: TEMP_FILE_NAME

        "content" -> {
            val proj = arrayOf(OpenableColumns.DISPLAY_NAME)

            contentResolver
                .query(uri, proj, null, null, null)
                ?.takeIf { it.count > 0 }
                ?.use { cursor ->
                    val columnIndex = cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME)
                    cursor.moveToFirst()
                    cursor.getString(columnIndex)
                }
                ?: TEMP_FILE_NAME
        }

        else -> TEMP_FILE_NAME
    }
}

