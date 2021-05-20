package com.example.verificationcodedemo.model

/**
 * Date:2020/4/30
 * author:wuyan
 */
class CaptchaCheckOt(
    val captchaType: String,//滑动拼图 blockPuzzle,文字点选 clickWord
    val pointJson: String,//aes加密坐标信息
    val token: String//get请求返回的token

)