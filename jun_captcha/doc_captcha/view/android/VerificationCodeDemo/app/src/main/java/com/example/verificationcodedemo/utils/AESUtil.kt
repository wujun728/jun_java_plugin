package com.example.verificationcodedemo.utils

import android.text.TextUtils
import android.util.Base64
import android.util.Log
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.SecretKeySpec

/**
 * Date:2020/5/8
 * author:wuyan
 */
object AESUtil {

    private val KEY: String = "XwKsGlMcdPMEhR1B"
    private val ALGORITHMSTR: String = "AES/ECB/PKCS7Padding"
    private val ALGORITHM: String = "AES"

    fun encode(content: String): String {
        if (TextUtils.isEmpty(content)) {
            return content
        }
        try {
            val result = encrypt(KEY, content)
            return String(Base64.encode(result, Base64.DEFAULT))
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    @Throws(Exception::class)
    private fun encrypt(encryptKey: String, content: String): ByteArray {
        // 创建AES秘钥
        val secretKeySpec = SecretKeySpec(encryptKey.toByteArray(), ALGORITHM)
        // 创建密码器
        val cipher = Cipher.getInstance(ALGORITHMSTR)
        // 初始化加密器
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
        // 加密
        return cipher.doFinal(content.toByteArray(charset("UTF-8")))
    }

}