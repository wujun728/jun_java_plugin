package com.example.verificationcodedemo.network

import android.content.Context
import android.util.Log
import com.example.verificationcodedemo.utils.MD5Util
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.Interceptor
import okhttp3.RequestBody
import okhttp3.Response
import okio.Buffer
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset

class CommonInterceptor(private val cx: Context) : Interceptor {

    private val utf8 = Charset.forName("UTF-8")

    override fun intercept(chain: Interceptor.Chain?): Response? {

        val request = chain?.request()

        val originalJsonElement = JsonParser().parse(getParamContent(request?.body()!!))

//        val time = System.currentTimeMillis().toString()
//        val baseSignMsg = ("reqData" + originalJsonElement + "time" + time
//                + "token" + Configuration.token)
//
//        val newJsonObject = JsonObject()
//
//        newJsonObject.addProperty("time", time)
//        newJsonObject.addProperty("token", Configuration.token)
//        newJsonObject.add("reqData", originalJsonElement)
//        newJsonObject.addProperty("sign", MD5Util.encode(baseSignMsg))
//
//        Log.e("请求参数", newJsonObject.toString())
        Log.e("请求参数", originalJsonElement.toString())

        val newRequestBody =
            RequestBody.create(request.body()!!.contentType(), originalJsonElement.toString())

        val newRequest = request.newBuilder()
            .header("Accept-Language", "zh-cn,zh")
            .method(request.method(), newRequestBody)
            .build()

        val response = chain.proceed(newRequest)

        val responseBody = response.body()
        val contentLength = responseBody?.contentLength()

        val source = responseBody?.source()
        source?.request(java.lang.Long.MAX_VALUE)
        val buffer = source?.buffer()

        var charset: Charset? = utf8
        val contentType = responseBody?.contentType()
        if (contentType != null) {
            charset = contentType.charset(utf8)
        }

        if (contentLength == 0L)
            return response

        val jsonObject: JSONObject
        try {
            jsonObject = JSONObject(buffer?.clone()?.readString(charset!!))
        } catch (e: JSONException) {
            return response
        }

        val code: String
        val msg: String
        try {
            code = jsonObject.getString("repCode")
            msg = jsonObject.getString("repMsg")
        } catch (e: Exception) {
            return response
        }
        return response
    }

    @Throws(IOException::class)
    private fun getParamContent(body: RequestBody): String {
        val buffer = Buffer()
        body.writeTo(buffer)
        return buffer.readUtf8()
    }

}