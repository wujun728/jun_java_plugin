package com.example.verificationcodedemo.model

/**
 * Date:2020/4/30
 * author:wuyan
 */
class CaptchaGetIt(
    val originalImageBase64: String,//图表url 目前用base64 data
    val point: Point,
    val jigsawImageBase64: String,
    val token: String,// 获取的token 用于校验
    val result: Boolean,
    val opAdmin: Boolean
)