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
            outerCirclePaint.color = value
        }
    var isJoystickPressed:Boolean = false
    var relativeMoveX:Float = 0.toFloat()
    var relativeMoveY:Float = 0.toFloat()
    var onChange:OnJoystickChange? = null
    var isPositionSet:Boolean = false

    /**
     * The function return true if the joystick is pressed, otherwise return false
     * coorX - coordinate x  of the press
     * coorY - coordinate y  of the press
     * ret - return true if the joystick is pressed, otherwise return false
     */
    fun isPressed(coorX:Float, coorY:Float):Boolean {
        val joystickTouchDistance = Math.sqrt(Math.pow((coorX - centerOuterCircleX).toDouble(), 2.0) + Math.pow((coorY - centerOuterCircleY).toDouble(), 2.0))
        return joystickTouchDistance < outerCircleRadius
    }

    /**
     * The function set isJoystickPressed
     * isJoystickPressed - if joystick pressed
     */
    fun setIsPressed(isJoystickPressed:Boolean){
        this.isJoystickPressed = isJoystickPressed
    }

    /**
     * The function return isJoystickPressed
     * ret - if joystick pressed
     */
    fun getIsPressed():Boolean {
        return isJoystickPressed
    }

    /**
     * The function update the joystick position, and activate  updateEvent function of onChange according pressed coordinates
     * coorX - coordinate x  of the press
     * coorY - coordinate y  of the press
     */
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

    /**
     * The function update inner circle of joystick position, according relative moves
     */
    fun update(){
        centerInnerCircleX = centerOuterCircleX + relativeMoveX * outerCircleRadius
        centerInnerCircleY = centerOuterCircleY + relativeMoveY * outerCircleRadius
        postInvalidate()
    }

    /**
     * The function reset relative moves
     */
    fun resetRelativeMoves(){
        relativeMoveX = 0.toFloat()
        relativeMoveY = 0.toFloat()

        update()
        onChange?.updateEvent(relativeMoveX, relativeMoveY)
    }

    /**
     * The function set starting position of joystick
     */
    fun setPositions(){
        centerInnerCircleX =  (this.getWidth() / 2).toFloat()
        centerInnerCircleY = (this.getHeight() / 2).toFloat()
        centerOuterCircleX =  (this.getWidth() / 2).toFloat()
        centerOuterCircleY = (this.getHeight() / 2).toFloat()
    }

    /**
     * The function handle with joystick press
     * event - press event that happened on the screen
     * ret - true
     */
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

    /**
     * The function draw the joystick in canvas
     */
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