package com.example.accountspayable.BottomSheet.Donation

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.accountspayable.Payment.Payment
import org.koin.androidx.compose.get
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.inject

@Composable
fun BottomSheetDonation(
    payment: Payment,
    callBack: () -> Unit
){

    val donation = remember{
        mutableStateOf(DonationType.Donate5)
    }
    val model : BottomSheetDonationViewModel = koinViewModel()
    val radioOptions = listOf(DonationType.Donate5, DonationType.Donate10, DonationType.Donate20, DonationType.Donate50, DonationType.Donate100)
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0] ) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Doação",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = MaterialTheme.colors.secondary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Olá! Estamos em busca de apoio para o nosso aplicativo que tem como objetivo ajudar as pessoas a organizarem suas contas de forma prática e eficiente. Se você acredita na importância da educação financeira e deseja contribuir para que mais pessoas tenham uma vida financeira mais saudável, pedimos a sua generosa doação. Com o seu suporte, poderemos continuar desenvolvendo e aprimorando essa ferramenta que pode fazer a diferença na vida de muitas pessoas. Junte-se a nós nessa missão. Sua contribuição é fundamental para o sucesso do nosso projeto. Muito obrigado",
            color = MaterialTheme.colors.secondary
        )

        Spacer(modifier = Modifier.height(16.dp))


            Row(
                Modifier
                    .fillMaxWidth()
            ) {
                radioOptions.forEach { text ->
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text) }
                    )
                    Text(
                        text = model.returnNumberDonationType(text),
                        style = MaterialTheme.typography.body1.merge(),
                        modifier = Modifier.padding(top = 11.dp),
                        color = MaterialTheme.colors.secondary
                    )
                }
            }


        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                payment.estabilishedConnection(
                    selectedOption
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
        ) {
            Text(text = "DOAR", color = MaterialTheme.colors.onSecondary)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { callBack.invoke() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error)
        ) {
            Text(text = "FECHAR", color = MaterialTheme.colors.onSecondary)
        }

    }

}

/*@Composable
@Preview
fun PreviewBottomSheetDonation(){

    BottomSheetDonation(
        activity = Activity(),
        callBack = {}
    )

}*/