package com.example.accountspayable.List

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.accountspayable.*
import com.example.accountspayable.Data.GlobalVariables
import com.example.accountspayable.List.Cards.Summary.AlertDialogSummary
import com.example.accountspayable.List.Cards.Summary.CardSummaryViewModel
import com.example.accountspayable.R
import com.example.accountspayable.Room.Data.DataSummary
import com.example.accountspayable.Room.Data.MonthYear
import com.example.accountspayable.Room.DataBase
import com.example.accountspayable.Room.Summary.SummaryEntity
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun CardSummary() {

    val model: CardSummaryViewModel = koinViewModel()
    val context = LocalContext.current
    val card by model.state.dataSummary.collectAsState(initial = null)

    LaunchedEffect(
        key1 = GlobalVariables.monthSelected.value,
        GlobalVariables.yearSelected.value
    ){

        model.state.dataSummary.transform {
            emit(it)
            card = it
        }

    }

    if(card != null) {

        card?.let {
            CardSummaryExist(
                year = GlobalVariables.yearSelected.value.toString(),
                month = GlobalVariables.monthSelected.value.toString(),
                obj = it
            )
        }

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
                    text = stringResource(id = R.string.summary_title_without),
                    modifier = Modifier.padding(top = 13.dp),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.secondary
                )

                Spacer(modifier = Modifier.weight(1f))

                TextButton(onClick = {
                    model.bottomSheetType.value = BottomSheetTypes.SUMMARYADD
                }) {

                    Text(text = stringResource(id = R.string.btn_register), color = MaterialTheme.colors.primary)

                }

            }

        }

    }

}

@Composable
fun CardSummaryExist(
    year: String,
    month: String,
    obj: SummaryEntity
){

    var expandItem by remember {
        mutableStateOf(false)
    }

    val model: CardSummaryViewModel = koinViewModel()
    val context = LocalContext.current
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
                    text = stringResource(id = R.string.summary_title),
                    modifier = Modifier.padding(top = 3.dp),
                    color = MaterialTheme.colors.secondary
                )
                
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = stringResource(id = R.string.summary_month_year, returnMonthString(context, month.toInt()), year),
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
                            contentDescription = stringResource(id = R.string.icon_summary_receipt),
                            tint = Color.Green
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = stringResource(id = R.string.summary_receipt,String.format("%.2f", obj.revenue)),
                            modifier = Modifier.padding(top = 3.dp),
                            color = MaterialTheme.colors.secondary
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {

                        Icon(
                            imageVector = Icons.Default.ArrowDownward,
                            contentDescription = stringResource(id = R.string.icon_summary_expenditure),
                            tint = Color.Red
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = stringResource(id = R.string.summary_expenditure, String.format("%.2f", listModel.sumExpenditure())),
                            modifier = Modifier.padding(top = 3.dp),
                            color = MaterialTheme.colors.secondary
                        )

                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {

                        Image(
                            painter = painterResource(id = R.drawable.icon_cash),
                            contentDescription = stringResource(id = R.string.icon_summary_receipt),
                            modifier = Modifier
                                .width(24.dp)
                                .height(24.dp)
                                .padding(3.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = stringResource(id = R.string.summary_remaining, model.revenueLess(listModel.sumExpenditure())) ,
                            modifier = Modifier.padding(top = 3.dp),
                            color = MaterialTheme.colors.secondary
                        )

                    }



                    Spacer(modifier = Modifier.height(8.dp))
                    if (
                        obj.person1.isNotEmpty() ||
                        obj.person2.isNotEmpty() ||
                        obj.person3.isNotEmpty() ||
                        obj.person4.isNotEmpty()
                    ) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Image(
                                painter = painterResource(id = R.drawable.icon_people),
                                contentDescription = stringResource(id = R.string.icon_group_people),
                                modifier = Modifier
                                    .width(32.dp)
                                    .height(32.dp)
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = stringResource(id = R.string.group_people_text),
                                modifier = Modifier.padding(top = 6.dp),
                                color = MaterialTheme.colors.secondary
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = stringResource(id = R.string.summary_value),
                                modifier = Modifier.padding(top = 6.dp, end = 10.dp),
                                color = MaterialTheme.colors.secondary
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        People(
                            obj
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    TextButtonsEditDelete(
                        obj
                    )
                }
            }

        }


    }

}

