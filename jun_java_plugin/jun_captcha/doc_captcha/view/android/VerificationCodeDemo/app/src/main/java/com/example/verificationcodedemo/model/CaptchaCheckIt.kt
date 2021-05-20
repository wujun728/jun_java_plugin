package com.example.verificationcodedemo.model

/**
 * Date:2020/4/30
 * author:wuyan
 */
class CaptchaCheckIt(
    val captchaType: String,
    val token: String,
    val result: Boolean,
    val opAdmin: Boolean
)