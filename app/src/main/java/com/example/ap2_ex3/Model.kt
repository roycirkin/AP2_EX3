package com.example.ap2_ex3

import android.util.Log
import java.net.Socket
import java.util.concurrent.LinkedBlockingQueue
import java.io.PrintWriter
import java.lang.Exception

class Model (ip : String, port : Int){

    private var throttle = 0.5;
    private var rudder = 0.0;
    private var aileron = 0.0
    private var elevator = 0.0
    private var stop = false;
    private final var BlockingQueue = LinkedBlockingQueue<Runnable>();
    private lateinit var outStream : PrintWriter;

    init {
        Thread(Runnable {
            try {
                val fg = Socket(ip, port);
                this.outStream = PrintWriter(fg.getOutputStream(), true);
            } catch (e: Exception) {
                Log.d("exe", e.message.toString());
            }
            while (!stop) {
                this.BlockingQueue.take().run();
            }
        }).start();
    }

    fun setThrottle(throttleVal : Double) : Unit {
        BlockingQueue.put(object : Runnable {
            override fun run() {
                throttle = throttleVal;
                outStream.print("set /controls/engines/current-engine/throttle "+throttle+"\r\n");
                outStream.flush();
            }
        });
    }

    fun setRudder(rudderVal : Double) : Unit {
        BlockingQueue.put(object : Runnable {
            override fun run() {
                rudder = rudderVal;
                outStream.print("set /controls/flight/rudder "+rudder+"\r\n");
                outStream.flush();
            }
        });
    }

    fun setAileron(aileronVal : Double) : Unit {
        BlockingQueue.put(object : Runnable {
            override fun run() {
                aileron = aileronVal;
                outStream.print("set /controls/flight/aileron "+aileron+"\r\n");
                outStream.flush();
            }
        });
    }

    fun setElevator(elevatorVal : Double) : Unit {
        BlockingQueue.put(object : Runnable {
            override fun run() {
                elevator = elevatorVal;
                outStream.print("set /controls/flight/elevator "+elevator+"\r\n");
                outStream.flush();
            }
        });
    }

    fun getThrottle() : Double{
        return  this.throttle;
    }

    fun getRudder() : Double{
        return  this.rudder;
    }

    fun getAileron():Double {
        return aileron
    }

    fun getElevator():Double {
        return elevator
    }

    fun stop() : Unit{
        BlockingQueue.put(object : Runnable {
            override fun run() {
                stop = true;
            }
        });
    }
}