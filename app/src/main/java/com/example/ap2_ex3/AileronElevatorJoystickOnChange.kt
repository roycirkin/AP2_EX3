package com.example.ap2_ex3

import kotlin.math.abs

class AileronElevatorJoystickOnChange(_maxAileron:Float, _minAileron:Float, _maxElevator:Float, _minElevator:Float, _viewModel:ViewModel) : OnJoystickChange {
    private val averageAileron:Float = (_maxAileron + _minAileron) / 2
    private val averageElevator:Float = (_maxElevator + _minElevator) / 2
    private val viewModel:ViewModel = _viewModel

    override fun updateEvent(relativeMoveX:Float, relativeMoveY:Float) {
        viewModel.setVM_aileron((averageAileron + abs(averageAileron) * relativeMoveX).toDouble())
        viewModel.setVM_aileron((averageElevator + abs(averageElevator) * relativeMoveY).toDouble())
    }
}