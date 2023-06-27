package com.example.accountspayable.List

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.accountspayable.*
import com.example.accountspayable.BottomSheet.Item.BottomSheetViewModel
import com.example.accountspayable.Components.FKButtonProgress
import com.example.accountspayable.Data.GlobalVariables
import com.example.accountspayable.List.Cards.Summary.CardSummaryViewModel
import com.example.accountspayable.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun BottomSheetAddItem(
    idItem: Int = 0,
    idSummary: Int = 0,
    itemName: String = "",
    vencimento: String = "",
    description: String = "",
    price : String = "",
    person1 : String = "",
    person2 : String = "",
    person3 : String = "",
    person4 : String = "",
    check1 : Boolean = false,
    check2 : Boolean = false,
    check3 : Boolean = false,
    check4 : Boolean = false,
    iconSelected: String = "Light",
    isEdit : Boolean = false,
    callBack: () -> Unit
){

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        
        Spacer(modifier = Modifier.height(16.dp))

        AddItemScreen(
            idItem = idItem,
            idSummary = idSummary,
            iconSelected = iconSelected,
            itemName = itemName,
            description = description,
            vencimento = vencimento,
            price = price,
            person1 = person1,
            person2 = person2,
            person3 = person3,
            person4 = person4,
            check1 = check1,
            check2 = check2,
            check3 = check3,
            check4 = check4,
            isEdit = isEdit,
            callBack = { callBack.invoke() }
        )

    }

}

