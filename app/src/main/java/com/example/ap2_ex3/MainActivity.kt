package com.example.ap2_ex3

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ap2_ex3.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private var throttleVal = 0
    private var rudderVal = 0
    private lateinit var viewModel : ViewModel
    private lateinit var joyStick: JoyStick
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ipField.setOnClickListener{
            if (ipField.getText().toString() == "enter here the IP") {
                ipField.getText().clear()
            }
        }

        portField.setOnClickListener{
            if (portField.getText().toString() == "enter here the port") {
                portField.getText().clear()
            }
        }
         /**
         * initializing the view model and connecting to the model
         */
        submitBTN.setOnClickListener {
            try {
                var port = portField.text.toString().toInt()
                viewModel = ViewModel(ipField.text.toString(), port)
                joystick.onChange = AileronElevatorJoystickOnChange(viewModel)
            } catch (e : Exception) {
                val myToast = Toast.makeText(applicationContext,"cant connect to flight gear",Toast.LENGTH_SHORT)
                myToast.show()
            }
        }

        throttleSeekBar?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
             /**
             * databind the seekbar to the view model
             * SeekBar - the seek bar
             * progress - the new value from 0 to 100
             */
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                var temp = progress.toDouble()
                temp /= 100
                throttle.setText("throttle: " + String.format("%.2f", temp))
                if (::viewModel.isInitialized) {
                    viewModel.VM_Throttle = temp
                }
            }

            override fun onStartTrackingTouch(seek: SeekBar) {}

            override fun onStopTrackingTouch(seek: SeekBar) {}
        })

        rudderSeekBar?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            /**
             * databind the seekbar to the view model
             * SeekBar - the seek bar
             * progress - the new value from 0 to 100
             */
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                var temp = progress.toDouble()
                temp -= 50
                temp = temp/50
                if (::viewModel.isInitialized) {
                    viewModel.VM_Rudder = temp
                }
                rudder.setText("rudder: " + String.format("%.2f", temp))
            }

            override fun onStartTrackingTouch(seek: SeekBar) {}

            override fun onStopTrackingTouch(seek: SeekBar) {}
        })
        //setting the text fields with the default rudder and throttle values
        var progressThrottle  = throttleSeekBar.getProgress().toDouble()
        progressThrottle = progressThrottle/100
        throttle.setText("throttle: " + String.format("%.2f", progressThrottle))
        var progressRudder  = rudderSeekBar.getProgress().toDouble()
        progressRudder -= 50
        progressRudder = progressRudder/50
        rudder.setText("rudder: " + String.format("%.2f", progressRudder))


        joyStick = findViewById(R.id.joystick)

        joyStick.outerCircleRadius = 300.toFloat()
        joyStick.innerCircleRadius = 100.toFloat()

        joyStick.innerCircleColor = Color.BLACK
        joyStick.outerCircleColor = Color.RED


    }
    }

