package com.janeandjohnstudio.pathfinding.graphics

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.janeandjohnstudio.pathfinding.R


class Node : View {

    private lateinit var value : String
    private var bgPaint: Paint = Paint()
    private var textPaint: Paint = Paint()
    private var nodeColor : Int = 0x000000

    constructor(context: Context) : super(context)

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0, defStyleRes: Int = 0)
            : super(context, attrs, defStyleAttr, defStyleRes) {

        val a : TypedArray =
                context.obtainStyledAttributes(attrs, R.styleable.Node, defStyleAttr, defStyleRes)

        setNodeColor(a.getColor(R.styleable.Node_nodeColor, 0x000000))

        setNodeValue(a.getInt(R.styleable.Node_nodeValue, 0).toString())

        a.recycle()
    }

    private fun setNodeValue(value: String) {
        this.value = value
    }

    fun setNodeColor(color : Int) {
        nodeColor = color
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val xCenter = (width / 2).toFloat()
        val yCenter = (height / 2).toFloat()

        val radius = Math.min((width - paddingLeft - paddingRight).toFloat(), (height - paddingBottom - paddingTop).toFloat()) / 2

        bgPaint.color = nodeColor

        textPaint.color = 0x000000
        textPaint.textSize = 20F

        canvas.drawCircle(xCenter, yCenter, radius, bgPaint)
        canvas.drawText(value, 10F, 25F, textPaint)

    }
}
