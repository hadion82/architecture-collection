package com.example.core.util

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.util.encoders.Base64
import timber.log.Timber
import java.lang.Exception
import java.security.Security

class AESEncryptor {
    companion object {
        const val DEF_KEY = "!Q@W#E"
    }

    fun encrypt(strToEncrypt: String?, secret_key: String = DEF_KEY): String? {
        if (strToEncrypt == null) {
            return null
        }
        Security.addProvider(BouncyCastleProvider())
        var keyBytes: ByteArray
        try {
            keyBytes = secret_key.toByteArray(charset("UTF8"))
            val skey = SecretKeySpec(keyBytes, "AES")
            val input = strToEncrypt.toByteArray(charset("UTF8"))

            synchronized(Cipher::class.java) {
                val cipher = Cipher.getInstance("AES/ECB/PKCS7Padding")
                cipher.init(Cipher.ENCRYPT_MODE, skey)

                val cipherText = ByteArray(cipher.getOutputSize(input.size))
                var ctLength = cipher.update(
                    input, 0, input.size,
                    cipherText, 0
                )
                ctLength += cipher.doFinal(cipherText, ctLength)
                return String(
                    Base64.encode(cipherText)
                )
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
        return null
    }

    fun decryptWithAES(strToDecrypt: String?, key: String = DEF_KEY): String? {
        Security.addProvider(BouncyCastleProvider())
        var keyBytes: ByteArray
        try {
            keyBytes = key.toByteArray(charset("UTF8"))
            val skey = SecretKeySpec(keyBytes, "AES")
            val input = Base64
                .decode(strToDecrypt?.trim { it <= ' ' }?.toByteArray(charset("UTF8")))

            synchronized(Cipher::class.java) {
                val cipher = Cipher.getInstance("AES/ECB/PKCS7Padding")
                cipher.init(Cipher.DECRYPT_MODE, skey)

                val plainText = ByteArray(cipher.getOutputSize(input.size))
                var ptLength = cipher.update(input, 0, input.size, plainText, 0)
                ptLength += cipher.doFinal(plainText, ptLength)
                val decryptedString = String(plainText)
                return decryptedString.trim { it <= ' ' }
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
        return null
    }
}