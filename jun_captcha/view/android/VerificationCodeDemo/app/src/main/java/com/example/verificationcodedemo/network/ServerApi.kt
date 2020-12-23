package com.example.verificationcodedemo.network

import com.example.verificationcodedemo.model.*
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Date:2020/4/29
 * author:wuyan
 */
interface ServerApi {

    companion object {
        const val urlDefault: String = "https://mirror.anji-plus.com/captcha-api/"
    }

    //获取验证码
    @POST("captcha/get")
    fun getAsync(@Body body: CaptchaGetOt): Deferred<Response<Input<CaptchaGetIt>>>

    //获取文字的验证码
    @POST("captcha/get")
    fun getWordCaptchaAsync(@Body body: CaptchaGetOt): Deferred<Response<Input<WordCaptchaGetIt>>>

    //核对验证码
    @POST("captcha/check")
    fun checkAsync(@Body body: CaptchaCheckOt): Deferred<Response<Input<CaptchaCheckIt>>>

}