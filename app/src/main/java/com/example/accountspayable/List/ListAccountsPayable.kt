package com.example.accountspayable.List

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.accountspayable.MainActivityViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ListAccountsPayable(){

    val model: ListAccountsPayableViewModel = koinViewModel()
    val activityViewModel: MainActivityViewModel = koinViewModel()
    val context = LocalContext.current

    LaunchedEffect(key1 =  model.state.dataItem){

        model.onAppearScreen(
            context,
            month = activityViewModel.monthSelected.value ?: 1,
            year = activityViewModel.yearSelected.value ?: 2023
        )

    }

    LaunchedEffect(activityViewModel.resetCardSummary.value){

        if (activityViewModel.resetCardSummary.value) {

            model.onAppearScreen(
                context,
                month = activityViewModel.monthSelected.value ?: 1,
                year = activityViewModel.yearSelected.value ?: 2023
            )

        }

    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if(activityViewModel.isLoading.value){

            CircularProgressIndicator()

        } else {
            CardSummary()

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxHeight()
            ) {


                items(items = model.state.dataItem,
                    itemContent = { item ->

                        if (item != null) {

                            CardItemPayable(
                                idCard = item.id,
                                icon = item.icon,
                                title = item.name,
                                valor = item.valor,
                                descricao = item.descricao,
                                person1 = item.person1,
                                check1 = item.check1,
                                person2 = item.person2,
                                check2 = item.check2,
                                person3 = item.person3,
                                check3 = item.check3,
                                person4 = item.person4,
                                check4 = item.check4,
                                vencimento = if (item.mYear.vencimento > 0) {
                                    item.mYear.vencimento.toString()
                                } else {
                                    ""
                                }
                            )

                        }

                    }
                )

            }

        }

    }



}

@Preview
@Composable
fun PreviewListAccountsPayable(){

    ListAccountsPayable()

}