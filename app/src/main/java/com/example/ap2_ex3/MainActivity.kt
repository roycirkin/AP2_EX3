package com.example.ap2_ex3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    var throttleVal = 0;
    var rudderVal = 0;
    var viewModel = ViewModel();

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
            println(portField.getText().toString() + ipField.getText().toString());
        }

        throttleSeekBar?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                var temp = progress.toDouble();
                temp /= 100;
                viewModel.setVM_throttle(temp);
                throttle.setText("throttle: " + String.format("%.2f", temp));
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
                viewModel.setVM_rudder(temp);
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
    }

    };

