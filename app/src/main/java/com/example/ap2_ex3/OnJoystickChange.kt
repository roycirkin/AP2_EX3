package com.example.ap2_ex3

interface OnJoystickChange {
    /**
     * The function update event that happened because the joystick
     * relativeMoveX - value form -1 to 1 that which describes the change that will take place in x
     * relativeMoveY - value form -1 to 1 that which describes the change that will take place in y
     */
    fun updateEvent(relativeMoveX:Float, relativeMoveY:Float)
}