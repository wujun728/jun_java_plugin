package com.example.verificationcodedemo.widget

import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import java.util.regex.Pattern

/**
 * Date:2020/5/6
 * author:wuyan
 */
class DiyStyleTextView : AppCompatTextView {

    private var colorRegex: String = ""//需要改变颜色的内容
    private var color: Int = 0 //需要改变的颜色


    private val indexArr: MutableList<Int> = mutableListOf()
    private val strArr: MutableList<String> = mutableListOf()

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    /**
     * 设置需要改变颜色的文本，和改变的颜色
     */
    fun setColorRegex(colorRegex: String, color: Int): DiyStyleTextView {
        movementMethod = LinkMovementMethod.getInstance()
        this.colorRegex = colorRegex
        this.color = color
        return this
    }

    override fun setText(text: CharSequence, type: TextView.BufferType) {
        super.setText(setTextStyle(text, false), type)
    }

    fun setTextStyle(text: CharSequence, flag: Boolean): CharSequence? {
        if (TextUtils.isEmpty(text)) {
            if (flag) super.setText(text)
            return text
        }

        val styledText = SpannableStringBuilder(text)

        if (!TextUtils.isEmpty(colorRegex)) {

            indexArr.clear()
            strArr.clear()

            val p = Pattern.compile(colorRegex!!)
            val m = p.matcher(text)
            while (m.find()) {
                strArr.add(m.group())
                indexArr.add(m.start())
            }
            for (i in indexArr.indices) {
                val index = indexArr[i]
                val clickText = strArr[i]

                styledText.setSpan(
                    TextViewClickSpan(clickText),
                    index,
                    index + clickText.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

        }

        if (flag) super.setText(styledText)
        return styledText
    }

    private inner class TextViewClickSpan internal constructor(private val clickText: String) :
        ClickableSpan() {
        override fun onClick(widget: View) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun updateDrawState(ds: TextPaint) {
            ds.color = color
        }
    }

}


