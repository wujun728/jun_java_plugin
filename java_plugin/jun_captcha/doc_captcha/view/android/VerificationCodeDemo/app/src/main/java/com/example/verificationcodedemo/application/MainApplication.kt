package com.example.verificationcodedemo.application

import android.app.Application
import android.content.Context
import com.example.verificationcodedemo.network.Configuration
import com.example.verificationcodedemo.network.ServerApi

/**
 * Date:2020/4/29
 * author:wuyan
 */
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val url = getSharedPreferences("CONFIGURATION", Context.MODE_PRIVATE)
            .getString("IP", ServerApi.urlDefault)
        Configuration.server = Configuration.getServer(this, url!!)

    }

}