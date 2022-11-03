package com.example.percival

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.media.AudioManager
import android.media.ToneGenerator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.percival.MyColor.color
import com.example.percival.MyColor.colorAll
import com.example.percival.MyColor.white
import com.example.percival.MyGame.sol
import com.example.percival.MyIO.load
import com.example.percival.MyIO.save

class MainActivity : AppCompatActivity() {
    @SuppressLint("CutPasteId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tV1 = findViewById<TextView>(R.id.tV1)
        val tV2 = findViewById<TextView>(R.id.tV2)
        val tV3 = findViewById<TextView>(R.id.tV3)
        val tV4 = findViewById<TextView>(R.id.tV4)

        val b1 = findViewById<Button>(R.id.b1)

        tV1.isVisible = false
        tV2.isVisible = false
        tV3.isVisible = false
        tV4.isVisible = false

        var repetition = 4
        var sol = 0

        b1.text = "AVVIA $repetition"

        b1.setOnClickListener {

            sol = 0

            white(tV1, tV2, tV3, tV4)

            tV1.isClickable = false
            tV2.isClickable = false
            tV3.isClickable = false
            tV4.isClickable = false

            tV1.isVisible = true
            tV2.isVisible = true
            tV3.isVisible = true
            tV4.isVisible = true

            save(this.applicationContext, "", "data1", Context.MODE_PRIVATE)
            save(this.applicationContext, "", "data2", Context.MODE_PRIVATE)

            b1.isVisible = false

            var counter = 0.toLong()
            var tone = ToneGenerator(AudioManager.STREAM_ALARM, 100)

            repeat(repetition) {
                Handler(Looper.getMainLooper()).postDelayed({
                    tone = color(this.applicationContext, tV1, tV2, tV3, tV4)
                }, 1000 + counter)

                Handler(Looper.getMainLooper()).postDelayed({
                    tone.release()
                    white(tV1, tV2, tV3, tV4)
                }, 2000 + counter)

                counter += 2000
            }

            Handler(Looper.getMainLooper()).postDelayed({
                colorAll(tV1, tV2, tV3, tV4)
                tV1.isClickable = true
                tV2.isClickable = true
                tV3.isClickable = true
                tV4.isClickable = true
                val x = load(this.applicationContext, "data1")
                println(x)
            }, 3000 + counter)
        }

        tV1.setOnClickListener {

            tV1.isClickable = false
            tV2.isClickable = false
            tV3.isClickable = false
            tV4.isClickable = false

            save(this.applicationContext, "0", "data2", Context.MODE_APPEND)

            sol += 1
            repetition = sol(this.applicationContext, b1, tV1, tV2, tV3, tV4, sol, repetition)

            Handler(Looper.getMainLooper()).postDelayed({
                tV1.setBackgroundColor(Color.WHITE)
            }, 500)

            val toneG = ToneGenerator(AudioManager.STREAM_ALARM, 100)
            toneG.startTone(ToneGenerator.TONE_DTMF_2, 500)

            Handler(Looper.getMainLooper()).postDelayed({
                toneG.release()
                tV1.setBackgroundColor(Color.CYAN)
                    tV1.isClickable = true
                    tV2.isClickable = true
                    tV3.isClickable = true
                    tV4.isClickable = true
            }, 1000)
        }

        tV2.setOnClickListener {

            tV1.isClickable = false
            tV2.isClickable = false
            tV3.isClickable = false
            tV4.isClickable = false

            save(this.applicationContext, "1", "data2", Context.MODE_APPEND)

            sol +=1
            repetition = sol(this.applicationContext, b1, tV1, tV2, tV3, tV4, sol, repetition)

            Handler(Looper.getMainLooper()).postDelayed({
                tV2.setBackgroundColor(Color.WHITE)
            }, 500)

            val toneG = ToneGenerator(AudioManager.STREAM_ALARM, 100)
            toneG.startTone(ToneGenerator.TONE_DTMF_4, 500)

            Handler(Looper.getMainLooper()).postDelayed({
                toneG.release()
                tV2.setBackgroundColor(Color.MAGENTA)
                    tV1.isClickable = true
                    tV2.isClickable = true
                    tV3.isClickable = true
                    tV4.isClickable = true
            }, 1000)
        }

        tV3.setOnClickListener {

            tV1.isClickable = false
            tV2.isClickable = false
            tV3.isClickable = false
            tV4.isClickable = false

            save(this.applicationContext, "2", "data2", Context.MODE_APPEND)

            sol +=1
            repetition = sol(this.applicationContext, b1, tV1, tV2, tV3, tV4, sol, repetition)

            Handler(Looper.getMainLooper()).postDelayed({
                tV3.setBackgroundColor(Color.WHITE)
            }, 500)

            val toneG = ToneGenerator(AudioManager.STREAM_ALARM, 100)
            toneG.startTone(ToneGenerator.TONE_DTMF_6, 500)

            Handler(Looper.getMainLooper()).postDelayed({
                toneG.release()
                tV3.setBackgroundColor(Color.YELLOW)
                    tV1.isClickable = true
                    tV2.isClickable = true
                    tV3.isClickable = true
                    tV4.isClickable = true
            }, 1000)
        }

        tV4.setOnClickListener {

            tV1.isClickable = false
            tV2.isClickable = false
            tV3.isClickable = false
            tV4.isClickable = false

            save(this.applicationContext, "3", "data2", Context.MODE_APPEND)

            sol +=1
            repetition = sol(this.applicationContext, b1, tV1, tV2, tV3, tV4, sol, repetition)

            Handler(Looper.getMainLooper()).postDelayed({
                tV4.setBackgroundColor(Color.WHITE)
            }, 500)

            val toneG = ToneGenerator(AudioManager.STREAM_ALARM, 100)
            toneG.startTone(ToneGenerator.TONE_DTMF_8, 500)

            Handler(Looper.getMainLooper()).postDelayed({
                toneG.release()
                tV4.setBackgroundColor(Color.GREEN)
                    tV1.isClickable = true
                    tV2.isClickable = true
                    tV3.isClickable = true
                    tV4.isClickable = true
            }, 1000)
        }
    }
}
