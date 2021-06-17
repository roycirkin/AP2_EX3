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

    /**
     * The function send to the model new aileron value
     * aileron - aileron value
     */
    fun setVM_aileron(aileron:Double){
        model.setAileron(aileron)
    }

    fun getVM_aileron():Double {
        return model.getAileron()
    }

    /**
     * The function send to the model new elevator value
     * elevator - elevator value
     */
    fun setVM_elevator(elevator:Double){
        model.setElevator(elevator)
    }

    fun getVM_elevator():Double {
        return model.getElevator()
    }

    fun setVM_throttleInt(throttle : Int) {
        this.model.setThrottle(throttle/100.0)
    }
    fun getVM_throttleInt() : Int {
        return (model.getThrottle() * 100).toInt()
    }
}