package com.example.rosariocompose

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
fun weekDay(): String {

    val dayN = LocalDate.now().dayOfWeek.ordinal
    var dayS = ""

    when (dayN) {
        0 -> dayS = "Lunedì"
        1 -> dayS = "Martedì"
        2 -> dayS = "Mercoledì"
        3 -> dayS = "Giovedì"
        4 -> dayS = "Venerdì"
        5 -> dayS = "Sabato"
        6 -> dayS = "Domenica"
    }

    return dayS
}

@RequiresApi(Build.VERSION_CODES.O)
fun rosario(applicationContext: Context): MediaPlayer {

    var mp = MediaPlayer()

    when (LocalDate.now().dayOfWeek.ordinal) {
        0 -> mp = MediaPlayer.create(applicationContext, R.raw.gaudiosi)
        1 -> mp = MediaPlayer.create(applicationContext, R.raw.dolorosi)
        2 -> mp = MediaPlayer.create(applicationContext, R.raw.gloriosi)
        3 -> mp = MediaPlayer.create(applicationContext, R.raw.gaudiosi)
        4 -> mp = MediaPlayer.create(applicationContext, R.raw.dolorosi)
        5 -> mp = MediaPlayer.create(applicationContext, R.raw.gloriosi)
        6 -> mp = MediaPlayer.create(applicationContext, R.raw.gloriosi)
    }

    mp.start()

    return mp
}
