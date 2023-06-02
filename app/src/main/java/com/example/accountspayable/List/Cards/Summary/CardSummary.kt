package com.example.accountspayable.List

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.accountspayable.*
import com.example.accountspayable.List.Cards.Item.CardItemPayableViewModel
import com.example.accountspayable.List.Cards.Summary.AlertDialogSummary
import com.example.accountspayable.List.Cards.Summary.CardSummaryViewModel
import com.example.accountspayable.R
import com.example.accountspayable.Room.Data.DataSummary
import com.example.accountspayable.Room.Data.MonthYear
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun CardSummary() {

    val model: CardSummaryViewModel = koinViewModel()
    val activityViewModel: MainActivityViewModel = koinViewModel()
    val context = LocalContext.current

    LaunchedEffect(true){

        model.onAppearCardSummary(
            context = context,
            year = activityViewModel.yearSelected.value ?: 2023,
            month = activityViewModel.monthSelected.value ?: 1
        )

    }

    LaunchedEffect(activityViewModel.resetCardSummary.value){

        if (activityViewModel.resetCardSummary.value){

            model.onAppearCardSummary(
                context = context,
                month = activityViewModel.monthSelected.value ?: 1,
                year = activityViewModel.yearSelected.value ?: 2023
            )

            activityViewModel.resetCardSummary.value = false
        }


    }

    LaunchedEffect(activityViewModel.updateSummaryPerson1.value){

        if(activityViewModel.updateSummaryPerson1.value){

            model.updateValueOfPerson1(
                context = context,
                year = activityViewModel.yearSelected.value ?: 2023,
                month = activityViewModel.monthSelected.value ?: 1
            )

            activityViewModel.updateSummaryPerson1.value = false

        }

    }

    LaunchedEffect(activityViewModel.updateSummaryPerson2.value){

        if(activityViewModel.updateSummaryPerson2.value){

            model.updateValueOfPerson2(
                context = context,
                year = activityViewModel.yearSelected.value ?: 2023,
                month = activityViewModel.monthSelected.value ?: 1
            )

            activityViewModel.updateSummaryPerson2.value = false

        }

    }

    LaunchedEffect(activityViewModel.updateSummaryPerson3.value){

        if(activityViewModel.updateSummaryPerson3.value){

            model.updateValueOfPerson3(
                context = context,
                year = activityViewModel.yearSelected.value ?: 2023,
                month = activityViewModel.monthSelected.value ?: 1
            )

            activityViewModel.updateSummaryPerson3.value = false

        }

    }

    LaunchedEffect(activityViewModel.updateSummaryPerson4.value){

        if(activityViewModel.updateSummaryPerson4.value){

            model.updateValueOfPerson4(
                context = context,
                year = activityViewModel.yearSelected.value ?: 2023,
                month = activityViewModel.monthSelected.value ?: 1
            )

            activityViewModel.updateSummaryPerson4.value = false

        }

    }

    LaunchedEffect(activityViewModel.updateSummaryAllPerson.value){

        if(activityViewModel.updateSummaryAllPerson.value){

            model.updateAllValuesOfPerson(
                context = context,
                year = activityViewModel.yearSelected.value ?: 2023,
                month = activityViewModel.monthSelected.value ?: 1
            )

            activityViewModel.updateSummaryAllPerson.value = false

        }

    }

    if(model.state.dataSummary.isNotEmpty()) {

        CardSummaryExist(
            year = activityViewModel.yearSelected.value.toString(),
            month = activityViewModel.monthSelected.value.toString()
        )

    } else {

        CardSummaryNotExist()

    }



}

@Composable
fun CardSummaryNotExist(){

    val model: MainActivityViewModel = koinViewModel()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        elevation = 10.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "Sumário mensal",
                    modifier = Modifier.padding(top = 13.dp),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.secondary
                )

                Spacer(modifier = Modifier.weight(1f))

                TextButton(onClick = {
                    model.bottomSheetType.value = BottomSheetTypes.SUMMARYADD
                }) {

                    Text(text = "CADASTRAR", color = MaterialTheme.colors.primary)

                }

            }

        }

    }

}

@Composable
fun CardSummaryExist(
    year: String,
    month: String
){

    var expandItem by remember {
        mutableStateOf(false)
    }

    val model: CardSummaryViewModel = koinViewModel()
    val listModel: ListAccountsPayableViewModel = koinViewModel()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable {
                expandItem = !expandItem
            },
        elevation = 10.dp
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(15.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                Image(
                    painter = painterResource(id = R.drawable.icon_financial),
                    contentDescription = "",
                    modifier = Modifier
                        .width(24.dp)
                        .height(24.dp)
                )

                Spacer(modifier = Modifier.width(2.dp))

                Text(
                    text = "Sumário",
                    modifier = Modifier.padding(top = 3.dp),
                    color = MaterialTheme.colors.secondary
                )
                
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "${returnMonthString(month.toInt())}/$year",
                    modifier = Modifier.padding(top = 3.dp),
                    color = MaterialTheme.colors.secondary
                )

            }

            AnimatedVisibility(visible = expandItem) {

                Column(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Icon(
                            imageVector = Icons.Default.ArrowUpward,
                            contentDescription = "",
                            tint = Color.Green
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "Receita: ${model.state.dataSummary.first().revenue}",
                            modifier = Modifier.padding(top = 3.dp),
                            color = MaterialTheme.colors.secondary
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {

                        Icon(
                            imageVector = Icons.Default.ArrowDownward,
                            contentDescription = "",
                            tint = Color.Red
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "Despesas: ${listModel.sumExpenditure()}",
                            modifier = Modifier.padding(top = 3.dp),
                            color = MaterialTheme.colors.secondary
                        )

                    }



                    Spacer(modifier = Modifier.height(8.dp))
                    if (
                        model.state.dataSummary.first().person1.isNotEmpty() ||
                        model.state.dataSummary.first().person2.isNotEmpty() ||
                        model.state.dataSummary.first().person3.isNotEmpty() ||
                        model.state.dataSummary.first().person4.isNotEmpty()
                    ) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Image(
                                painter = painterResource(id = R.drawable.icon_people),
                                contentDescription = "",
                                modifier = Modifier
                                    .width(32.dp)
                                    .height(32.dp)
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = "Divido com: ",
                                modifier = Modifier.padding(top = 6.dp),
                                color = MaterialTheme.colors.secondary
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = "Valor",
                                modifier = Modifier.padding(top = 6.dp, end = 10.dp),
                                color = MaterialTheme.colors.secondary
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        People()
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    TextButtonsEditDelete()
                }
            }

        }


    }

}

