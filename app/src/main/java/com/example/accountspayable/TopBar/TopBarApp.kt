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
import androidx.compose.ui.unit.dp
import com.example.accountspayable.BottomSheetTypes
import com.example.accountspayable.MainActivityViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TopBarApp(
    title : String
){

    val activityViewModel: MainActivityViewModel = koinViewModel()
    var showMenu = remember{ mutableStateOf(false) }

    TopAppBar(
        title = {
            Text(text = title.replaceFirstChar { it.uppercase()  }, color = Color.White)
        },
        actions = {
            Icon(
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = "Calendário",
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        activityViewModel.bottomSheetType.value = BottomSheetTypes.CALENDAR
                    },
                tint = Color.White
            )
            IconButton(onClick = { showMenu.value = !showMenu.value }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Mais itens")
            }
            DropdownMenu(
                expanded = showMenu.value,
                onDismissRequest = { showMenu.value = false }
            ) {

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
                            contentDescription = "Setting",
                            tint = MaterialTheme.colors.secondary
                        )

                        Text(
                            text = "Ajustes",
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
                            contentDescription = "Sugestão",
                            tint = MaterialTheme.colors.secondary,
                            modifier = Modifier.padding(end = 8.dp)
                        )

                        Spacer(modifier = Modifier.weight(1f))
                        
                        Text(
                            text = "Sugestão",
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
                            contentDescription = "Donation",
                            tint = MaterialTheme.colors.secondary,
                        )

                        Text(
                            text = "Apoie",
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