package com.example.accountspayable.List

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.accountspayable.*
import com.example.accountspayable.List.Cards.Item.CardItemPayableViewModel
import com.example.accountspayable.R
import com.example.accountspayable.Room.Data.DataItem
import com.example.accountspayable.Room.Data.MonthYear
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get
import org.koin.androidx.compose.koinViewModel


@SuppressLint("SuspiciousIndentation")
@Composable
fun CardItemPayable(
    idCard: Int,
    icon: String,
    title: String,
    valor: Double,
    descricao: String,
    vencimento: String,
    person1: String,
    check1: Boolean,
    person2: String,
    check2: Boolean,
    person3: String,
    check3: Boolean,
    person4: String,
    check4: Boolean
){

    var expandItem by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val cardModel: CardItemPayableViewModel = koinViewModel()
    val model: ListAccountsPayableViewModel = koinViewModel()
    val activityViewModel: MainActivityViewModel = koinViewModel()

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
            modifier = Modifier.padding(15.dp)
        ) {
            Row{

                Image(
                    modifier = Modifier
                        .width(32.dp)
                        .height(32.dp),
                    painter = getPainterIcon(str = icon),
                    contentDescription = stringResource(id = R.string.choose_icon_item, getTypeIcon(
                        str = icon
                    ))
                )

                Text(
                    modifier = Modifier.padding(top = 6.dp, start = 4.dp),
                    text = title,
                    color = MaterialTheme.colors.secondary
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    modifier = Modifier.padding(top = 6.dp),
                    text = stringResource(id = R.string.price_item, "R$", String.format("%.2f", valor)),
                    color = MaterialTheme.colors.secondary
                )

            }

            AnimatedVisibility(visible = expandItem) {
                Column {

                    Spacer(modifier = Modifier.padding(vertical = 8.dp))

                    if(vencimento.isNotEmpty()){

                        Row(modifier = Modifier.fillMaxWidth()) {

                            Image(
                                painter = painterResource(id = R.drawable.icon_deadline),
                                contentDescription = stringResource(id = R.string.icon_deadline),
                                modifier = Modifier
                                    .width(28.dp)
                                    .height(28.dp)
                                    .padding(start = 6.dp),
                            )

                            Spacer(modifier = Modifier.width(2.dp))

                            Text(
                                modifier = Modifier.padding(start = 6.dp, top =6.dp),
                                text = stringResource(id = R.string.day_deadline, vencimento),
                                color = MaterialTheme.colors.secondary
                            )
                        }

                        Spacer(modifier = Modifier.padding(vertical = 8.dp))

                    }



                    if (descricao.isNotEmpty()) {

                        Row(modifier = Modifier.fillMaxWidth()) {

                            Image(
                                painter = painterResource(id = R.drawable.icon_descricao),
                                contentDescription = stringResource(id = R.string.icon_description),
                                modifier = Modifier
                                    .width(28.dp)
                                    .height(28.dp)
                                    .padding(start = 4.dp)
                            )

                            Spacer(modifier = Modifier.width(2.dp))

                            Text(
                                modifier = Modifier.padding(start = 6.dp, top =6.dp),
                                text = stringResource(id = R.string.description_item, descricao),
                                color = MaterialTheme.colors.secondary
                            )
                        }

                        Spacer(modifier = Modifier.padding(vertical = 8.dp))

                    }

                    if(person1.isNotEmpty() ||
                        person2.isNotEmpty() ||
                        person3.isNotEmpty() ||
                        person4.isNotEmpty()
                    ) {
                        Dividido(
                            idCard = idCard,
                            person1 = person1,
                            person2 = person2,
                            person3 = person3,
                            person4 = person4
                        )

                        Spacer(modifier = Modifier.padding(vertical = 4.dp))
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        TextButton(contentPadding = PaddingValues(0.dp), onClick = {
                            scope.launch {
                                activityViewModel.editCardObj.value = DataItem(
                                    id = idCard,
                                    name = title,
                                    valor = valor,
                                    descricao = descricao,
                                    icon = icon,
                                    person1 = person1,
                                    check1 = check1,
                                    person2 = person2,
                                    check2 = check2,
                                    person3 = person3,
                                    check3 = check3,
                                    person4 = person4,
                                    check4 = check4,
                                    mYear = MonthYear(
                                        month = 0,
                                        year = 0,
                                        vencimento = if(vencimento.isNotEmpty()) { vencimento.toInt() } else { 0 }
                                    )
                                )
                                activityViewModel.bottomSheetType.value =
                                    BottomSheetTypes.EDIT
                            }
                        }) {
                            Text(stringResource(id = R.string.edit), color = MaterialTheme.colors.primary)
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        TextButton(onClick = {
                            scope.launch {
                                model.deleteItem(
                                    context = context,
                                    id = idCard
                                )
                            }
                        }) {
                            Text(stringResource(id = R.string.delete), color = MaterialTheme.colors.primary)
                        }

                    }

                }
            }
        }

    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Dividido(
    idCard: Int,
    person1: String,
    person2: String,
    person3: String,
    person4: String
){

    val model: CardItemPayableViewModel = get()

    val scope = rememberCoroutineScope()
    val context =  LocalContext.current

    LaunchedEffect(true){

        model.fillCheckPerson(
            id = idCard,
            context = context
        )

    }

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {

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
            modifier = Modifier.padding(start = 3.dp, top = 6.dp),
            color = MaterialTheme.colors.secondary
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = stringResource(id = R.string.group_people_status),
            modifier = Modifier.padding(top = 6.dp),
            color = MaterialTheme.colors.secondary
        )

    }

    Spacer(modifier = Modifier.height(8.dp))

    if(
        person1.isNotEmpty()
    ) {

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
                text = person1.replaceFirstChar(Char::uppercase),
                modifier = Modifier.padding(top = 2.dp, start = 2.dp),
                color = MaterialTheme.colors.secondary
            )

            Spacer(modifier = Modifier.weight(1f))

            CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
                Checkbox(
                    checked = model.state.checkBoxPerson1.value,
                    onCheckedChange = {
                        model.state.checkBoxPerson1.value = it
                        scope.launch {
                            model.checkBoxUpdateCard(
                                context = context,
                                id = idCard
                            )
                            Log.d("ENDEREÇO CHECKBOX", model.state.checkBoxPerson1.toString())
                        }
                    },
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }

    }

    if(
        person2.isNotEmpty()
    ) {
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
                text = person2.replaceFirstChar(Char::uppercase),
                modifier = Modifier.padding(top = 2.dp, start = 2.dp),
                color = MaterialTheme.colors.secondary
            )

            Spacer(modifier = Modifier.weight(1f))

            CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {

                Checkbox(
                    checked = model.state.checkBoxPerson2.value,
                    onCheckedChange = {
                        model.state.checkBoxPerson2.value = it
                        scope.launch {
                            model.checkBoxUpdateCard(
                                context = context,
                                id = idCard
                            )
                        }
                    },
                    modifier = Modifier.padding(end = 8.dp)
                )

            }


        }
    }

    if(
        person3.isNotEmpty()
    ) {
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
                text = person3.replaceFirstChar(Char::uppercase),
                modifier = Modifier.padding(top = 2.dp, start = 2.dp),
                color = MaterialTheme.colors.secondary
            )

            Spacer(modifier = Modifier.weight(1f))

            CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {

                Checkbox(
                    checked = model.state.checkBoxPerson3.value,
                    onCheckedChange = {
                        model.state.checkBoxPerson3.value = it
                        scope.launch {
                            model.checkBoxUpdateCard(
                                context = context,
                                id = idCard
                            )
                        }
                    },
                    modifier = Modifier.padding(end = 8.dp)
                )

            }

        }
    }
    if(
        person4.isNotEmpty()
    ) {
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
                text = person4.replaceFirstChar(Char::uppercase),
                modifier = Modifier.padding(top = 2.dp, start = 2.dp),
                color = MaterialTheme.colors.secondary
            )

            Spacer(modifier = Modifier.weight(1f))

            CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {

                Checkbox(
                    checked = model.state.checkBoxPerson4.value,
                    onCheckedChange = {
                        model.state.checkBoxPerson4.value = it
                        scope.launch {
                            model.checkBoxUpdateCard(
                                context = context,
                                id = idCard
                            )
                        }
                    },
                    modifier = Modifier.padding(end = 8.dp)
                )

            }

        }
    }


}

@Preview
@Composable
fun ShowCardItemPayable(){

    CardItemPayable(
        idCard = 0,
        icon = "Market",
        title = "Título",
        valor = 12.60,
        descricao = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book",
        person1 = "teste",
        check1 = false,
        person2 = "teste",
        check2 = false,
        person3 = "teste",
        check3 = false,
        person4 = "teste",
        check4 = false,
        vencimento = ""
    )

}