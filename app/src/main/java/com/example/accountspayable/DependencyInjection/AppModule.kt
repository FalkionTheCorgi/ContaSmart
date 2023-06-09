package com.example.accountspayable.DependencyInjection

import android.app.Activity
import com.example.accountspayable.BottomSheet.Calendar.BottomSheetCalendarViewModel
import com.example.accountspayable.BottomSheet.Donation.BottomSheetDonationViewModel
import com.example.accountspayable.BottomSheet.Item.BottomSheetViewModel
import com.example.accountspayable.BottomSheet.Sugestao.BottomSheetSugestaoViewModel
import com.example.accountspayable.BottomSheet.Summary.BottomSheetSummaryViewModel
import com.example.accountspayable.DataStore.DataStore
import com.example.accountspayable.List.Cards.Item.CardItemPayableViewModel
import com.example.accountspayable.List.Cards.Summary.CardSummaryViewModel
import com.example.accountspayable.List.ListAccountsPayableViewModel
import com.example.accountspayable.MainActivityViewModel
import com.example.accountspayable.Payment.Payment
import com.example.accountspayable.Room.BackupDataBase
import com.example.accountspayable.UpdateApp.InAppUpdate
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        ListAccountsPayableViewModel()
    }

    single {
        MainActivityViewModel()
    }

    single{
        CardSummaryViewModel()
    }

    single {
        DataStore(androidContext())
    }

    single {
        BackupDataBase()
    }

    single{ (view: Activity) ->
        InAppUpdate(
            activity = view,
            context = androidContext()
        )
    }

    single {  (view: Activity) ->
        Payment(
            act = view,
            context = androidContext()
        )
    }

    viewModel {
          CardItemPayableViewModel()
    }

    viewModel{
        BottomSheetViewModel(
            androidApplication()
        )
    }

    viewModel{
        BottomSheetSummaryViewModel()
    }

    single{
        BottomSheetCalendarViewModel(
            androidApplication()
        )
    }

    viewModel {
        BottomSheetSugestaoViewModel()
    }
    viewModel {
        BottomSheetDonationViewModel()
    }

}