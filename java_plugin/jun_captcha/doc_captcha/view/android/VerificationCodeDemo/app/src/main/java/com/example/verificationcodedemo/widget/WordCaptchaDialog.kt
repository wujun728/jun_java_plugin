package com.example.verificationcodedemo.widget

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.example.verificationcodedemo.R
import com.example.verificationcodedemo.model.CaptchaCheckOt
import com.example.verificationcodedemo.model.CaptchaGetOt
import com.example.verificationcodedemo.network.Configuration
import com.example.verificationcodedemo.utils.AESUtil
import com.example.verificationcodedemo.utils.ImageUtil
import kotlinx.android.synthetic.main.dialog_word_captcha.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Date:2020/5/8
 * author:wuyan
 */
class WordCaptchaDialog : Dialog {
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
    var handler: Handler? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.verificationcodedemo.R.layout.dialog_word_captcha)

        tv_delete.setOnClickListener {
            dismiss()
        }

        tv_refresh.setOnClickListener {
            wordView.reset()
            loadCaptcha()
        }

        //设置默认图片
        val bitmap: Bitmap = ImageUtil.getBitmap(context, R.drawable.bg_default)
        wordView.setUp(
            ImageUtil.base64ToBitmap(ImageUtil.bitmapToBase64(bitmap))!!
        )
        loadCaptcha()
    }

    private fun loadCaptcha() {
        Configuration.token = ""
        GlobalScope.launch(Dispatchers.Main) {
            try {
                bottomTitle.text = "数据加载中......"
                bottomTitle.setTextColor(Color.BLACK)
                wordView.visibility = View.INVISIBLE
                rl_pb_word.visibility = View.VISIBLE

                val o = CaptchaGetOt(
                    captchaType = "clickWord"
                )
                val b = Configuration.server.getWordCaptchaAsync(o).await().body()
                when (b?.repCode) {

                    "0000" -> {
                        baseImageBase64 = b.repData!!.originalImageBase64
                        Configuration.token = b.repData!!.token
                        var wordStr: String = ""
                        var i = 0;
                        b.repData!!.wordList!!.forEach {
                            i++
                            wordStr += it
                            if (i < b.repData!!.wordList!!.size)
                                wordStr += ","
                        }
                        wordView.setSize(b.repData!!.wordList!!.size)
                        bottomTitle.text = "请依此点击【" + wordStr + "】"
                        bottomTitle.setTextColor(Color.BLACK)
                        wordView.setUp(
                            ImageUtil.base64ToBitmap(baseImageBase64)!!
                        )
                        initEvent()
                    }
                    else -> {
                        bottomTitle.text = "加载失败,请刷新"
                        bottomTitle.setTextColor(Color.RED)
                        wordView.setSize(-1)
                    }
                }
                wordView.visibility = VISIBLE
                rl_pb_word.visibility = GONE

            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("wuyan", e.toString())
                runUIDelayed(
                    Runnable {
                        bottomTitle.text = "加载失败,请刷新"
                        bottomTitle.setTextColor(Color.RED)
                        wordView.setSize(-1)
                        wordView.visibility = VISIBLE
                        rl_pb_word.visibility = GONE
                    }, 1000
                )
            }
        }
    }

    //检查验证码
    private fun checkCaptcha(pointListStr: String) {
        Log.e("wuyan", AESUtil.encode(pointListStr))
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val o = CaptchaCheckOt(
                    captchaType = "clickWord",
                    pointJson = AESUtil.encode(pointListStr),
                    token = Configuration.token
                )
                val b = Configuration.server.checkAsync(o).await().body()
                when (b?.repCode) {

                    "0000" -> {
                        bottomTitle.text = "验证成功"
                        bottomTitle.setTextColor(Color.GREEN)
                        wordView.ok()
                        runUIDelayed(
                            Runnable {
                                dismiss()
                                loadCaptcha()
                            }, 2000
                        )
                    }
                    else -> {
                        bottomTitle.text = "验证失败"
                        bottomTitle.setTextColor(Color.RED)
                        wordView.fail()
                        runUIDelayed(
                            Runnable {
                                //刷新验证码
                                loadCaptcha()
                            }, 1500
                        )
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
                bottomTitle.text = "验证失败"
                bottomTitle.setTextColor(Color.RED)
                wordView.fail()
                runUIDelayed(
                    Runnable {
                        //刷新验证码
                        loadCaptcha()
                    }, 1500
                )
            }
        }
    }

    fun initEvent() {
        wordView.setWordListenner(object : WordImageView.WordListenner {
            override fun onWordClick(cryptedStr: String) {
                if (cryptedStr != null) {
                    checkCaptcha(cryptedStr!!)
                }
            }
        })
    }

    fun runUIDelayed(run: Runnable, de: Int) {
        if (handler == null)
            handler = Handler(Looper.getMainLooper())
        handler!!.postDelayed(run, de.toLong())
    }


}