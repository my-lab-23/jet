package com.example.percival

import android.content.Context
import android.graphics.Color
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.percival.MyIO.save

object MyUI {

    fun clickable(tVL: List<TextView>, b: Boolean) {
        for(tV in tVL) tV.isClickable = b
    }

    fun visible(tVL: List<TextView>, b: Boolean) {
        for(tV in tVL) tV.isVisible = b
    }

    fun tV(context: Context, tVL: List<TextView>, i: Int) {

        val toneG = ToneGenerator(AudioManager.STREAM_ALARM, 100)

        clickable(tVL, false)

        save(context, i.toString(), "data2", Context.MODE_APPEND)

        Handler(Looper.getMainLooper()).postDelayed({
            tVL[i].setBackgroundColor(Color.WHITE)
        }, 500)

        when(i) {
            0 -> toneG.startTone(ToneGenerator.TONE_DTMF_2, 500)
            1 -> toneG.startTone(ToneGenerator.TONE_DTMF_4, 500)
            2 -> toneG.startTone(ToneGenerator.TONE_DTMF_6, 500)
            3 -> toneG.startTone(ToneGenerator.TONE_DTMF_8, 500)
        }

        Handler(Looper.getMainLooper()).postDelayed({
            toneG.release()

            when(i) {
                0 -> tVL[i].setBackgroundColor(Color.CYAN)
                1 -> tVL[i].setBackgroundColor(Color.MAGENTA)
                2 -> tVL[i].setBackgroundColor(Color.YELLOW)
                3 -> tVL[i].setBackgroundColor(Color.GREEN)
            }

            clickable(tVL, true)
        }, 1000)
    }
}
