package com.example.percival

import android.content.Context
import android.graphics.Color
import android.media.AudioManager
import android.media.ToneGenerator
import android.widget.TextView
import com.example.percival.MyIO.save
import kotlin.random.Random

object MyColor {

    fun color(context: Context, tVL: List<TextView>): ToneGenerator {

        val r = Random.nextInt(0, 4)
        save(context, r.toString(), "data1", Context.MODE_APPEND)

        when (r) {
            0 -> { val tone = cyan(tVL); return tone; }
            1 -> { val tone = magenta(tVL); return tone; }
            2 -> { val tone = yellow(tVL); return tone; }
            3 -> { val tone = green(tVL); return tone; }
        }

        return ToneGenerator(AudioManager.STREAM_ALARM, 100)
    }

    fun colorAll(tVL: List<TextView>) {
        tVL[0].setBackgroundColor(Color.CYAN)
        tVL[1].setBackgroundColor(Color.MAGENTA)
        tVL[2].setBackgroundColor(Color.YELLOW)
        tVL[3].setBackgroundColor(Color.GREEN)
    }

    fun white(tVL: List<TextView>) {
        tVL[0].setBackgroundColor(Color.WHITE)
        tVL[1].setBackgroundColor(Color.WHITE)
        tVL[2].setBackgroundColor(Color.WHITE)
        tVL[3].setBackgroundColor(Color.WHITE)
    }

    private fun cyan(tVL: List<TextView>): ToneGenerator {
        val toneG = ToneGenerator(AudioManager.STREAM_ALARM, R.string.volume)
        toneG.startTone(ToneGenerator.TONE_DTMF_2, 500)
        tVL[0].setBackgroundColor(Color.CYAN)
        tVL[1].setBackgroundColor(Color.WHITE)
        tVL[2].setBackgroundColor(Color.WHITE)
        tVL[3].setBackgroundColor(Color.WHITE)
        return toneG
    }

    private fun magenta(tVL: List<TextView>): ToneGenerator {
        val toneG = ToneGenerator(AudioManager.STREAM_ALARM, R.string.volume)
        toneG.startTone(ToneGenerator.TONE_DTMF_4, 500)
        tVL[0].setBackgroundColor(Color.WHITE)
        tVL[1].setBackgroundColor(Color.MAGENTA)
        tVL[2].setBackgroundColor(Color.WHITE)
        tVL[3].setBackgroundColor(Color.WHITE)
        return toneG
    }

    private fun yellow(tVL: List<TextView>): ToneGenerator {
        val toneG = ToneGenerator(AudioManager.STREAM_ALARM, R.string.volume)
        toneG.startTone(ToneGenerator.TONE_DTMF_6, 500)
        tVL[0].setBackgroundColor(Color.WHITE)
        tVL[1].setBackgroundColor(Color.WHITE)
        tVL[2].setBackgroundColor(Color.YELLOW)
        tVL[3].setBackgroundColor(Color.WHITE)
        return toneG
    }

    private fun green(tVL: List<TextView>): ToneGenerator {
        val toneG = ToneGenerator(AudioManager.STREAM_ALARM, R.string.volume)
        toneG.startTone(ToneGenerator.TONE_DTMF_8, 500)
        tVL[0].setBackgroundColor(Color.WHITE)
        tVL[1].setBackgroundColor(Color.WHITE)
        tVL[2].setBackgroundColor(Color.WHITE)
        tVL[3].setBackgroundColor(Color.GREEN)
        return toneG
    }
}
