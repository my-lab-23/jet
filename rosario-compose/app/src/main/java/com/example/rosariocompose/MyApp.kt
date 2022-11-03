package com.example.rosariocompose

import android.media.MediaPlayer
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyApp(mp: MediaPlayer) {

    Row {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement
                .spacedBy(20.dp, alignment = Alignment.CenterVertically),
            modifier = Modifier.fillMaxHeight().weight(1f)
        ) {
            MyButton1(mp)
            Text(weekDay(), fontSize = 40.sp)
            MyButton2(mp)
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight().weight(1f)
        ) {
            Image(painter = painterResource(id = R.drawable.rosario), contentDescription = null)
        }
    }
}

@Composable
fun MyButton1(mp: MediaPlayer) {

    Button(onClick = {
        if(mp.isPlaying) { mp.pause() }
    }) {
        Text("PAUSA", fontSize = 40.sp)
    }
}

@Composable
fun MyButton2(mp: MediaPlayer) {

    Button(onClick = {
        if(!mp.isPlaying) {
            val pos = mp.currentPosition
            mp.seekTo(pos)
            mp.start()
        }
    }) {
        Text("RIPRENDI", fontSize = 40.sp)
    }
}