@Composable
fun AddItemScreen(
    idItem: Int = 0,
    idSummary: Int = 0,
    itemName: String = "",
    description: String = "",
    vencimento: String = "",
    price : String = "",
    person1 : String = "",
    person2 : String = "",
    person3 : String = "",
    person4 : String = "",
    check1 : Boolean = false,
    check2 : Boolean = false,
    check3 : Boolean = false,
    check4 : Boolean = false,
    iconSelected: String = "Light",
    isEdit : Boolean = false,
    callBack: () -> Unit
){

    val model: BottomSheetViewModel = koinViewModel()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val selectedOption = remember { mutableStateOf<String?>( null ) }
    val modelSummary : CardSummaryViewModel = koinViewModel()

    LaunchedEffect(true){

        model.onAppearBtmSheet(
            context = context,
            icon = iconSelected,
            itemName = itemName,
            price = price,
            description = description,
            isEdit = isEdit,
            vencimento = vencimento,
            person1 = modelSummary.state.dataSummary.value?.person1 ?: "" ,
            person2 = modelSummary.state.dataSummary.value?.person2 ?: "" ,
            person3 = modelSummary.state.dataSummary.value?.person3 ?: "" ,
            person4 = modelSummary.state.dataSummary.value?.person4 ?: "" ,
            checkedPerson1 = person1.isNotEmpty(),
            checkedPerson2 = person2.isNotEmpty(),
            checkedPerson3 = person3.isNotEmpty(),
            checkedPerson4 = person4.isNotEmpty()
        )

        model.getSummary(
            id = idSummary,
            context = context
        )

        selectedOption.value = model.state.listItemRadioButton[getOptionSelected(iconSelected)]
    }

    Row (
        modifier = Modifier.fillMaxWidth()
    ){

        OutlinedTextField(
            value = model.state.itemName.value,
            onValueChange = {
                model.state.itemName.value = it
                model.verifyFieldItemName(model.state.itemName.value)
            },
            singleLine = true,
            label = { Text(text = stringResource(id = R.string.bottomsheet_item_name)) },
            placeholder =  { Text(text = stringResource(id = R.string.bottomsheet_item_name_placeholder))},
            keyboardOptions = KeyboardOptions.Default.copy(KeyboardCapitalization.Words, keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
            modifier = Modifier
                .fillMaxWidth()
                .testTag(context.getString(R.string.bottomsheet_item_name_tag)),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = if (!model.state.redFieldItemName.value) { MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled) } else { Color.Red },
                focusedBorderColor = if (!model.state.redFieldItemName.value) { MaterialTheme.colors.primary } else { Color.Red },
                focusedLabelColor = if (!model.state.redFieldItemName.value) { MaterialTheme.colors.primary } else { Color.Red }
            )
        )

    }

    Spacer(modifier = Modifier.height(16.dp))

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {

        OutlinedTextField(
            value = model.state.itemValue.value,
            onValueChange = {
                if(it.isNotEmpty() && it[it.length - 1] == ',') {
                    model.state.itemValue.value += "."
                } else {
                    model.state.itemValue.value = it
                }
                model.verifyFieldItemValue(model.state.itemValue.value)
            },
            singleLine = true,
            label = { Text(text = stringResource(id = R.string.bottomsheet_item_value)) },
            placeholder =  { Text(text = stringResource(id = R.string.bottomsheet_item_value))},
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            modifier = Modifier
                .weight(1f)
                .testTag(context.getString(R.string.bottomsheet_item_value_tag)),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = if (!model.state.redFieldItemValue.value) { MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled) } else { Color.Red },
                focusedBorderColor = if (!model.state.redFieldItemValue.value) { MaterialTheme.colors.primary } else { Color.Red },
                focusedLabelColor =  if (!model.state.redFieldItemValue.value) { MaterialTheme.colors.primary } else { Color.Red }
            )
        )

        Spacer(modifier = Modifier.width(8.dp))

        OutlinedTextField(
            value = model.state.itemDeadline.value,
            onValueChange = {
                if (it.length <= 2) model.state.itemDeadline.value = it
                model.verifyFieldItemDeadline(model.state.itemDeadline.value)
            },
            singleLine = true,
            label = { Text(text = stringResource(id = R.string.bottomsheet_item_deadline)) },
            placeholder =  { Text(text = stringResource(id = R.string.bottomsheet_item_deadline))},
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            modifier = Modifier
                .weight(1f)
                .testTag(context.getString(R.string.bottomsheet_item_deadline_tag)),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = if (!model.state.redFieldItemDeadline.value) { MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled) }else { Color.Red },
                focusedBorderColor = if (!model.state.redFieldItemDeadline.value) { MaterialTheme.colors.primary } else { Color.Red },
                focusedLabelColor = if (!model.state.redFieldItemDeadline.value) { MaterialTheme.colors.primary } else { Color.Red }
            )
        )

    }

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(
        value = model.state.description.value,
        onValueChange = {
            model.state.description.value = it
        },
        singleLine = true,
        label = { Text(text = stringResource(id = R.string.bottomsheet_item_description)) },
        placeholder =  { Text(text = stringResource(id = R.string.bottomsheet_item_description_placeholder))},
        keyboardOptions = KeyboardOptions.Default.copy(KeyboardCapitalization.Words, keyboardType = KeyboardType.Text),
        modifier = Modifier
            .fillMaxWidth()
            .testTag(context.getString(R.string.bottomsheet_item_description_tag))
    )

    Spacer(modifier = Modifier.height(16.dp))

    Text(text = stringResource(id = R.string.bottomsheet_item_icon))

    ExposedDropdownMenuBox(
        iconSelected = iconSelected
    )

    Spacer(modifier = Modifier.height(16.dp))

    Text(text = stringResource(id = R.string.bottomsheet_item_share_account))

    Spacer(modifier = Modifier.height(8.dp))

    CheckBoxPeople()

    Spacer(modifier = Modifier.height(16.dp))

    FKButtonProgress(
        textColor = MaterialTheme.colors.onSecondary,
        textButton = model.state.textButton.value,
        isProgress = model.state.progressBtn.value,
        modifier = Modifier
            .fillMaxWidth()
            .height(37.dp)
            .background(color = MaterialTheme.colors.primary, shape = RoundedCornerShape(4.dp))
            .testTag(context.getString(R.string.bottomsheet_item_btn_save_tag))
            .clickable {
                if (!model.state.progressBtn.value) {
                    scope.launch {

                        if(
                            model.state.textButton.value == context.getString(R.string.btn_save)
                        ) {

                            model.addItem(
                                context = context,
                                month = GlobalVariables.monthSelected.value ?: 1,
                                year = GlobalVariables.yearSelected.value ?: 2023,
                                onSuccess = {
                                    callBack.invoke()
                                },
                                onFailure = {}
                            )

                        } else {

                            model.editItem(
                                context = context,
                                id = idItem,
                                checkBefore1 = check1,
                                checkBefore2 = check2,
                                checkBefore3 = check3,
                                checkBefore4 = check4,
                                onSuccess = {
                                    scope.launch {
                                        callBack.invoke()
                                    }
                                },
                                onFailure = {}
                            )

                        }

                    }
                }
            }
    )

    Spacer(modifier = Modifier.height(4.dp))

    Button(
        onClick = { callBack.invoke() },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error)
    ) {
        Text(text = stringResource(id = R.string.btn_close), color = MaterialTheme.colors.onSecondary)
    }




}

