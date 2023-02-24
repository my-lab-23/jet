package com.example.kata.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.kata.runPackage

@Composable
fun MyAppCard(name: String, description: String, packageName: String) {
    Card(
        modifier = Modifier.padding(8.dp),
        border = BorderStroke(3.dp, Color.DarkGray),
        shape = RoundedCornerShape(15.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(text = name, style = MaterialTheme.typography.h5)
            Text(text = description, style = MaterialTheme.typography.body2)
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                    border = BorderStroke(3.dp, Color.DarkGray),
                    modifier = Modifier.defaultMinSize(80.dp),
                    onClick = {
                        runPackage(packageName)
                    }) {
                    Text(text = "Run")
                }

                Spacer(modifier = Modifier.width(16.dp))

                OutlinedButton(
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                    border = BorderStroke(3.dp, Color.DarkGray),
                    modifier = Modifier.defaultMinSize(80.dp),
                    onClick = {
                        /* Do something */
                    }) {
                    Text(text = "Info")
                }
            }
        }
    }
}
