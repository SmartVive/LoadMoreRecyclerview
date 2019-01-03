package com.example.administrator.loadmorerecyclerview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View

class StarView : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attributeSet: AttributeSet) : super(context, attributeSet)

    private var rating: Double = 0.0
    private var starCount: Int = 5
    private var starWidth: Int = 20
    private var starHeight: Int = 20
    private var margin: Int = 4
    private var starFull: Bitmap
    private var starHalf: Bitmap
    private var starGray: Bitmap
    private var paint: Paint

    init {
        starFull = BitmapFactory.decodeResource(context?.resources, R.mipmap.rating_star_full)
        starHalf = BitmapFactory.decodeResource(context?.resources, R.mipmap.rating_star_half)
        starGray = BitmapFactory.decodeResource(context?.resources, R.mipmap.rating_star_gray)
        paint = Paint()
    }

    fun setRating(num: Double?) {
        num?.let { rating = num }

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val top = ((height - starHeight) / 2)
        for (i in 0 until starCount) {
            var bitmap: Bitmap
            if ((i + 1) * 2 <= rating) {
                bitmap = starFull
            } else if ((i + 1) * 2 - 1 <= rating) {
                bitmap = starHalf
            } else {
                bitmap = starGray
            }
            val margin = margin * i
            val rect = Rect(i * starWidth + margin, top, (i + 1) * starWidth + margin, starHeight + top)
            canvas?.drawBitmap(bitmap, null, rect, paint)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecSize = MeasureSpec.getSize(heightMeasureSpec)

        val mWidth = starWidth * starCount + margin * (starCount - 1)
        val mHeight = starHeight
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mWidth, mHeight)
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mWidth, heightSpecSize)
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, mHeight)
        }
    }
}