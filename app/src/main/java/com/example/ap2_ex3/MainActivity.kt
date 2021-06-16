package com.example.ap2_ex3

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private var throttleVal = 0;
    private var rudderVal = 0;
    private lateinit var viewModel : ViewModel;
    private var joyStick = JoyStick(this, 1.toFloat(), 1.toFloat(), 1.toFloat(), 1.toFloat(), 1, 1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        ipField.setOnClickListener{
            if (ipField.getText().toString() == "enter here the IP") {
                ipField.getText().clear();
            }
        }

        portField.setOnClickListener{
            if (portField.getText().toString() == "enter here the port") {
                portField.getText().clear();
            }
        }

        submitBTN.setOnClickListener {
            try {
                var port = portField.text.toString().toInt()
                viewModel = ViewModel(ipField.text.toString(), port)
            } catch (e : Exception) {
                //make toast!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                Log.d("error", "port isn't a number ")
            }
        }

        throttleSeekBar?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                var temp = progress.toDouble();
                temp /= 100;
                throttle.setText("throttle: " + String.format("%.2f", temp));
                if (::viewModel.isInitialized) {
                    viewModel.setVM_throttle(temp);
                }
            }

            override fun onStartTrackingTouch(seek: SeekBar) {}

            override fun onStopTrackingTouch(seek: SeekBar) {}
        })

        rudderSeekBar?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                var temp = progress.toDouble();
                temp -= 50;
                temp = temp/50;
                if (::viewModel.isInitialized) {
                    viewModel.setVM_rudder(temp);
                }
                rudder.setText("rudder: " + String.format("%.2f", temp));
            }

            override fun onStartTrackingTouch(seek: SeekBar) {}

            override fun onStopTrackingTouch(seek: SeekBar) {}
        })

        var progressThrottle  = throttleSeekBar.getProgress().toDouble();
        progressThrottle = progressThrottle/100;
        throttle.setText("throttle: " + String.format("%.2f", progressThrottle));
        var progressRudder  = rudderSeekBar.getProgress().toDouble();
        progressRudder -= 50;
        progressRudder = progressRudder/50;
        rudder.setText("rudder: " + String.format("%.2f", progressRudder));

        joyStick.onChange = AileronElevatorJoystickOnChange(1.toFloat(), (-1).toFloat(), 1.toFloat(), (-1).toFloat(), viewModel)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        //return super.onTouchEvent(event)
        if (event != null) {
            when(event.action){
                MotionEvent.ACTION_DOWN -> {
                    if(joyStick.isPressed(event.getX().toFloat(), event.getY().toFloat())){
                        joyStick.setIsPressed(true);
                    }
                    return true
                }
                MotionEvent.ACTION_MOVE -> {
                    if(joyStick.getIsPressed()){
                        joyStick.setRelativeMoves(event.getX().toFloat(), event.getY().toFloat())
                    }
                    return true
                }
                MotionEvent.ACTION_UP -> {
                    joyStick.setIsPressed(false)
                    joyStick.resetRelativeMoves()
                    return true
                }
            }
        }
        return true
    }


    };

