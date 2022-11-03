package com.example.percival

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.percival.MyIO.load
import com.example.percival.MyIO.save

object MyGame {

    private fun check(context: Context): Boolean {
        val x = load(context, "data1")
        val y = load(context,"data2")
        save(context, "", "data2", Context.MODE_PRIVATE)
        println(x)
        println(y)
        return x==y
    }

    @SuppressLint("SetTextI18n")
    fun sol(
        context: Context,
        b1: Button,
        tV1: TextView,
        tV2: TextView,
        tV3: TextView,
        tV4: TextView,
        sol: Int,
        repetition: Int
    ): Int {
        println(sol)
        if(sol==repetition) {
            val res = check(context)
            if(res) {
                tV1.isVisible = false
                tV2.isVisible = false
                tV3.isVisible = false
                tV4.isVisible = false
                b1.isVisible = true
                b1.text = "VINTO ${repetition+1}"
                return repetition+1
            } else {
                tV1.isVisible = false
                tV2.isVisible = false
                tV3.isVisible = false
                tV4.isVisible = false
                b1.isVisible = true
                if(repetition>4) b1.text = "PERSO ${repetition-1}"
                else b1.text = "PERSO 4"
                return if(repetition>4) repetition-1
                else 4
            }
        }

        return repetition
    }
}
