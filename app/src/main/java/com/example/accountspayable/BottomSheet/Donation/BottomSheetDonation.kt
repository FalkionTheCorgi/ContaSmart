package com.example.accountspayable.BottomSheet.Donation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.accountspayable.Payment.Payment
import org.koin.androidx.compose.koinViewModel
import com.example.accountspayable.R

@Composable
fun BottomSheetDonation(
    payment: Payment,
    callBack: () -> Unit
){

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
            text = stringResource(id = R.string.bottomsheet_donation_title),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = MaterialTheme.colors.secondary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.bottomsheet_donation_subtitle),
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
            Text(text = stringResource(id = R.string.btn_donate), color = MaterialTheme.colors.onSecondary)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { callBack.invoke() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error)
        ) {
            Text(text = stringResource(id = R.string.btn_close), color = MaterialTheme.colors.onSecondary)
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