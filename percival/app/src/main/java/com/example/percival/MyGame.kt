package com.example.percival

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.percival.MyIO.load
import com.example.percival.MyIO.save
import com.example.percival.MyUI.visible

object MyGame {

    private fun check(context: Context): Boolean {
        val x = load(context, "data1")
        val y = load(context,"data2")
        save(context, "", "data2", Context.MODE_PRIVATE)
        return x==y
    }

    @SuppressLint("SetTextI18n")
    fun result(
        context: Context,
        b1: Button,
        tVL: List<TextView>,
        click: Int,
        repetition: Int
    ): Int {

        if(click==repetition) {
            val res = check(context)
            return if(res) {
                visible(tVL, false)
                b1.isVisible = true
                b1.text = "VINTO ${repetition+1}"
                repetition+1
            } else {
                visible(tVL, false)
                b1.isVisible = true
                if(repetition>4) b1.text = "PERSO ${repetition-1}"
                else b1.text = "PERSO 4"
                if(repetition>4) repetition-1
                else 4
            }
        }

        return repetition
    }
}
