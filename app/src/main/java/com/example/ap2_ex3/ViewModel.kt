package com.example.ap2_ex3

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.Observable

class ViewModel(ip : String, port : Int)   {
    private var model = Model(ip, port)

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
    fun setVM_throttleInt(throttle : Int) {
        this.model.setThrottle(throttle/100.0)
    }
    fun getVM_throttleInt() : Int {
        return (model.getThrottle() * 100).toInt()
    }
}