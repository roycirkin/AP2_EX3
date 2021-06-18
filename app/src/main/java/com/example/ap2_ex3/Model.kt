package com.example.ap2_ex3

import android.util.Log
import java.net.Socket
import java.util.concurrent.LinkedBlockingQueue
import java.io.PrintWriter
import java.lang.Exception

class Model (ip : String, port : Int){

    private var throttle = 0.5
    private var rudder = 0.0
    private var aileron = 0.0
    private var elevator = 0.0
    private var stop = false
    private final var BlockingQueue = LinkedBlockingQueue<Runnable>()
    private lateinit var outStream : PrintWriter
    /**
     * initializing the model, conecting to the flgiht gear if possible and starting the active object's thread
     */
    init {
        Thread(Runnable {
            try {
                val fg = Socket(ip, port)
                this.outStream = PrintWriter(fg.getOutputStream(), true)
            } catch (e: Exception) {
                Log.d("exe", e.message.toString())
            }
            while (!stop) {
                this.BlockingQueue.take().run()
            }
        }).start()
    }
    /**
     * setting the new throttle value, and inserting the queue a new runnable which updates the flight gear
     * throttleVal - the current value
     */
    fun setThrottle(throttleVal : Double) : Unit {
        BlockingQueue.put(object : Runnable {
            override fun run() {
                throttle = throttleVal
                outStream.print("set /controls/engines/current-engine/throttle "+throttle+"\r\n")
                outStream.flush()
            }
        })
    }
    /**
     * setting the new rudder value, and inserting the queue a new runnable which updates the flight gear
     * rudderVal - the current value
     */
    fun setRudder(rudderVal : Double) : Unit {
        BlockingQueue.put(object : Runnable {
            override fun run() {
                rudder = rudderVal
                outStream.print("set /controls/flight/rudder "+rudder+"\r\n")
                outStream.flush()
            }
        })
    }

    /**
     * The function update aileron value
     * aileronVal - aileron value
     */
    fun setAileron(aileronVal : Double) : Unit {
        BlockingQueue.put(object : Runnable {
            override fun run() {
                aileron = aileronVal
                outStream.print("set /controls/flight/aileron "+aileron+"\r\n")
                outStream.flush()
            }
        })
    }

    /**
     * The function update elevator value
     * aileronVal - elevator value
     */
    fun setElevator(elevatorVal : Double) : Unit {
        BlockingQueue.put(object : Runnable {
            override fun run() {
                elevator = elevatorVal
                outStream.print("set /controls/flight/elevator "+elevator+"\r\n")
                outStream.flush()
            }
        })
    }

    fun getThrottle() : Double{
        return  this.throttle
    }

    fun getRudder() : Double{
        return  this.rudder
    }

    /**
     * The function return aileron value
     * ret - aileron value
     */
    fun getAileron():Double {
        return aileron
    }

    /**
     * The function return elevator value
     * ret - elevator value
     */
    fun getElevator():Double {
        return elevator
    }

    fun stop() : Unit{
        BlockingQueue.put(object : Runnable {
            override fun run() {
                stop = true
            }
        })
    }
}