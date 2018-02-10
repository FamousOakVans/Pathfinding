package com.janeandjohnstudio.pathfinding.graphics

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.janeandjohnstudio.pathfinding.R

class Node : View {

    private lateinit var value : String
//    private var valueSize : Float

    private var backgroundPaint: Paint = Paint()
    private var textPaint: TextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    private var borderPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        borderPaint.style = Paint.Style.STROKE
    }

    private var nodeColor : Int = 0x000000

    constructor(context: Context) : super(context)

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0, defStyleRes: Int = 0)
            : super(context, attrs, defStyleAttr, defStyleRes) {

        val a : TypedArray =
                context.obtainStyledAttributes(attrs, R.styleable.Node, defStyleAttr, defStyleRes)

        setNodeColor(a.getColor(R.styleable.Node_nodeColor, Color.WHITE))

        setNodeBorder(a)

        setNodeValue(a, attrs)

        // required
        a.recycle()
    }

    private fun setNodeBorder(a: TypedArray) {

        borderPaint.style = Paint.Style.STROKE

        val width = a.getFloat(R.styleable.Node_borderWidth, 3F)
        val color = a.getColor(R.styleable.Node_borderColor, Color.BLACK)

        borderPaint.color = color
        borderPaint.strokeWidth = width

    }

    private fun setNodeValue(a: TypedArray, attrs: AttributeSet?) {

        this.value = a.getString(R.styleable.Node_nodeValue)

        val set = intArrayOf(android.R.attr.textSize)

        val b : TypedArray = context.obtainStyledAttributes(attrs, set)

        val textSize : Float = b.getDimensionPixelSize(0, 0).toFloat()
        if(textSize != 0F) {
            this.textPaint.textSize = textSize
        }

        // required
        b.recycle()
    }

    private fun setNodeColor(color : Int) {
        nodeColor = color
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val xCenter = (width / 2).toFloat()
        val yCenter = (height / 2).toFloat()

        val radius = Math.min((width - paddingLeft - paddingRight).toFloat() - borderPaint.strokeWidth,
                (height - paddingBottom - paddingTop).toFloat() - borderPaint.strokeWidth) / 2

        backgroundPaint.color = nodeColor

        textPaint.style = Paint.Style.FILL
        textPaint.color = Color.BLACK

        canvas.drawCircle(xCenter, yCenter, radius, backgroundPaint)
        canvas.drawCircle(xCenter, yCenter, radius, borderPaint)

        canvas.drawText(value, xCenter, yCenter, textPaint)

        canvas.translate(0F, 200F)
    }
}