@Composable
fun TextButtonsEditDelete(){

    val openDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val activityViewModel : MainActivityViewModel = koinViewModel()
    val model : CardSummaryViewModel = koinViewModel()

    Row(
        modifier = Modifier.fillMaxWidth()
    ){

        TextButton(onClick = {
            activityViewModel.editCardSummary.value = DataSummary(
                id = model.state.dataSummary.first().id,
                revenue = model.state.dataSummary.first().revenue,
                person1 = model.state.dataSummary.first().person1,
                person2 = model.state.dataSummary.first().person2,
                person3 = model.state.dataSummary.first().person3,
                person4 = model.state.dataSummary.first().person4,
                mYear = model.state.dataSummary.first().mYear
            )
            activityViewModel.bottomSheetType.value = BottomSheetTypes.SUMMARYEDIT
        }) {

            Text(text = "EDITAR", color = MaterialTheme.colors.primary)

        }

        Spacer(modifier = Modifier.weight(1f))

        TextButton(onClick = { openDialog.value = true }) {

            Text(text = "DELETAR", color = MaterialTheme.colors.primary)

        }

    }

    if(openDialog.value) {

        AlertDialogSummary(
            accept = {
                scope.launch {
                    model.deleteSummaryAndItens(
                        context = context,
                        month = activityViewModel.monthSelected.value ?: 1,
                        year = activityViewModel.yearSelected.value ?: 2022,
                        success = {
                            activityViewModel.resetCardSummary.value = true
                        }
                    )
                    openDialog.value = false
                }
            },
            decline = {
                openDialog.value = false
            }
        )

    }

}

@Composable
fun People(){

    val model: CardSummaryViewModel = koinViewModel()

    Column(modifier = Modifier.fillMaxWidth()) {

        if(model.state.dataSummary.first().person1.isNotEmpty()) {

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                Image(painter = painterResource(id = R.drawable.icon_person), contentDescription = "", modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .padding(start = 8.dp))

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = model.state.dataSummary.first().person1.replaceFirstChar(Char::uppercase),
                    modifier = Modifier.padding(top = 2.dp),
                    color = MaterialTheme.colors.secondary
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = model.state.priceOfPerson1.value.toString(),
                    modifier = Modifier.padding(top = 2.dp, end = 16.dp),
                    color = MaterialTheme.colors.secondary
                )

            }

        }

        if(model.state.dataSummary.first().person2.isNotEmpty()) {

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                Image(painter = painterResource(id = R.drawable.icon_person), contentDescription = "", modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .padding(start = 8.dp))

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = model.state.dataSummary.first().person2.replaceFirstChar(Char::uppercase),
                    modifier = Modifier.padding(top = 2.dp),
                    color = MaterialTheme.colors.secondary
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = model.state.priceOfPerson2.value.toString(),
                    modifier = Modifier.padding(top = 2.dp, end = 16.dp),
                    color = MaterialTheme.colors.secondary
                )

            }

        }

        if(model.state.dataSummary.first().person3.isNotEmpty()) {

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                Image(painter = painterResource(id = R.drawable.icon_person), contentDescription = "", modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .padding(start = 8.dp))

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = model.state.dataSummary.first().person3.replaceFirstChar(Char::uppercase),
                    modifier = Modifier.padding(top = 2.dp),
                    color = MaterialTheme.colors.secondary
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = model.state.priceOfPerson3.value.toString(),
                    modifier = Modifier.padding(top = 2.dp, end = 16.dp),
                    color = MaterialTheme.colors.secondary
                )

            }

        }

        if(model.state.dataSummary.first().person4.isNotEmpty()) {

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                Image(painter = painterResource(id = R.drawable.icon_person), contentDescription = "", modifier = Modifier
                    .width(20.dp)
                    .height(20.dp)
                    .padding(start = 8.dp))

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = model.state.dataSummary.first().person4.replaceFirstChar(Char::uppercase),
                    modifier = Modifier.padding(top = 2.dp),
                    color = MaterialTheme.colors.secondary
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = model.state.priceOfPerson4.value.toString(),
                    modifier = Modifier.padding(top = 2.dp, end = 16.dp),
                    color = MaterialTheme.colors.secondary
                )

            }

        }

    }



}

@Preview
@Composable
fun ShowCardSummary(){

    CardSummary()

}