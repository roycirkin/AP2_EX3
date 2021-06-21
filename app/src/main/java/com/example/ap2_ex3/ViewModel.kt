package com.example.ap2_ex3

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModel(ip : String, port : Int)   {
    private var model = Model(ip, port)

    var VM_Throttle: Double
        get() {
            return  model.getThrottle()
        }
        set(value){
           model.setThrottle(value)
        }

    var VM_Rudder: Double
        get() {
            return  model.getRudder()
        }
        set(value){
            model.setRudder(value)
        }
    var VM_Aileron: Double
        get() {
            return  model.getAileron()
        }
        set(value){
            model.setAileron(value)
        }
    var VM_Elevator: Double
        get() {
            return  model.getElevator()
        }
        set(value){
            model.setElevator(value)
        }



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

}