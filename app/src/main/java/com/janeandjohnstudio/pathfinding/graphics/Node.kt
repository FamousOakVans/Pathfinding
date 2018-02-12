package com.janeandjohnstudio.pathfinding.graphics

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.support.v4.view.GestureDetectorCompat
import android.text.TextPaint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.janeandjohnstudio.pathfinding.R
import android.support.v4.view.MotionEventCompat
import android.view.MotionEvent.INVALID_POINTER_ID
import android.text.method.Touch.onTouchEvent
import android.view.DragEvent


class Node : View {

    private lateinit var value : String
//    private var valueSize : Float

    private val backgroundPaint: Paint = Paint()
    private val textPaint: TextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    private val borderPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textRect : Rect = Rect()

    private var lastTouchX = 0F
    private var lastTouchY = 0F
    private var activePointerId = 0

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

        this.value = a.getString(R.styleable.Node_nodeName)

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

//        val textWidth = textPaint.measureText(value)
        textPaint.getTextBounds(value, 0, value.length, textRect)
        textPaint.fontMetrics.ascent
        canvas.drawText(value, xCenter - textRect.width() / 2, yCenter + textRect.height() / 2 - textPaint.descent(), textPaint)

//        canvas.translate(0F, 200F)
    }

    override fun onDragEvent(event: DragEvent?): Boolean {
        return super.onDragEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

//        dete.onTouchEvent(ev)

        val action = event?.actionMasked

        when (action) {
            MotionEvent.ACTION_DOWN -> {
                val x = event.getX(event.actionIndex)
                val y = event.getY(event.actionIndex)

                // Remember where we started (for dragging)
                lastTouchX = x
                lastTouchY = y
                // Save the ID of this pointer (for dragging)
                activePointerId = event.getPointerId(0)
            }

            MotionEvent.ACTION_MOVE -> {
                // Find the index of the active pointer and fetch its position
                val pointerIndex = event.findPointerIndex(activePointerId)

                val x = event.getX(pointerIndex)
                val y = event.getY(pointerIndex)

                // Calculate the distance moved
                val dx = x - lastTouchX
                val dy = y - lastTouchY

//                mPosX += dx
//                mPosY += dy

                invalidate()

                // Remember this touch position for the next move event
                lastTouchX = x
                lastTouchY = y
            }

            MotionEvent.ACTION_UP -> {
                activePointerId = INVALID_POINTER_ID
            }

            MotionEvent.ACTION_CANCEL -> {
                activePointerId = INVALID_POINTER_ID
            }

            MotionEvent.ACTION_POINTER_UP -> {

                val pointerIndex = event.actionIndex
                val pointerId = event.getPointerId(pointerIndex)

                if (pointerId == activePointerId) {
                    // This was our active pointer going up. Choose a new
                    // active pointer and adjust accordingly.
                    val newPointerIndex = if (pointerIndex == 0) 1 else 0
                    lastTouchX = event.getX(newPointerIndex)
                    lastTouchY = event.getY(newPointerIndex)
                    activePointerId = event.getPointerId(newPointerIndex)
                }
            }
        }
        return true
    }

//    override fun performClick(): Boolean {
//        super.performClick()
//        return true
//    }
}
