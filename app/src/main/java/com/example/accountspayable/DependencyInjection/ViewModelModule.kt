package com.example.accountspayable.DependencyInjection

import com.example.accountspayable.BottomSheet.Calendar.BottomSheetCalendarViewModel
import com.example.accountspayable.BottomSheet.Donation.BottomSheetDonationViewModel
import com.example.accountspayable.BottomSheet.Item.BottomSheetViewModel
import com.example.accountspayable.BottomSheet.Sugestao.BottomSheetSugestaoViewModel
import com.example.accountspayable.BottomSheet.Summary.BottomSheetSummaryViewModel
import com.example.accountspayable.List.Cards.Item.CardItemPayableViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel{
        BottomSheetViewModel(
            androidApplication()
        )
    }

    viewModel{
        BottomSheetSummaryViewModel(
            androidApplication()
        )
    }

    viewModel {
        BottomSheetSugestaoViewModel()
    }

    viewModel {
        BottomSheetDonationViewModel()
    }

    viewModel{
        BottomSheetCalendarViewModel(
            androidApplication()
        )
    }

    viewModel {
        CardItemPayableViewModel()
    }



}