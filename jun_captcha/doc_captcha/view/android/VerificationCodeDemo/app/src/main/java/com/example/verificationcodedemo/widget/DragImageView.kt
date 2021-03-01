package com.example.verificationcodedemo.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import android.widget.SeekBar
import com.example.verificationcodedemo.R
import com.example.verificationcodedemo.utils.DisplayUtil
import com.example.verificationcodedemo.utils.DisplayUtil.px2dip
import kotlinx.android.synthetic.main.drag_view.view.*

/**
 * Date:2020/5/6
 * author:wuyan
 */
class DragImageView : FrameLayout, SeekBar.OnSeekBarChangeListener {

    private val showTipsTime = 1500
    private val animeTime = 333
    private val flashTime = 800

    private var sb: SeekBar? = null
    private var cover: Bitmap? = null
    private var block: Bitmap? = null

    //===================seekbar监听===================
    private var timeTemp: Long = 0
    private var timeUse: Float = 0.toFloat()

    //失败延时重置控件
    private val resetRun = Runnable {
        tipsShowAnime(false)
        tips2ShowAnime(true)
        sb!!.isEnabled = true

        val position = sb!!.progress
        val animator = ValueAnimator.ofFloat(1f, 0.0f)
        animator.setDuration(animeTime.toLong()).start()
        animator.addUpdateListener { animation ->
            val f = animation.animatedValue as Float
            sb!!.progress = (position * f).toInt()
            setSbThumb(R.drawable.drag_btn_n)
            sb!!.progressDrawable = context.resources.getDrawable(R.drawable.drag_seek_progress)
        }
    }

