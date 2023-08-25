package com.example.accountspayable.BottomSheet.Donation

import androidx.lifecycle.ViewModel

class BottomSheetDonationViewModel : ViewModel(){

    fun returnNumberDonationType(type: DonationType): String{

        return when(type){
            DonationType.Donate5 -> "5"
            DonationType.Donate10 -> "10"
            DonationType.Donate20 -> "20"
            DonationType.Donate50 -> "50"
            DonationType.Donate100-> "100"
        }


    }

}

enum class DonationType {
    Donate5, Donate10, Donate20, Donate50, Donate100
}