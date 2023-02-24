package com.example.kata.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun MyTechCard(image: Int, contentDescription: String, techName: String, brand: String) {

    Card(
        modifier = Modifier.padding(8.dp),
        border = BorderStroke(3.dp, Color.DarkGray),
        shape = RoundedCornerShape(15.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {

            Row {
                Image(
                    painter = painterResource(id = image),
                    contentDescription = contentDescription,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(8.dp)
                )

                Column {
                    Text(
                        text = techName,
                        style = MaterialTheme.typography.h5
                    )
                    Text(
                        text = brand,
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }
}
