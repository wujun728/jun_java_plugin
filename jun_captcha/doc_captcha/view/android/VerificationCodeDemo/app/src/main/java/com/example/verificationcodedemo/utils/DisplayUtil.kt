package com.example.verificationcodedemo.utils

import android.content.Context

/**
 * Date:2020/5/9
 * author:wuyan
 */
object DisplayUtil {

    /**
     *
     * 将dp值转换为px，保证尺寸大小不变
     * DisplayMetrics类中属性density
     */

    fun dip2px(context: Context, dpValue: Float): Int {

        val scale = context.resources.displayMetrics.density

        return (dpValue * scale + 0.5f).toInt()

    }

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     * DisplayMetrics类中属性density
     */
    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }
}