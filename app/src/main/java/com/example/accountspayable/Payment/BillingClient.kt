package com.example.accountspayable.Payment

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.android.billingclient.api.*
import com.android.billingclient.api.BillingClient.BillingResponseCode
import com.android.billingclient.api.BillingFlowParams.ProductDetailsParams
import com.google.common.collect.ImmutableList
import kotlinx.coroutines.*


class Payment(
    act: Activity,
    context: Context
){

    val activity = act

    @OptIn(DelicateCoroutinesApi::class)
    private val purchasesUpdatedListener =
        PurchasesUpdatedListener { billingResult, purchases ->
            if (billingResult.responseCode == BillingResponseCode.OK && purchases != null) {
                Toast.makeText(context, "Processando...", Toast.LENGTH_LONG).show()
                GlobalScope.launch {
                    consumeItem(
                        purchase = purchases.first(),
                        activity = activity
                    )
                }
            } else if (billingResult.responseCode == BillingResponseCode.USER_CANCELED) {
                Toast.makeText(context, "Compra cancelada", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Ocorreu um problema ao tentar fazer a doação", Toast.LENGTH_LONG).show()
            }
        }

    private var billingClient = BillingClient.newBuilder(context)
        .setListener(purchasesUpdatedListener)
        .enablePendingPurchases()
        .build()

    fun estabilishedConnection(){

        billingClient.startConnection(object: BillingClientStateListener{
            override fun onBillingServiceDisconnected() {
                estabilishedConnection()
            }

            override fun onBillingSetupFinished(result: BillingResult) {
                if (result.responseCode == BillingClient.BillingResponseCode.OK){

                    showProducts()

                }
            }

        })

    }

    fun showProducts(){

        val queryProductDetailsParams =
            QueryProductDetailsParams.newBuilder()
                .setProductList(
                    ImmutableList.of(
                        QueryProductDetailsParams.Product.newBuilder()
                            .setProductId("donate5")
                            .setProductType(BillingClient.ProductType.INAPP)
                            .build()))
                .build()

       billingClient.queryProductDetailsAsync(queryProductDetailsParams) {
           billingResult,
           productDetailsList ->

           launchPurchaseFlow(productDetailsList.first())

       }

    }

    fun launchPurchaseFlow(
        productDetails: ProductDetails
    ){

        assert(productDetails.name.isNotEmpty())
        val productDetailsParamsList = listOf(
            ProductDetailsParams.newBuilder()
                // retrieve a value for "productDetails" by calling queryProductDetailsAsync()
                .setProductDetails(productDetails)
                // to get an offer token, call ProductDetails.subscriptionOfferDetails()
                // for a list of offers that are available to the user
                .setOfferToken(productDetails.subscriptionOfferDetails?.first()?.offerToken ?: "")
                .build()
        )


        val billingFlowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(productDetailsParamsList)
            .build()

        billingClient.launchBillingFlow(activity, billingFlowParams)

    }

    suspend fun consumeItem(purchase: Purchase, activity: Activity) {

        val consumeParams = ConsumeParams.newBuilder()
            .setPurchaseToken(purchase.purchaseToken)
            .build()

        val consumeResult = withContext(Dispatchers.IO){
            billingClient.consumeAsync(consumeParams
            ) { result, p1 ->
                activity.runOnUiThread {
                    Toast.makeText(
                        activity.applicationContext,
                        "Obrigado por nos ajudar com o projeto :)",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

    }



}