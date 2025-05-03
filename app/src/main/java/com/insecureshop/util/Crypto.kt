package com.insecureshop.util

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.inject.Inject
import javax.inject.Singleton

private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
private const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"
private val CHARSET = Charsets.UTF_8

@Singleton
class Crypto @Inject constructor() {
    private val cipher = Cipher.getInstance(TRANSFORMATION)

    private val keyStore = KeyStore
        .getInstance("AndroidKeyStore")
        .apply { load(null) }

    private fun createKey(alias: String): SecretKey = KeyGenerator
        .getInstance(ALGORITHM, "AndroidKeyStore")
        .apply {
            init(
                KeyGenParameterSpec.Builder(
                    alias,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT,
                )
                    .setBlockModes(BLOCK_MODE)
                    .setEncryptionPaddings(PADDING)
                    .setRandomizedEncryptionRequired(true)
                    .setUserAuthenticationRequired(false)
                    .build()
            )
        }
        .generateKey()

    private fun getKey(alias: String): SecretKey {
        val existingKey = keyStore.getEntry(alias, null) as? KeyStore.SecretKeyEntry?
        return existingKey?.secretKey ?: createKey(alias)
    }

    fun encrypt(alias: String, value: String): String {
        cipher.init(Cipher.ENCRYPT_MODE, getKey(alias))
        val iv = cipher.iv
        val data = value.toByteArray(CHARSET)
        val encrypted = cipher.doFinal(data)
        return Base64.encodeToString(iv + encrypted, Base64.DEFAULT)
    }

    fun decrypt(alias: String, value: String): String {
        val bytes = Base64.decode(value, Base64.DEFAULT)
        val iv = bytes.copyOfRange(0, cipher.blockSize)
        val data = bytes.copyOfRange(cipher.blockSize, bytes.size)
        cipher.init(Cipher.DECRYPT_MODE, getKey(alias), IvParameterSpec(iv))
        return cipher.doFinal(data).toString(CHARSET)
    }
}
