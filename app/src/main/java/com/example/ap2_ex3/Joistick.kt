package com.example.ap2_ex3

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import kotlin.math.sqrt

class JoyStick(_context: Context, _centerJoistickX: Float, _centerJoistickY:Float, _innerCircleRadius:Float, _outerCircleRadius:Float, _innerCircleColor:Int, _outerCircleColor:Int) : View(_context) {
    private var centerInnerCircleX:Float
    private var centerInnerCircleY:Float
    private var centerOuterCircleX:Float
    private var centerOuterCircleY:Float
    private var innerCircleRadius:Float
    private var outerCircleRadius:Float
    private var innerCirclePaint:Paint
    private var outerCirclePaint:Paint
    private var isJoystickPressed:Boolean
    private var relativeMoveX:Float = 0.toFloat()
    private var relativeMoveY:Float = 0.toFloat()
    var onChange:OnJoystickChange? = null

    init{
        centerInnerCircleX = _centerJoistickX
        centerInnerCircleY = _centerJoistickY
        centerOuterCircleX = _centerJoistickX
        centerOuterCircleY = _centerJoistickY

        innerCircleRadius = _innerCircleRadius
        outerCircleRadius = _outerCircleRadius

        innerCirclePaint = Paint()
        innerCirclePaint.color = _innerCircleColor
        innerCirclePaint.style = Paint.Style.FILL_AND_STROKE

        outerCirclePaint = Paint()
        outerCirclePaint.color = _outerCircleColor
        outerCirclePaint.style = Paint.Style.FILL_AND_STROKE

        isJoystickPressed = false
    }

    fun isPressed(coorX:Float, coorY:Float):Boolean {
        val joystickTouchDistance = Math.sqrt(Math.pow((coorX - centerOuterCircleX).toDouble(), 2.0) + Math.pow((coorY - centerOuterCircleY).toDouble(), 2.0))
        return joystickTouchDistance < outerCircleRadius
    }

    fun setIsPressed(isJoystickPressed:Boolean){
        this.isJoystickPressed = isJoystickPressed
    }

    fun getIsPressed():Boolean {
        return isJoystickPressed
    }

    fun setRelativeMoves(coorX:Float, coorY:Float){
        var distanceX = coorX - centerOuterCircleX
        var distanceY = coorY - centerOuterCircleY
        var distanceFromCenter =  Math.sqrt(Math.pow(distanceX.toDouble(), 2.0) + Math.pow(distanceY.toDouble(), 2.0))

        if(distanceFromCenter < outerCircleRadius) {
            relativeMoveX = distanceX / outerCircleRadius
            relativeMoveY = distanceY / centerOuterCircleY
        } else {
            relativeMoveX = distanceX / distanceFromCenter.toFloat()
            relativeMoveY = distanceY / distanceFromCenter.toFloat()
        }

        update()
        onChange?.updateEvent(relativeMoveX, relativeMoveY)
    }

    fun update(){
        centerInnerCircleX = centerOuterCircleX + relativeMoveX * outerCircleRadius
        centerInnerCircleY = centerOuterCircleY + relativeMoveY * outerCircleRadius
        postInvalidate()
    }

    fun resetRelativeMoves(){
        relativeMoveX = 0.toFloat()
        relativeMoveY = 0.toFloat()
    }

    override fun onDraw(canvas: Canvas?) {
        //super.onDraw(canvas)
        if (canvas != null) {
            canvas.drawCircle(centerOuterCircleX, centerOuterCircleY, outerCircleRadius, outerCirclePaint)
        }
        if (canvas != null) {
            canvas.drawCircle(centerInnerCircleX, centerInnerCircleY, innerCircleRadius, innerCirclePaint)
        }
    }
}