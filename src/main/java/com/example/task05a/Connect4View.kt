package com.example.task05a

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.logicofficial.StudentConnect4Game

class Connect4View: View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    var game: StudentConnect4Game = StudentConnect4Game()
        set(value) {
            field = value
            // After the new value is set, make sure to recalculate sizes and then trigger a redraw
            recalculateDimensions()
            invalidate()
        }

    private val colCount:Int get() = game.columns
    private val rowCount:Int get() = game.rows

    private var circleDiameter: Float = 0f
    private var circleSpacing: Float = 0f
    private var circleSpacingRatio: Float = 0.2f

    private val gridPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.BLUE
    }

    private val player1Paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.RED
    }

    private val player2Paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.YELLOW
    }


    private val noPlayerPaint: Paint= Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.WHITE
    }

    private fun recalculateDimensions(w: Int = width, h: Int = height) {
        val diameterX = w/(colCount + (colCount+1)*circleSpacingRatio)
        val diameterY = h/(rowCount + (rowCount+1)*circleSpacingRatio)

        circleDiameter = minOf(diameterX, diameterY)
        circleSpacing = circleDiameter*circleSpacingRatio
    }



    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        recalculateDimensions(w,h)
    }

    override fun onDraw(canvas: Canvas) {
        val gridLeft: Float = 0f
        val gridTop: Float = 0f
        val gridRight: Float = gridLeft + colCount * (circleDiameter+circleSpacing) + circleSpacing
        val gridBottom: Float = gridTop + rowCount * (circleDiameter+circleSpacing) + circleSpacing

        //draw the game board
        canvas.drawRect(gridLeft, gridTop, gridRight, gridBottom, gridPaint)

        val radius = circleDiameter / 2f

        for (row in 0 until rowCount) {
            // The vertical center is the same for each circle in the row
            val cy = gridTop + circleSpacing + ((circleDiameter + circleSpacing) * row) + radius

            for (col in 0 until colCount) {
                // We will later on want to use the game data to determine this
                val paint = noPlayerPaint

                // Drawing circles uses the center and radius
                val cx = gridLeft + circleSpacing + ((circleDiameter + circleSpacing) * col) + radius

                canvas.drawCircle(cx, cy, radius, paint)
            }
        }
    }
}

