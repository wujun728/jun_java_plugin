package com.example.verificationcodedemo.widget

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import kotlinx.android.synthetic.main.dialog_block_puzzle.*
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View.*
import android.widget.Toast
import com.example.verificationcodedemo.R
import com.example.verificationcodedemo.model.CaptchaCheckOt
import com.example.verificationcodedemo.model.CaptchaGetOt
import com.example.verificationcodedemo.model.Point
import com.example.verificationcodedemo.network.Configuration
import com.example.verificationcodedemo.utils.AESUtil
import com.example.verificationcodedemo.utils.ImageUtil
import com.google.gson.Gson
import kotlinx.android.synthetic.main.dialog_block_puzzle.tv_delete
import kotlinx.android.synthetic.main.dialog_block_puzzle.tv_refresh
import kotlinx.android.synthetic.main.dialog_word_captcha.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf

/**
 * Date:2020/5/6
 * author:wuyan
 */
class BlockPuzzleDialog : Dialog {
    constructor(mContext: Context) : this(mContext, 0)
    constructor(mContext: Context, themeResId: Int) : super(
        mContext,
        com.example.verificationcodedemo.R.style.dialog
    ) {
        window!!.setGravity(Gravity.CENTER)
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val windowManager = (mContext as Activity).windowManager
        val display = windowManager.defaultDisplay
        val lp = window!!.attributes
        lp.width = display.width * 9 / 10//设置宽度为屏幕的0.9
        window!!.attributes = lp
        setCanceledOnTouchOutside(false)//点击外部Dialog不消失
    }

    var baseImageBase64: String = ""//背景图片
    var slideImageBase64: String = ""//滑动图片
    var handler: Handler? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.verificationcodedemo.R.layout.dialog_block_puzzle)

        tv_delete.setOnClickListener {
            dismiss()
        }

        tv_refresh.setOnClickListener {
            loadCaptcha()
        }

        //设置默认图片
        val bitmap: Bitmap = ImageUtil.getBitmap(context, R.drawable.bg_default)
        val bitmap1: Bitmap = ImageUtil.getBitmap(context, R.drawable.bg_default)
        dragView.setUp(
            ImageUtil.base64ToBitmap(ImageUtil.bitmapToBase64(bitmap))!!,
            ImageUtil.base64ToBitmap(ImageUtil.bitmapToBase64(bitmap1))!!
        )
        dragView.setSBUnMove(false)
        loadCaptcha()
    }

    private fun loadCaptcha() {
        Configuration.token = ""
        GlobalScope.launch(Dispatchers.Main) {
            try {

                dragView.visibility = INVISIBLE
                rl_pb.visibility = VISIBLE

                val o = CaptchaGetOt(
                    captchaType = "blockPuzzle"
                )
                val b = Configuration.server.getAsync(o).await().body()
                when (b?.repCode) {

                    "0000" -> {
                        baseImageBase64 = b.repData!!.originalImageBase64
                        slideImageBase64 = b.repData!!.jigsawImageBase64
                        Configuration.token = b.repData!!.token

                        dragView.setUp(
                            ImageUtil.base64ToBitmap(baseImageBase64)!!,
                            ImageUtil.base64ToBitmap(slideImageBase64)!!
                        )
                        dragView.setSBUnMove(true)
                        initEvent()
                    }
                    else -> {
                        dragView.setSBUnMove(false)
                    }
                }
                dragView.visibility = VISIBLE
                rl_pb.visibility = GONE

            } catch (e: Exception) {
                e.printStackTrace()
                runUIDelayed(
                    Runnable {
                        dragView.setSBUnMove(false)
                        dragView.visibility = VISIBLE
                        rl_pb.visibility = GONE
                        Toast.makeText(context, "网络请求错误", Toast.LENGTH_SHORT).show()
                    }, 1000
                )
            }
        }
    }

    private fun checkCaptcha(sliderXMoved: Double) {
        val point = Point(sliderXMoved, 5.0)
        var pointStr = Gson().toJson(point).toString()
        Log.e("wuyan", pointStr)
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val o = CaptchaCheckOt(
                    captchaType = "blockPuzzle",
                    pointJson = AESUtil.encode(pointStr),
                    token = Configuration.token
                )
                val b = Configuration.server.checkAsync(o).await().body()
                when (b?.repCode) {

                    "0000" -> {
                        dragView.ok()
                        runUIDelayed(
                            Runnable {
                                dragView.reset()
                                dismiss()
                                loadCaptcha()
                            }, 2000
                        )
                    }
                    else -> {
                        dragView.fail()
                        //刷新验证码
                        loadCaptcha()
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
                dragView.fail()
                loadCaptcha();
            }
        }
    }

    fun initEvent() {
        dragView.setDragListenner(object : DragImageView.DragListenner {
            override fun onDrag(position: Double) {
                checkCaptcha(position)
            }
        })
    }

    fun runUIDelayed(run: Runnable, de: Int) {
        if (handler == null)
            handler = Handler(Looper.getMainLooper())
        handler!!.postDelayed(run, de.toLong())
    }


}