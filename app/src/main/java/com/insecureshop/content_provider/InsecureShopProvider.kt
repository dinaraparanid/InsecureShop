package com.insecureshop.content_provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import com.insecureshop.hilt.PrefsEntryPoint
import dagger.hilt.android.EntryPointAccessors

class InsecureShopProvider : ContentProvider() {

    companion object {
        const val URI_AUTHORITY = "com.insecureshop.file_provider"
        const val URI_PATH = "insecure"
        const val URI_CODE: Int = 100
    }

    private val uriMatcher by lazy {
        UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(URI_AUTHORITY, URI_PATH, URI_CODE)
        }
    }

    private val prefs by lazy {
        EntryPointAccessors.fromApplication(
            context!!.applicationContext,
            PrefsEntryPoint::class.java,
        ).prefs
    }

    override fun onCreate(): Boolean = true

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?,
    ): Cursor? {
        val username = prefs.loadUsername()
        val password = prefs.loadPassword()

        return when {
            uriMatcher.match(uri) == URI_CODE && username != null && password != null ->
                MatrixCursor(arrayOf("username", "password")).apply {
                    addRow(arrayOf(username, password))
                }

            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return 0
    }
}