@Composable
fun ExposedDropdownMenuBox(
    iconSelected: String
) {

    val model: BottomSheetViewModel = koinViewModel()
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(model.state.listItemRadioButton[getOptionSelected(iconSelected)]) }
    var textfieldSize by remember { mutableStateOf(Size.Zero)}
    val icon = if (expanded)
        Icons.Filled.ArrowDropUp
    else
        Icons.Filled.ArrowDropDown

    Box {
        OutlinedTextField(
            enabled = false,
            value = getItemDropDown(selectedText),
            onValueChange = { selectedText = it },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textfieldSize = coordinates.size.toSize()
                }
                .clickable {
                    expanded = !expanded
                }
                .testTag(context.getString(R.string.bottomsheet_item_dropdown_button_tag)),
            label = {Text(stringResource(id = R.string.bottomsheet_item_icon_label))},
            placeholder = { Text(text = stringResource(id = R.string.bottomsheet_item_select))},
            leadingIcon = {
                Image(
                    painter = getPainterIcon(str = selectedText),
                    contentDescription = "",
                    modifier = Modifier
                        .width(24.dp)
                        .height(24.dp)
                )
            },
            trailingIcon = {
                Icon(icon, stringResource(id = R.string.bottomsheet_item_icon_expand),
                    Modifier.clickable { expanded = !expanded })
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                disabledTextColor = LocalContentColor.current.copy(LocalContentAlpha.current),
                backgroundColor = Color.Transparent,
                disabledBorderColor = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled),
                disabledLabelColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium),
            )
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current){textfieldSize.width.toDp()})
        ) {
            model.state.listItemRadioButton.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedText = label
                    model.state.iconOption.value = label
                    expanded = false
                }) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Image(
                            painter = getPainterIcon(str = label),
                            contentDescription = stringResource(id = R.string.bottomsheet_item_icon_dropdown, getTypeIcon(
                                str = label
                            )),
                            modifier = Modifier
                                .width(24.dp)
                                .height(24.dp)
                        )
                        Text(
                            text = getItemDropDown(label),
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .testTag(context.getString(R.string.bottomsheet_item_dropdown_item_tag, label))
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CheckBoxPeople(){

    val model : BottomSheetViewModel = koinViewModel()
    val modelSummary : CardSummaryViewModel = koinViewModel()
    val context = LocalContext.current
    val card by modelSummary.state.dataSummary.collectAsState(initial = null)

    card?.let {group ->
        if(model.state.dataSummary.isNotEmpty()){

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                if(
                    group.person1.isNotEmpty()
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
                            Checkbox(
                                checked = model.state.checkPerson1.value,
                                onCheckedChange = {
                                    model.state.checkPerson1.value = it
                                },
                                modifier = Modifier.testTag(context.getString(R.string.bottomsheet_item_checkbox_person1_item_tag))
                            )
                        }

                        Spacer(modifier = Modifier.width(4.dp))

                        group.person1.replaceFirstChar(Char::uppercase).let {
                            Text(
                                text = it,
                                modifier = Modifier
                                    .padding(top = 2.dp, start = 2.dp)
                                    .testTag(context.getString(R.string.bottomsheet_item_name_person1_item_tag))
                            )
                        }

                    }

                }

                if(
                    group.person2.isNotEmpty()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {

                            Checkbox(
                                checked = model.state.checkPerson2.value,
                                onCheckedChange = {
                                    model.state.checkPerson2.value = it
                                },
                                modifier = Modifier.testTag(context.getString(R.string.bottomsheet_item_checkbox_person2_item_tag))
                            )

                        }

                        Spacer(modifier = Modifier.width(4.dp))

                        group.person2.replaceFirstChar(Char::uppercase).let {
                            Text(
                                text = it,
                                modifier = Modifier
                                    .padding(top = 2.dp, start = 2.dp)
                                    .testTag(context.getString(R.string.bottomsheet_item_name_person2_item_tag))
                            )
                        }

                    }
                }

                if(
                    group.person3.isNotEmpty()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {

                            Checkbox(
                                checked = model.state.checkPerson3.value,
                                onCheckedChange = {
                                    model.state.checkPerson3.value = it
                                },
                                modifier = Modifier
                                    .testTag(context.getString(R.string.bottomsheet_item_checkbox_person3_item_tag))
                            )

                        }

                        Spacer(modifier = Modifier.width(4.dp))

                        group.person3.replaceFirstChar(Char::uppercase).let {
                            Text(
                                text = it,
                                modifier = Modifier
                                    .padding(top = 2.dp, start = 2.dp)
                                    .testTag(context.getString(R.string.bottomsheet_item_name_person3_item_tag))
                            )
                        }

                    }
                }
                if(
                    group.person4.isNotEmpty()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {

                            Checkbox(
                                checked = model.state.checkPerson4.value,
                                onCheckedChange = {
                                    model.state.checkPerson4.value = it
                                },
                                modifier = Modifier.testTag(context.getString(R.string.bottomsheet_item_checkbox_person4_item_tag))
                            )

                        }

                        Spacer(modifier = Modifier.width(4.dp))

                        group.person4.replaceFirstChar(Char::uppercase).let {
                            Text(
                                text = it,
                                modifier = Modifier
                                    .padding(top = 2.dp, start = 2.dp)
                                    .testTag(context.getString(R.string.bottomsheet_item_name_person4_item_tag))
                            )
                        }

                    }
                }
            }

        }
    }




}


fun getOptionSelected(
    str: String
): Int {

    return when(str) {

        "Water" -> {
            1
        }

        "Light" -> {
            0
        }

        "Market" -> {
            2
        }

        "Router" -> {
            3
        }

        "Card" -> {
            4
        }

        "Restaurant" -> {
            5
        }

        "House" -> {
           7
        }

        "Game" -> {
            8
        }

        "Phone" -> {
           6
        }

        "Other" -> {
            9
        }

        else -> {
            9
        }

    }

}


@Preview
@Composable
fun PreviewBottomSheetAddItem(){

    BottomSheetAddItem(
        callBack = {  }
    )

}