    //监听
    private var dragListenner: DragListenner? = null

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
        View.inflate(context, R.layout.drag_view, this)
        //设置需要字体颜色为红色的内容
        drag_tv_tips!!.setColorRegex("拼图|成功|失败|正确|[\\d\\.%]+", -0x8aeaf)
        sb = findViewById(R.id.drag_sb)
        sb!!.max = context.resources.displayMetrics.widthPixels
        sb!!.setOnSeekBarChangeListener(this)
        reset()
    }


    /**
     * 设置资源
     *
     * @param cover 拼图
     * @param block 滑块
     */
    fun setUp(cover: Bitmap, block: Bitmap) {
        this.cover = cover
        this.block = block

        val w = cover.width
        val h = cover.height
        val l = drag_iv_cover!!.layoutParams
        l.width = DisplayUtil.dip2px(context, w.toFloat())
        l.height = DisplayUtil.dip2px(context, h.toFloat())
        drag_iv_cover!!.layoutParams = l
        drag_iv_cover!!.setImageBitmap(cover)

        val l2 = drag_iv_block!!.layoutParams as ViewGroup.MarginLayoutParams
        l2.height = DisplayUtil.dip2px(context, block.height.toFloat())
        l2.width = DisplayUtil.dip2px(context, block.width.toFloat())
        drag_iv_block!!.layoutParams = l2
        drag_iv_block!!.setImageBitmap(block)
        setLocation(1f * cover.width / cover.height, cover.width)
    }

    /**
     * 设置比例大小
     *
     * @param cover_wph  图片bili
     * @param block_size 滑块大小占高比
     */
    private fun setLocation(cover_wph: Float, cover_w: Int) {
        post {
            val w = cover_w
            val h = (w / cover_wph).toInt()
            val l = drag_fl_content!!.layoutParams
            l.width = DisplayUtil.dip2px(context, w.toFloat())
            l.height = DisplayUtil.dip2px(context, h.toFloat())
            drag_fl_content!!.layoutParams = l
        }

    }

    fun ok() {
        blockHideAnime()
        var penset: Int
        if (timeUse > 1) {
            penset = (99 - (timeUse - 1) / 0.1f).toInt()
        } else {
            penset = (99 - 0 / 0.1f).toInt()
        }
        if (penset < 1) penset = 1
        drag_tv_tips!!.text = String.format("拼图成功: 耗时%.1f秒,打败了%d%%的用户!", timeUse, penset)
        tipsShowAnime(true)
        flashShowAnime()
        sb!!.isEnabled = false
        setSbThumb(R.drawable.drag_btn_success)
        sb!!.progressDrawable = context.resources.getDrawable(R.drawable.drag_seek_progress_success)
    }

    fun fail() {
        drag_tv_tips!!.text = "拼图失败: 请重新拖曳滑块到正确的位置!"
        tipsShowAnime(true)
        handler.postDelayed(resetRun, showTipsTime.toLong())
        sb!!.isEnabled = false
        setSbThumb(R.drawable.drag_btn_error)
        sb!!.progressDrawable = context.resources.getDrawable(R.drawable.drag_seek_progress_fail)
    }

    fun reset() {
        val position = sb!!.progress
        if (position != 0) {
            val animator = ValueAnimator.ofFloat(1f, 0.0f)
            animator.setDuration(animeTime.toLong()).start()
            animator.addUpdateListener { animation ->
                val f = animation.animatedValue as Float
                sb!!.progress = (position * f).toInt()
            }
        }
        tipsShowAnime(false)
        tips2ShowAnime(true)
        sb!!.isEnabled = true
        drag_v_flash!!.visibility = View.GONE
        setSbThumb(R.drawable.drag_btn_n)
        sb!!.progressDrawable = context.resources.getDrawable(R.drawable.drag_seek_progress)
        drag_iv_block!!.visibility = View.VISIBLE
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        val cw = drag_iv_cover!!.measuredWidth
        val bw = drag_iv_block!!.measuredWidth
        val l = drag_iv_block!!.layoutParams as ViewGroup.MarginLayoutParams
        l.leftMargin = (cw - bw) * progress / seekBar.max
        drag_iv_block!!.layoutParams = l
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {
        setSbThumb(R.drawable.drag_btn)
        sb!!.progressDrawable = context.resources.getDrawable(R.drawable.drag_seek_progress)
        drag_iv_block!!.visibility = View.VISIBLE
        drag_iv_cover!!.setImageBitmap(cover)
        tips2ShowAnime(false)
        timeTemp = System.currentTimeMillis()
    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {
        timeUse = (System.currentTimeMillis() - timeTemp) / 1000f
        if (dragListenner != null)
            dragListenner!!.onDrag(
                px2dip(
                    context,
                    (drag_iv_cover!!.measuredWidth - drag_iv_block!!.measuredWidth).toFloat() * 1f * seekBar.progress.toFloat() / seekBar.max
                ).toDouble()
            )
    }
    //===================seekbar监听===================


    //闪烁滑块
    private fun twinkleImage(view: View?) {
        val animator = ValueAnimator.ofFloat(0.0f, 1.0f)
        animator.setTarget(view)
        animator.interpolator = LinearInterpolator()
        animator.setDuration(showTipsTime.toLong()).start()
        animator.addUpdateListener { animation ->
            val f = animation.animatedValue as Float
            val time = (showTipsTime * f).toInt()

            if (time < 125)
                view!!.visibility = View.INVISIBLE
            else if (time < 250)
                view!!.visibility = View.VISIBLE
            else if (time < 375)
                view!!.visibility = View.INVISIBLE
            else
                view!!.visibility = View.VISIBLE
        }
    }

    //提示文本显示隐藏
    private fun tipsShowAnime(isShow: Boolean) {
        if (drag_tv_tips!!.visibility == View.VISIBLE == isShow)
            return
        val translateAnimation = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, if (isShow) 1f else 0f,
            Animation.RELATIVE_TO_SELF, if (isShow) 0f else 1f
        )
        translateAnimation.duration = animeTime.toLong()
        drag_tv_tips!!.animation = translateAnimation
        drag_tv_tips!!.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    //提示文本显示隐藏
    private fun tips2ShowAnime(isShow: Boolean) {
        if (drag_tv_tips2!!.visibility == View.VISIBLE == isShow)
            return
        val translateAnimation =
            AlphaAnimation((if (isShow) 0 else 1).toFloat(), (if (isShow) 1 else 0).toFloat())
        translateAnimation.duration = animeTime.toLong()
        drag_tv_tips2!!.animation = translateAnimation
        drag_tv_tips2!!.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    //成功完成拼图滑块消失
    private fun blockHideAnime() {
        val translateAnimation = AlphaAnimation(1f, 0f)
        translateAnimation.duration = animeTime.toLong()
        drag_iv_block!!.animation = translateAnimation
        drag_iv_block!!.visibility = View.GONE
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
        drag_v_flash!!.animation = translateAnimation
        drag_v_flash!!.visibility = View.VISIBLE
        translateAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationEnd(animation: Animation) {
                drag_v_flash!!.visibility = View.GONE
            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })
    }

    //设置seekBar的Thumb
    fun setSbThumb(id: Int) {
        val drawable = resources.getDrawable(id)
        drawable.bounds = sb!!.thumb.bounds
        sb!!.thumb = drawable
        sb!!.thumbOffset = 0
    }

    //设置seekBar是否可以滑动
    fun setSBUnMove(isMove: Boolean) {
        sb!!.isEnabled = isMove
    }


    interface DragListenner {
        fun onDrag(position: Double)
    }

    fun setDragListenner(dragListenner: DragListenner) {
        this.dragListenner = dragListenner
    }
}