package com.example.accountspayable.BottomSheet.Sugestao

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.accountspayable.R
import kotlinx.coroutines.launch

class BottomSheetSugestaoViewModel: ViewModel() {

    @SuppressLint("IntentReset")
    fun sendEmail(context: Context, message: String) {

        if(message.isNotEmpty()) {

            val mIntent = Intent(Intent.ACTION_VIEW)
            //mIntent.addCategory(Intent.CATEGORY_APP_EMAIL)
            val data = Uri.parse("mailto:${context.getString(R.string.contato_support)}?subject=Sugestão/Dúvida&body=$message")
            mIntent.setData(data)
            /*mIntent.data = Uri.parse("mailto:")
            mIntent.type = "text/plain"

            mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("contatocontasmart@gmail.com"))
            mIntent.putExtra(Intent.EXTRA_SUBJECT, "Sugestão/Dúvida")
            mIntent.putExtra(Intent.EXTRA_TEXT, message)*/

            try {

                context.startActivity(mIntent)

            } catch (e: java.lang.Exception) {
                viewModelScope.launch {
                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                }
            }
        }else{
            viewModelScope.launch {
                Toast.makeText(context, context.getString(R.string.toast_please_digit_message), Toast.LENGTH_LONG).show()
            }
        }

    }


}