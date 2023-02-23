package com.example.percival

import android.annotation.SuppressLint
import android.content.Context
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
import com.example.percival.MyGame.result
import com.example.percival.MyIO.load
import com.example.percival.MyIO.save
import com.example.percival.MyUI.clickable
import com.example.percival.MyUI.tV
import com.example.percival.MyUI.visible

class MainActivity : AppCompatActivity() {
    @SuppressLint("CutPasteId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tV1 = findViewById<TextView>(R.id.tV1)
        val tV2 = findViewById<TextView>(R.id.tV2)
        val tV3 = findViewById<TextView>(R.id.tV3)
        val tV4 = findViewById<TextView>(R.id.tV4)
        val tVL = listOf(tV1, tV2, tV3, tV4)
        val b1 = findViewById<Button>(R.id.b1)

        var repetition = 4
        var click = 0

        visible(tVL, false)
        b1.text = "AVVIA $repetition"

        b1.setOnClickListener {

            var counter = 0.toLong()
            var tone = ToneGenerator(AudioManager.STREAM_ALARM, 100)

            white(tVL)
            clickable(tVL, false)
            visible(tVL, true)
            b1.isVisible = false

            click = 0

            save(this.applicationContext, "", "data1", Context.MODE_PRIVATE)
            save(this.applicationContext, "", "data2", Context.MODE_PRIVATE)

            repeat(repetition) {
                Handler(Looper.getMainLooper()).postDelayed({
                    tone = color(this.applicationContext, tVL)
                }, 1000 + counter)

                Handler(Looper.getMainLooper()).postDelayed({
                    tone.release()
                    white(tVL)
                }, 2000 + counter)

                counter += 2000
            }

            Handler(Looper.getMainLooper()).postDelayed({
                colorAll(tVL)
                clickable(tVL, true)
                val x = load(this.applicationContext, "data1")
                println(x)
            }, 3000 + counter)
        }

        tV1.setOnClickListener {

            tV(applicationContext, tVL, 0)
            click += 1
            repetition = result(applicationContext, b1, tVL, click, repetition)
        }

        tV2.setOnClickListener {

            tV(applicationContext, tVL, 1)
            click += 1
            repetition = result(applicationContext, b1, tVL, click, repetition)
        }

        tV3.setOnClickListener {

            tV(applicationContext, tVL, 2)
            click += 1
            repetition = result(applicationContext, b1, tVL, click, repetition)
        }

        tV4.setOnClickListener {

            tV(applicationContext, tVL, 3)
            click += 1
            repetition = result(applicationContext, b1, tVL, click, repetition)
        }
    }
}
