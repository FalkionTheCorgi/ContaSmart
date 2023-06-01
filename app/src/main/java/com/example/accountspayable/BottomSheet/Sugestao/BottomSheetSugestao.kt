package com.example.accountspayable.BottomSheet.Sugestao

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel

@Composable
fun BottomSheetSugestao(
    callBack: () -> Unit
){

    val text = rememberSaveable { mutableStateOf("") }
    val model : BottomSheetSugestaoViewModel = koinViewModel()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Sugestão/Dúvida",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.secondary
        )

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            OutlinedTextField(
                value = text.value,
                placeholder =  { Text(text = "Mensagem")},
                label = { Text(text = "Mensagem")},
                onValueChange = { text.value = it }, modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(10.dp),
                keyboardOptions = KeyboardOptions(KeyboardCapitalization.Words)
            )

        }

        Button(
            onClick = {
                model.sendEmail(context = context,
                    message = text.value
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
        ) {
            Text(text = "ENVIAR", color = MaterialTheme.colors.onSecondary)
        }

        Button(
            onClick = { callBack.invoke() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error)
        ) {
            Text(text = "FECHAR", color = MaterialTheme.colors.onSecondary)
        }

    }

}

@Composable
@Preview
fun PreviewBottomSheetSugestao(){

    BottomSheetSugestao(
        callBack = {}
    )

}