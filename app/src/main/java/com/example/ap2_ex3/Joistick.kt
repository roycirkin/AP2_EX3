package com.example.ap2_ex3

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class JoyStick @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    var centerInnerCircleX:Float =  0.toFloat()
    var centerInnerCircleY:Float = 0.toFloat()
    var centerOuterCircleX:Float =  0.toFloat()
    var centerOuterCircleY:Float = 0.toFloat()
    var innerCircleRadius:Float =  0.toFloat()
    var outerCircleRadius:Float =  0.toFloat()
    private var innerCirclePaint:Paint = Paint()
    private var outerCirclePaint:Paint = Paint()
    var innerCircleColor:Int = 0
        set(value){
            field = value
            innerCirclePaint.color = value
        }
    var outerCircleColor:Int = 0
        set(value){
            field = value
            innerCirclePaint.color = value
        }
    var isJoystickPressed:Boolean = false
    var relativeMoveX:Float = 0.toFloat()
    var relativeMoveY:Float = 0.toFloat()
    var onChange:OnJoystickChange? = null
    var isPositionSet:Boolean = false

    init {
        println("width = " + this.getWidth())
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

        update()
        onChange?.updateEvent(relativeMoveX, relativeMoveY)
    }

    fun setPositions(){
        centerInnerCircleX =  (this.getWidth() / 2).toFloat()
        centerInnerCircleY = (this.getHeight() / 2).toFloat()
        centerOuterCircleX =  (this.getWidth() / 2).toFloat()
        centerOuterCircleY = (this.getHeight() / 2).toFloat()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            when(event.action){
                MotionEvent.ACTION_DOWN -> {
                    if(isPressed(event.getX().toFloat(), event.getY().toFloat())){
                        setIsPressed(true);
                    }
                    setPositions()
                    return true
                }
                MotionEvent.ACTION_MOVE -> {
                    if(getIsPressed()){
                        setRelativeMoves(event.getX().toFloat(), event.getY().toFloat())
                    }
                    return true
                }
                MotionEvent.ACTION_UP -> {
                    setIsPressed(false)
                    resetRelativeMoves()
                    return true
                }
            }
        }
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if(!isPositionSet)
        {
            setPositions()
            isPositionSet = true
        }
        if (canvas != null) {
            canvas.drawCircle(centerOuterCircleX, centerOuterCircleY, outerCircleRadius, outerCirclePaint)
        }
        if (canvas != null) {
            canvas.drawCircle(centerInnerCircleX, centerInnerCircleY, innerCircleRadius, innerCirclePaint)
        }
    }
}