@Composable
fun TextButtonsEditDelete(
    obj: SummaryEntity
){

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
                id = obj.id,
                revenue = obj.revenue,
                person1 = obj.person1,
                person2 = obj.person2,
                person3 = obj.person3,
                person4 = obj.person4,
                mYear = MonthYear(
                    month = obj.month,
                    year = obj.year,
                    vencimento = 0
                )
            )
            activityViewModel.bottomSheetType.value = BottomSheetTypes.SUMMARYEDIT
        }) {

            Text(text = stringResource(id = R.string.edit), color = MaterialTheme.colors.primary)

        }

        Spacer(modifier = Modifier.weight(1f))

        TextButton(onClick = { openDialog.value = true }) {

            Text(text = stringResource(id = R.string.delete), color = MaterialTheme.colors.primary)

        }

    }

    if(openDialog.value) {

        AlertDialogSummary(
            accept = {
                scope.launch {
                    model.deleteSummaryAndItens(
                        context = context,
                        month = GlobalVariables.monthSelected.value ?: 1,
                        year = GlobalVariables.yearSelected.value ?: 2022,
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
fun People(
    obj: SummaryEntity
){

    val model: CardSummaryViewModel = koinViewModel()
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxWidth()) {

        if(obj.person1.isNotEmpty()) {

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                Image(
                    painter = painterResource(id = R.drawable.icon_person),
                    contentDescription = stringResource(id = R.string.icon_people),
                    modifier = Modifier
                        .width(24.dp)
                        .height(24.dp)
                        .padding(start = 8.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = obj.person1.replaceFirstChar(Char::uppercase),
                    modifier = Modifier.padding(top = 2.dp),
                    color = MaterialTheme.colors.secondary
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = model.returnValueOrStatus(context = context, model.state.priceOfPerson1.value),
                    modifier = Modifier.padding(top = 2.dp, end = 16.dp),
                    color = MaterialTheme.colors.secondary
                )

            }

        }

        if(obj.person2.isNotEmpty()) {

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                Image(
                    painter = painterResource(id = R.drawable.icon_person),
                    contentDescription = stringResource(id = R.string.icon_people),
                    modifier = Modifier
                        .width(24.dp)
                        .height(24.dp)
                        .padding(start = 8.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = obj.person2.replaceFirstChar(Char::uppercase),
                    modifier = Modifier.padding(top = 2.dp),
                    color = MaterialTheme.colors.secondary
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = model.returnValueOrStatus(context = context, model.state.priceOfPerson2.value),
                    modifier = Modifier.padding(top = 2.dp, end = 16.dp),
                    color = MaterialTheme.colors.secondary
                )

            }

        }

        if(obj.person3.isNotEmpty()) {

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                Image(
                    painter = painterResource(id = R.drawable.icon_person),
                    contentDescription = stringResource(id = R.string.icon_people),
                    modifier = Modifier
                        .width(24.dp)
                        .height(24.dp)
                        .padding(start = 8.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = obj.person3.replaceFirstChar(Char::uppercase),
                    modifier = Modifier.padding(top = 2.dp),
                    color = MaterialTheme.colors.secondary
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = model.returnValueOrStatus(context = context, model.state.priceOfPerson3.value),
                    modifier = Modifier.padding(top = 2.dp, end = 16.dp),
                    color = MaterialTheme.colors.secondary
                )

            }

        }

        if(obj.person4.isNotEmpty()) {

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                Image(
                    painter = painterResource(id = R.drawable.icon_person),
                    contentDescription = stringResource(id = R.string.icon_people),
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                        .padding(start = 8.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = obj.person4.replaceFirstChar(Char::uppercase),
                    modifier = Modifier.padding(top = 2.dp),
                    color = MaterialTheme.colors.secondary
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = model.returnValueOrStatus(context = context, model.state.priceOfPerson4.value),
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