package com.example.accountspayable.List

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.accountspayable.MainActivityViewModel
import org.koin.androidx.compose.koinViewModel
import com.example.accountspayable.R

@Composable
fun ListAccountsPayable(){

    val model: ListAccountsPayableViewModel = koinViewModel()
    val activityViewModel: MainActivityViewModel = koinViewModel()
    val items by model.state.dataItem.collectAsState(initial = emptyList())
    val context = LocalContext.current
    val listState = rememberLazyListState()

    LaunchedEffect(listState.canScrollForward, listState.canScrollBackward){
        activityViewModel.showFloatingActionButton.value =
            !(!listState.canScrollForward && listState.canScrollBackward)
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .testTag(context.getString(R.string.column_list_tag)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if(activityViewModel.isLoading.value){

            CircularProgressIndicator(
                modifier = Modifier.testTag(context.getString(R.string.progress_tag))
            )

        } else {

            CardSummary()

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight()
                    .testTag(context.getString(R.string.lazy_column_list_tag)),
                state = listState
            ) {

                items(items = items,
                    itemContent = { item ->
                        CardItemPayable(
                            idCard = item.id,
                            icon = item.icon,
                            title = item.itemName,
                            valor = item.price,
                            descricao = item.description,
                            person1 = item.person1,
                            check1 = item.checkedPerson1,
                            person2 = item.person2,
                            check2 = item.checkedPerson2,
                            person3 = item.person3,
                            check3 = item.checkedPerson3,
                            person4 = item.person4,
                            check4 = item.checkedPerson4,
                            vencimento = if (item.vencimento > 0) {
                                item.vencimento.toString()
                            } else {
                                ""
                            }
                        )
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