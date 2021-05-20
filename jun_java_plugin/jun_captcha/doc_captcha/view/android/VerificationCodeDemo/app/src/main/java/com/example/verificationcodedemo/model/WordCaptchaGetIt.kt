package com.example.verificationcodedemo.model

/**
 * Date:2020/5/8
 * author:wuyan
 */
class WordCaptchaGetIt(
    val originalImageBase64: String,//图表url 目前用base64 data
    val result: Boolean,
    val token: String,// 获取的token 用于校验
    val wordList: MutableList<String>?
)