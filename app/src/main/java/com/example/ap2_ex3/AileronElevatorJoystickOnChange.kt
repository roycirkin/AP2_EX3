package com.example.ap2_ex3

class AileronElevatorJoystickOnChange(_viewModel:ViewModel) : OnJoystickChange {
    private val viewModel:ViewModel = _viewModel

    /**
     * The function update the aileron and elevator fields
     * relativeMoveX - value form -1 to 1 that which describes the change that will take place in aileron
     * relativeMoveY - value form -1 to 1 that which describes the change that will take place in elevator
     */
    override fun updateEvent(relativeMoveX:Float, relativeMoveY:Float) {
        viewModel.setVM_aileron(relativeMoveX.toDouble())
        viewModel.setVM_elevator(relativeMoveY.toDouble())
    }
}