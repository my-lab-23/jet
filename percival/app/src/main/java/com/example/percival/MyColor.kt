package com.example.percival

import android.content.Context
import android.graphics.Color
import android.media.AudioManager
import android.media.ToneGenerator
import android.widget.TextView
import com.example.percival.MyIO.save
import kotlin.random.Random

object MyColor {

    fun colorAll(tV1: TextView, tV2: TextView, tV3: TextView, tV4: TextView) {
        tV1.setBackgroundColor(Color.CYAN)
        tV2.setBackgroundColor(Color.MAGENTA)
        tV3.setBackgroundColor(Color.YELLOW)
        tV4.setBackgroundColor(Color.GREEN)
    }

    fun color(
        context: Context,
        tV1: TextView,
        tV2: TextView,
        tV3: TextView,
        tV4: TextView
    ): ToneGenerator {

        val r = Random.nextInt(0, 4)
        save(context, r.toString(), "data1", Context.MODE_APPEND)

        when (r) {
            0 -> { val tone = cyan(tV1, tV2, tV3, tV4); return tone; }
            1 -> { val tone = magenta(tV1, tV2, tV3, tV4); return tone; }
            2 -> { val tone = yellow(tV1, tV2, tV3, tV4); return tone; }
            3 -> { val tone = green(tV1, tV2, tV3, tV4); return tone; }
        }

        return ToneGenerator(AudioManager.STREAM_ALARM, 100)
    }

    fun white(tV1: TextView, tV2: TextView, tV3: TextView, tV4: TextView) {
        tV1.setBackgroundColor(Color.WHITE)
        tV2.setBackgroundColor(Color.WHITE)
        tV3.setBackgroundColor(Color.WHITE)
        tV4.setBackgroundColor(Color.WHITE)
    }

    private fun cyan(tV1: TextView, tV2: TextView, tV3: TextView, tV4: TextView): ToneGenerator {
        val toneG = ToneGenerator(AudioManager.STREAM_ALARM, 100)
        toneG.startTone(ToneGenerator.TONE_DTMF_2, 500)
        tV1.setBackgroundColor(Color.CYAN)
        tV2.setBackgroundColor(Color.WHITE)
        tV3.setBackgroundColor(Color.WHITE)
        tV4.setBackgroundColor(Color.WHITE)
        return toneG
    }

    private fun magenta(tV1: TextView, tV2: TextView, tV3: TextView, tV4: TextView): ToneGenerator {
        val toneG = ToneGenerator(AudioManager.STREAM_ALARM, 100)
        toneG.startTone(ToneGenerator.TONE_DTMF_4, 500)
        tV1.setBackgroundColor(Color.WHITE)
        tV2.setBackgroundColor(Color.MAGENTA)
        tV3.setBackgroundColor(Color.WHITE)
        tV4.setBackgroundColor(Color.WHITE)
        return toneG
    }

    private fun yellow(tV1: TextView, tV2: TextView, tV3: TextView, tV4: TextView): ToneGenerator {
        val toneG = ToneGenerator(AudioManager.STREAM_ALARM, 100)
        toneG.startTone(ToneGenerator.TONE_DTMF_6, 500)
        tV1.setBackgroundColor(Color.WHITE)
        tV2.setBackgroundColor(Color.WHITE)
        tV3.setBackgroundColor(Color.YELLOW)
        tV4.setBackgroundColor(Color.WHITE)
        return toneG
    }

    private fun green(tV1: TextView, tV2: TextView, tV3: TextView, tV4: TextView): ToneGenerator {
        val toneG = ToneGenerator(AudioManager.STREAM_ALARM, 100)
        toneG.startTone(ToneGenerator.TONE_DTMF_8, 500)
        tV1.setBackgroundColor(Color.WHITE)
        tV2.setBackgroundColor(Color.WHITE)
        tV3.setBackgroundColor(Color.WHITE)
        tV4.setBackgroundColor(Color.GREEN)
        return toneG
    }
}
