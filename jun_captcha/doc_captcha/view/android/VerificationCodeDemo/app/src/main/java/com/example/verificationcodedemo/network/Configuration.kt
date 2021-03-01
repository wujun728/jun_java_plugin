package com.example.verificationcodedemo.network

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.math.BigDecimal

object Configuration {

    var token: String = ""
    lateinit var server: ServerApi

    fun getServer(cx: Context, url: String): ServerApi {

        val m = Moshi.Builder()
            .add(BigDecimalAdapter())
            .build()

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val httpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(CommonInterceptor(cx))
            .addInterceptor(loggingInterceptor)
            .build()
        return Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(MoshiConverterFactory.create(m))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(url)
            .build()
            .create<ServerApi>(ServerApi::class.java)

    }

}

class BigDecimalAdapter {
    @ToJson
    fun toJson(bigDecimal: BigDecimal): String {
        return bigDecimal.toPlainString()
    }

    @FromJson
    fun fromJson(bigDecimal: String): BigDecimal {
        return BigDecimal(bigDecimal)
    }
}