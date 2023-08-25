package com.example.accountspayable.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FKButtonProgress(
    textColor: Color,
    textButton: String,
    isProgress: Boolean = false,
    modifier: Modifier,
){

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        if(isProgress) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .width(24.dp),
                color = textColor
            )
        }

        Text(
            modifier = Modifier
                .padding(
                    top = 6.dp,
                    start = if (!isProgress ){ 0.dp } else { 2.dp }
                )
            ,
            text = textButton,
            color = textColor,
            fontSize = 15.sp
        )

    }



}

@Preview
@Composable
fun ShowFKButtonProgress(){

    FKButtonProgress(
        textColor = Color.White,
        textButton = "CLICK ME",
        isProgress = false,
        modifier = Modifier
            .fillMaxWidth()
            .height(37.dp)
            .background(color = MaterialTheme.colors.primary, shape = RoundedCornerShape(4.dp))
            .clickable { }
    )

}