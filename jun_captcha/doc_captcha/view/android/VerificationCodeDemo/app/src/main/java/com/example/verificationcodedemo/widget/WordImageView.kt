package com.example.verificationcodedemo.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.setPadding
import com.example.verificationcodedemo.R
import com.example.verificationcodedemo.model.Point
import com.example.verificationcodedemo.utils.DisplayUtil
import com.example.verificationcodedemo.utils.DisplayUtil.px2dip
import com.google.gson.Gson
import kotlinx.android.synthetic.main.word_view.view.*

/**
 * Date:2020/5/8
 * author:wuyan
 */
class WordImageView : FrameLayout {

    private val flashTime = 800
    private var size: Int = 0//需要点击文字的数量
    private val mList: MutableList<Point> = mutableListOf()
    private var cover: Bitmap? = null

    //监听
    private var wordListenner: WordListenner? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.word_view, this)
    }

    fun setSize(size: Int) {
        this.size = size
    }

    /**
     * 设置图片资源
     * @param cover 拼图
     */
    fun setUp(cover: Bitmap) {
        this.cover = cover
        val w = cover.width
        val h = cover.height
        val l = word_iv_cover!!.layoutParams
        l.width = DisplayUtil.dip2px(context, w.toFloat())
        l.height = DisplayUtil.dip2px(context, h.toFloat())
        word_iv_cover!!.layoutParams = l
        word_iv_cover!!.setImageBitmap(cover)
        setLocation(1f * cover.width / cover.height)
    }

    /**
     * 设置比例大小
     * @param cover_wph 图片比例
     */
    private fun setLocation(cover_wph: Float) {
        post {
            val w = word_fl_content!!.measuredWidth
            val h = (w / cover_wph).toInt()
            val l = word_fl_content!!.layoutParams
            l.width = w
            l.height = h
            word_fl_content!!.layoutParams = l
        }
    }

    //验证成功
    fun ok() {
        flashShowAnime()
        handler.postDelayed({ reset() }, 1000)
    }

    //验证失败
    fun fail() {
        handler.postDelayed({ reset() }, 1000)
    }

    //控件状态重置
    fun reset() {
        word_v_flash!!.visibility = View.GONE
        mList.clear()
        word_fl_content!!.removeAllViews()
        word_fl_content!!.addView(word_iv_cover)
        word_fl_content!!.addView(word_v_flash)
    }

    //成功高亮动画
    private fun flashShowAnime() {
        val translateAnimation = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 1f,
            Animation.RELATIVE_TO_SELF, -1f,
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 0f
        )
        translateAnimation.duration = flashTime.toLong()
        word_v_flash!!.animation = translateAnimation
        word_v_flash!!.visibility = View.VISIBLE
        translateAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationEnd(animation: Animation) {
                word_v_flash!!.visibility = View.GONE
            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            size--
            mList.add(
                Point(
                    px2dip(context, event.x).toDouble(),
                    px2dip(context, event.y).toDouble()
                )
            )
            if (size > 0) {
                //添加图标
                addTextView(event)
            } else if (size == 0) {
                addTextView(event)
                if (wordListenner != null)
                    wordListenner!!.onWordClick(Gson().toJson(mList))
                Log.e("wuyan", "Gson().toJson(mList)" + Gson().toJson(mList))
            }
        }
        return true
    }

    //点击后添加小圆点
    private fun addTextView(event: MotionEvent) {
        val textView = TextView(context)
        val l = LayoutParams(
            DisplayUtil.dip2px(context, 20*1f),
            DisplayUtil.dip2px(context, 20*1f)
        )
        textView.layoutParams = l
        textView.gravity= Gravity.CENTER
        textView.text = mList.size.toString()
        textView.setTextColor(Color.WHITE)
        textView.background = resources.getDrawable(R.drawable.shape_dot_bg)
        val postion = textView.layoutParams as MarginLayoutParams
        postion.leftMargin = event.x.toInt()-10
        postion.topMargin = event.y.toInt()-10
        word_fl_content!!.addView(textView)
    }

    interface WordListenner {
        fun onWordClick(cryptedStr: String)
    }

    fun setWordListenner(wordListenner: WordListenner) {
        this.wordListenner = wordListenner
    }

}