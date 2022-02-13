package com.example.sketch

import android.content.Context
import android.graphics.*
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.core.content.res.ResourcesCompat
import kotlin.math.abs
import kotlin.math.log
import kotlin.math.pow
import kotlin.math.sqrt

class DrawingView(c: Context) : View(c) {

     var shape: Int = 0
     var isDrawing: Boolean= true
    var currentcolor = ResourcesCompat.getColor(resources, R.color.black, null)

    var mPath: Path = Path()
     val TOUCH_TOLERANCE = 4f

    private var mStartX = 0f
    private var mStartY = 0f

    var mX: Float = 0.0f
    var mY: Float = 0.0f

    var stroke_Width: Float = 12f
    lateinit var mCanvas: Canvas
    lateinit var mBitmap: Bitmap
    var paint: Paint = Paint().apply {
        color = currentcolor
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = stroke_Width

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        Log.d("mmmmmmmmmmmm","onSizeChanged")
        super.onSizeChanged(w, h, oldw, oldh)
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap!!)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(mBitmap!!, 0f, 0f, paint)
        if (isDrawing) {
            when (shape) {
                LINE -> onDrawLine(canvas)
                RECTANGLE -> onDrawRectangle(canvas)
                CIRCLE -> onDrawCircle(canvas)
            }
        }
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        mX = event.x
        mY = event.y
        when (shape) {
            LINE -> onTouchEventLine(event)
            PEN -> onTouchEventPencil(event)
            RECTANGLE -> onTouchEventRectangle(event)
            CIRCLE -> onTouchEventCircle(event)
        }
        return true
    }




    private fun onTouchEventPencil(event: MotionEvent) {

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isDrawing = true
                mStartX = mX
                mStartY = mY
              //  mPath!!.reset()
                mPath!!.moveTo(mX, mY)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = abs(mX - mStartX)
                val dy = abs(mY - mStartY)
                if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                    mPath!!.quadTo(mStartX, mStartY, (mX + mStartX) / 2, (mY + mStartY) / 2)
                    mStartX = mX
                    mStartY = mY
                }
                mCanvas!!.drawPath(mPath!!, paint!!)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                isDrawing = false
                mPath!!.lineTo(mStartX, mStartY)
                mCanvas!!.drawPath(mPath!!, paint!!)
            //    mPath!!.reset()
                invalidate()
            }
        }
    }


    private fun onDrawLine(canvas: Canvas) {
        Log.d("mmmmmmmmmmmm","onDrawLine")
        val dx = abs(mX - mStartX)
        val dy = abs(mY - mStartY)
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            canvas.drawLine(mStartX, mStartY, mX, mY, paint!!)
        }
    }

    private fun onTouchEventLine(event: MotionEvent) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isDrawing = true
                mStartX = mX
                mStartY = mY
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> invalidate()
            MotionEvent.ACTION_UP -> {
                isDrawing = false
                mCanvas!!.drawLine(mStartX, mStartY, mX, mY, paint!!)
                invalidate()
            }
        }
    }


    private fun onDrawCircle(canvas: Canvas) {

        Log.d("mmmmmmmmmmmm","drawCircle")
        canvas.drawCircle(mStartX, mStartY, calculateRadius(mStartX, mStartY, mX, mY), paint!!)
    }

    private fun onTouchEventCircle(event: MotionEvent) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isDrawing = true
                mStartX = mX
                mStartY = mY
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> invalidate()
            MotionEvent.ACTION_UP -> {
                isDrawing = false
                mCanvas!!.drawCircle(
                    mStartX, mStartY, calculateRadius(mStartX, mStartY, mX, mY),
                    paint!!
                )
                invalidate()
            }
        }
    }

    private fun calculateRadius(x1: Float, y1: Float, x2: Float, y2: Float): Float {
        return sqrt(
            (x1 - x2).toDouble().pow(2.0) +
                    (y1 - y2).toDouble().pow(2.0)
        ).toFloat()
    }


    private fun onDrawRectangle(canvas: Canvas) {
        Log.d("mmmmmmmmmmmm","onDrawRectangle")
        drawRectangle(canvas, paint)
    }

    private fun onTouchEventRectangle(event: MotionEvent) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isDrawing = true
                mStartX = mX
                mStartY = mY
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> invalidate()
            MotionEvent.ACTION_UP -> {
                isDrawing = false
                drawRectangle(mCanvas, paint)
                invalidate()
            }
        }
    }

    private fun drawRectangle(canvas: Canvas?, paint: Paint?) {
        val right = if (mStartX > mX) mStartX else mX
        val left = if (mStartX > mX) mX else mStartX
        val bottom = if (mStartY > mY) mStartY else mY
        val top = if (mStartY > mY) mY else mStartY
        canvas!!.drawRect(left, top, right, bottom, paint!!)
    }

    fun reset() {
        mPath = Path()
        paint= Paint().apply {
            color = currentcolor
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = stroke_Width        }
    }

    companion object {
        const val PEN = 1
        const val LINE = 2
        const val RECTANGLE = 3
        const val CIRCLE = 4

    }

}
