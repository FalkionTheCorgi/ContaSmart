package com.example.gamesprices.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.accountspayable.List.ListAccountsPayable

@Composable
fun NavigationView(navController: NavHostController){

   NavHost(navController = navController, startDestination = "list"){

       composable("list"){

            ListAccountsPayable()

       }

       /*composable("login"){

          Login(navController = navController)

      }

      composable("register"){

          Register(navController = navController)

      }

      composable("mostPopular"){

          MostPopular(
              navController = navController
          )

      }

      composable("popularOnSale"){

          PopularOnSale(
              navController = navController
          )

      }

      composable("newGame"){

          NewGame(
              navController = navController
          )

      }

      composable("gameDetail/{title}/{image}"){

          val title = it.arguments?.getString("title")
          val image = it.arguments?.getInt("image")

          GameDetailView(title = title ?: "", image = image ?: R.drawable.ic_android_green_24dp)

      }*/

  }

}