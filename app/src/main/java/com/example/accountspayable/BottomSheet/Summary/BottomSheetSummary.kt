package com.example.accountspayable.BottomSheet

import android.view.KeyEvent
import android.view.KeyEvent.ACTION_DOWN
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.accountspayable.BottomSheet.Summary.BottomSheetSummaryViewModel
import com.example.accountspayable.Components.FKButtonProgress
import com.example.accountspayable.List.Cards.Summary.CardSummaryViewModel
import com.example.accountspayable.MainActivityViewModel
import com.example.accountspayable.R
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BottomSheetSummary(
    isEdit: Boolean = false,
    id: Int = 0,
    revenue: String = "",
    person1: String = "",
    person2: String = "",
    person3: String = "",
    person4: String = "",
    callBack: () -> Unit
){

    val model: BottomSheetSummaryViewModel = koinViewModel()
    val modelCard: CardSummaryViewModel = koinViewModel()
    val activityViewModel: MainActivityViewModel = koinViewModel()

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(true){

        model.onAppearBtmSheetSummary(
            isEdit = isEdit,
            revenue = revenue,
            person1 = person1,
            person2 = person2,
            person3 = person3,
            person4 = person4,
        )

    }

   Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
    ) {

        OutlinedTextField(
            value = model.state.revenue.value,
            onValueChange = {
                if(it.isNotEmpty() && it[it.length - 1] == ',') {
                    model.state.revenue.value += "."
                } else {
                    model.state.revenue.value = it
                }
            },
            singleLine = true,
            label = { Text(text = "Receita") },
            placeholder =  { Text(text = "Adicione sua receita mensal") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Image(painter = painterResource(id = R.drawable.icon_cash), contentDescription = "", modifier = Modifier
                .width(24.dp)
                .height(24.dp)) }

        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Deseja adicionar quantas pessoas que vão dividir as contas com você esse mês?")

       OutlinedTextField(
           value = model.state.person1.value,
           onValueChange = {
               model.state.person1.value = it
           },
           singleLine = true,
           label = { Text(text = "Pessoa 1") },
           placeholder =  { Text(text = "Adicione primeira pessoa") },
           keyboardOptions = KeyboardOptions.Default.copy(KeyboardCapitalization.Words, keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
           modifier = Modifier.fillMaxWidth(),
           leadingIcon = { Image(painter = painterResource(id = R.drawable.icon_person), contentDescription = "", modifier = Modifier
               .width(24.dp)
               .height(24.dp)) }

       )

       OutlinedTextField(
           value = model.state.person2.value,
           onValueChange = {
               model.state.person2.value = it
           },
           singleLine = true,
           label = { Text(text = "Pessoa 2") },
           placeholder =  { Text(text = "Adicione segunda pessoa") },
           keyboardOptions = KeyboardOptions.Default.copy(KeyboardCapitalization.Words, keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
           modifier = Modifier.fillMaxWidth(),
           leadingIcon = { Image(painter = painterResource(id = R.drawable.icon_person), contentDescription = "", modifier = Modifier
               .width(24.dp)
               .height(24.dp)) }

       )

       OutlinedTextField(
           value = model.state.person3.value,
           onValueChange = {
               model.state.person3.value = it
           },
           singleLine = true,
           label = { Text(text = "Pessoa 3") },
           placeholder =  { Text(text = "Adicione terceira pessoa") },
           keyboardOptions = KeyboardOptions.Default.copy(KeyboardCapitalization.Words, keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
           modifier = Modifier.fillMaxWidth(),
           leadingIcon = { Image(painter = painterResource(id = R.drawable.icon_person), contentDescription = "", modifier = Modifier
               .width(24.dp)
               .height(24.dp)) }

       )

       OutlinedTextField(
           value = model.state.person4.value,
           onValueChange = {
               model.state.person4.value = it
           },
           singleLine = true,
           label = { Text(text = "Pessoa 4") },
           placeholder =  { Text(text = "Adicione quarta pessoa") },
           keyboardOptions = KeyboardOptions.Default.copy(KeyboardCapitalization.Words, keyboardType = KeyboardType.Text),
           modifier = Modifier.fillMaxWidth(),
           leadingIcon = { Image(painter = painterResource(id = R.drawable.icon_person), contentDescription = "", modifier = Modifier
               .width(24.dp)
               .height(24.dp)) }

       )

       Spacer(modifier = Modifier.height(16.dp))

       FKButtonProgress(
           bgColor = MaterialTheme.colors.primary,
           textColor = MaterialTheme.colors.onSecondary,
           textButton = model.state.textButton.value,
           isProgress = model.state.progressBtn.value
       ) {

           scope.launch {

               if(model.state.textButton.value == "SALVAR") {

                   model.addSummary(
                       context = context,
                       month = activityViewModel.monthSelected.value ?: 1,
                       year = activityViewModel.yearSelected.value ?: 2023,
                       onSuccess = {
                           scope.launch {
                               modelCard.onAppearCardSummary(
                                   context = context,
                                   year = activityViewModel.yearSelected.value ?: 2023,
                                   month = activityViewModel.monthSelected.value ?: 1
                               )
                           }
                           callBack.invoke()
                       }
                   )

               } else {

                    model.editSummary(
                        id = id,
                        context = context,
                        onSuccess = {
                            activityViewModel.resetCardSummary.value = true
                            callBack.invoke()
                        }
                    )

               }


           }


       }

       Spacer(modifier = Modifier.height(4.dp))

       Button(
           onClick = { callBack.invoke() },
           modifier = Modifier.fillMaxWidth(),
           colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
       ) {
           Text(text = "FECHAR", color = Color.White)
       }


    }

}

@Preview
@Composable
fun ShowBottomSheetAddSummary(){
    BottomSheetSummary(
        callBack = {}
    )
}