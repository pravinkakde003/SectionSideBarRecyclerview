package com.user.sectionalphabetrecyclerview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 *  Sidebar is custom class used to add/attach index bar (A to Z) to recyclerview.
 */
class SideBar(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var onTouchingLetterChangedListener: OnTouchingLetterChangedListener? = null
    private var choose = -1
    private val paint = Paint()
    private var mTextDialog: TextView? = null
    private var parent: RecyclerView? = null

//    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {}

    fun setTextView(textDialog: TextView?) {
        mTextDialog = textDialog
    }

    /**
     * @param canvas object
     * Draw the view on canvas
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
//        val height = height
//        val width = width
        val singleHeight = height / b.size
        for (i in b.indices) {
            paint.color = Color.rgb(33, 65, 98)
            paint.typeface = Typeface.DEFAULT_BOLD
            paint.isAntiAlias = true
            paint.isDither = true
            if (i == choose) {
                paint.color = Color.parseColor("#FF4081")
                paint.isFakeBoldText = true
            }
            //            To show bar from top
//            float xPos = width / 2 - paint.measureText(b[i]) / 2;
//            float yPos = singleHeight * i + singleHeight;
//            canvas.drawText(b[i], xPos, yPos, paint);
//            setBackgroundResource(R.drawable.shape_sidebar_bg)
//            //            To show bar from center
            val scaledWidth = indWidth * resources.displayMetrics.density
            val scaledHeight = indHeight * resources.displayMetrics.density
            val sx = width - this.paddingRight - (1.2 * scaledWidth).toFloat()
            val sy = ((height - scaledHeight * b.size) / 2.0).toFloat()
            canvas.drawText(b[i],
                    sx + paint.textSize / 2, sy + parent!!.paddingTop
                    + scaledHeight * (i + 1), paint)
            paint.reset()
        }
    }

    /**
     * Track the touch event
     */
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val action = event.action
        val y = event.y
        val oldChoose = choose
        val listener = onTouchingLetterChangedListener
        val c = (y / height * b.size).toInt()
        when (action) {
            MotionEvent.ACTION_UP -> {
                setBackgroundResource(R.drawable.shape_sidebar_bg)
                choose = -1
                invalidate()
                if (mTextDialog != null) {
                    mTextDialog!!.visibility = INVISIBLE
                }
            }
            else -> {
                setBackgroundResource(R.drawable.shape_sidebar_bg)
                if (oldChoose != c) {
                    if (c >= 0 && c < b.size) {
                        listener?.onTouchingLetterChanged(b[c])
                        if (mTextDialog != null) {
                            mTextDialog!!.text = b[c]
                            mTextDialog!!.visibility = VISIBLE
                        }
                        choose = c
                        invalidate()
                    }
                }
            }
        }
        return true
    }

    fun setOnTouchingLetterChangedListener(onTouchingLetterChangedListener: OnTouchingLetterChangedListener?) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener
    }

    fun setParentView(mRecyclerView: RecyclerView?) {
        parent = mRecyclerView
    }

    interface OnTouchingLetterChangedListener {
        fun onTouchingLetterChanged(s: String?)
    }

    /**
     *   companion object for static A to Z
     */
    companion object {
        var b = arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "I",
                "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z", "#")
        var indWidth = 25
        var indHeight = 18
    }
}