package com.example.stopwatch

import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private var seconds = 0
    private var running = false
    private var wasRunning = false
    private lateinit var start: ImageView
    private lateinit var pause: ImageView
    private lateinit var reset: ImageView

    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start = findViewById(R.id.start)
        pause = findViewById(R.id.pause)
        reset = findViewById(R.id.reset)

        pause.setOnClickListener {
            onClickPause()
        }

        start.setOnClickListener {
            onClickStart()
        }

        reset.setOnClickListener {
            onClickReset()
        }

        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds")
            running = savedInstanceState.getBoolean("running")
            wasRunning = savedInstanceState.getBoolean("wasRunning")
        }

        runTimer()
    }

    private fun runTimer() {
        val timer: TextView = findViewById(R.id.time_view)
        handler.post(object : Runnable {
            override fun run() {
                val hours = seconds / 3600
                val minutes = (seconds % 3600) / 60
                val secs = seconds % 60
                val time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs)

                timer.text = time

                if (running) {
                    seconds++
                }
                handler.postDelayed(this, 1000)

            }
        })
    }

    private fun onClickStart() {
        running = true
    }

    private fun onClickPause() {
        running = false
    }
    private fun onClickReset(){
        running = false
        seconds = 0
    }

    override fun onPause() {
        super.onPause()
        wasRunning = running
        running = false
        handler.removeCallbacksAndMessages(null)
    }

    override fun onResume() {
        super.onResume()
        if (wasRunning) {
            running = true
            runTimer()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("seconds", seconds)
        outState.putBoolean("running", running)
        outState.putBoolean("wasRunning", wasRunning)
        super.onSaveInstanceState(outState)
    }
}