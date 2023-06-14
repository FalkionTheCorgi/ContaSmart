package com.example.accountspayable.BottomSheet.Calendar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.accountspayable.MainActivityViewModel
import com.example.accountspayable.R
import com.example.accountspayable.returnMonthString
import org.koin.androidx.compose.koinViewModel


@Composable
fun BottomSheetCalendar(
    callBack: () -> Unit
) {

    val activityViewModel: MainActivityViewModel = koinViewModel()
    val model: BottomSheetCalendarViewModel = koinViewModel()

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(15.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            Row(
                modifier = Modifier.weight(1f)
            ){
                ExposedDropdownMenuMonth()
            }


            Spacer(modifier = Modifier.width(8.dp))

            Row(
                modifier = Modifier.weight(1f)
            ) {
                ExposedDropdownMenuYear()
            }

        }
        
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                activityViewModel.monthSelected.value = model.state.monthSelected.value
                activityViewModel.yearSelected.value = model.state.yearSelected.value
                activityViewModel.resetCardSummary.value = true
                callBack.invoke()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
        ) {
            Text(text = stringResource(id = R.string.btn_select), color = MaterialTheme.colors.onSecondary)
        }

        Button(
            onClick = { callBack.invoke() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error)
        ) {
            Text(text = stringResource(id = R.string.btn_close), color = MaterialTheme.colors.onSecondary)
        }

    }

}

@Composable
fun ExposedDropdownMenuMonth() {

    val model: BottomSheetCalendarViewModel = koinViewModel()
    val activityViewModel: MainActivityViewModel = koinViewModel()
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(model.state.months[0]) }
    var textfieldSize by remember { mutableStateOf(Size.Zero) }
    val icon = if (expanded)
        Icons.Filled.ArrowDropUp
    else
        Icons.Filled.ArrowDropDown
    val context = LocalContext.current

    LaunchedEffect(true){

        selectedText = returnMonthString(context = context,activityViewModel.monthSelected.value ?: 1)

    }

    Box {
        OutlinedTextField(
            enabled = false,
            value = selectedText,
            onValueChange = { selectedText = it },
            modifier = Modifier
                .wrapContentWidth()
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textfieldSize = coordinates.size.toSize()
                }
                .clickable {
                    expanded = !expanded
                },
            label = { Text(stringResource(id = R.string.bottomsheet_calendar_month)) },
            placeholder = { Text(text = stringResource(id = R.string.bottomsheet_calendar_select)) },
            trailingIcon = {
                Icon(icon, stringResource(id = R.string.bottomsheet_calendar_open_close),
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
            model.state.months.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedText = label
                    model.state.monthSelected.value = model.returnMonth(label)
                    expanded = false
                }) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = label,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ExposedDropdownMenuYear() {

    val model: BottomSheetCalendarViewModel = koinViewModel()
    val activityViewModel: MainActivityViewModel = koinViewModel()
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(model.state.years[0]) }
    var textfieldSize by remember { mutableStateOf(Size.Zero) }
    val icon = if (expanded)
        Icons.Filled.ArrowDropUp
    else
        Icons.Filled.ArrowDropDown

    LaunchedEffect(true){

        selectedText = activityViewModel.yearSelected.value.toString()

    }

    Box {
        OutlinedTextField(
            enabled = false,
            value = selectedText,
            onValueChange = { selectedText = it },
            modifier = Modifier
                .wrapContentWidth()
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textfieldSize = coordinates.size.toSize()
                }
                .clickable {
                    expanded = !expanded
                },
            label = { Text(stringResource(id = R.string.bottomsheet_calendar_year)) },
            placeholder = { Text(text = stringResource(id = R.string.bottomsheet_calendar_select)) },
            trailingIcon = {
                Icon(icon,stringResource(id = R.string.bottomsheet_calendar_open_close_year),
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
            model.state.years.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedText = label
                    model.state.yearSelected.value = label.toInt()
                    expanded = false
                }) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = label,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewBottomSheetCalendar(){
    BottomSheetCalendar(
        callBack = {}
    )
}