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

}