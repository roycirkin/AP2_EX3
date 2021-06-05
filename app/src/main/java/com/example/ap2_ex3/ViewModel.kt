package com.example.ap2_ex3

class ViewModel {
    private var model = Model()

    fun setVM_throttle(throttle : Double) {
        this.model.setThrottle(throttle)
    }

    fun getVM_throttle() : Double{
        return model.getThrottle()
    }

    fun setVM_rudder(rudder : Double) {
        this.model.setRudder(rudder)
    }

    fun getVM_rudder() : Double{
        return model.getRudder()
    }
}