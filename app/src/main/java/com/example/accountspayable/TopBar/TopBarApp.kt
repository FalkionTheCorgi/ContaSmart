package com.example.accountspayable.TopBar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.accountspayable.AlertTypes
import com.example.accountspayable.BottomSheetTypes
import com.example.accountspayable.MainActivityViewModel
import com.example.accountspayable.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun TopBarApp(
    title : String
){

    val activityViewModel: MainActivityViewModel = koinViewModel()
    var showMenu = remember{ mutableStateOf(false) }
    val context = LocalContext.current

    TopAppBar(
        title = {
            Text(text = title.replaceFirstChar { it.uppercase()  }, color = Color.White)
        },
        actions = {
            Icon(
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = stringResource(id = R.string.topbar_calendar),
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        activityViewModel.bottomSheetType.value = BottomSheetTypes.CALENDAR
                    }
                    .testTag(
                        context.getString(
                            R.string.topbar_calendar_icon
                        )
                    ),
                tint = Color.White
            )
            IconButton(onClick = { showMenu.value = !showMenu.value }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = stringResource(id = R.string.topbar_more_itens))
            }
            DropdownMenu(
                expanded = showMenu.value,
                onDismissRequest = { showMenu.value = false }
            ) {

                DropdownMenuItem(onClick = {
                    activityViewModel.openAlert.value = AlertTypes.IMPORTDATA
                    showMenu.value = false
                }) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowDownward,
                            contentDescription = stringResource(id = R.string.topbar_icon_import),
                            tint = MaterialTheme.colors.secondary
                        )

                        Text(
                            text = stringResource(id = R.string.topbar_import),
                            color = MaterialTheme.colors.secondary,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }

                Divider()

                DropdownMenuItem(onClick = {
                    activityViewModel.bottomSheetType.value = BottomSheetTypes.SETTINGS
                    showMenu.value = false
                }) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = stringResource(id = R.string.topbar_icon_settings),
                            tint = MaterialTheme.colors.secondary
                        )

                        Text(
                            text = stringResource(id = R.string.topbar_settings),
                            color = MaterialTheme.colors.secondary,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }

                Divider()

                DropdownMenuItem(onClick = {
                    activityViewModel.bottomSheetType.value = BottomSheetTypes.SUGGESTION
                    showMenu.value = false
                }) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Help,
                            contentDescription = stringResource(id = R.string.topbar_icon_suggestion),
                            tint = MaterialTheme.colors.secondary,
                            modifier = Modifier.padding(end = 8.dp)
                        )

                        Spacer(modifier = Modifier.weight(1f))
                        
                        Text(
                            text = stringResource(id = R.string.topbar_suggestion),
                            color = MaterialTheme.colors.secondary
                        )
                    }
                    
                }

                Divider()

                DropdownMenuItem(onClick = {
                    activityViewModel.bottomSheetType.value = BottomSheetTypes.DONATION
                    showMenu.value = false
                }) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CardGiftcard,
                            contentDescription = stringResource(id = R.string.topbar_icon_donation),
                            tint = MaterialTheme.colors.secondary,
                        )

                        Text(
                            text = stringResource(id = R.string.topbar_donation),
                            color = MaterialTheme.colors.secondary,
                            modifier = Modifier.padding(start = 8.dp)
                        )

                    }
                }


            }
        },
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.primarySurface
    )

}