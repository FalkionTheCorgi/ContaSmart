package com.example.accountspayable.Payment

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.*
import com.android.billingclient.api.BillingFlowParams.ProductDetailsParams
import com.google.common.collect.ImmutableList


class Payment(
    act: Activity
){

    private lateinit var billingClient: BillingClient
    val activity = act


    fun startNewBuilder(
        context: Context
    ){

        billingClient = BillingClient.newBuilder(context)
            .enablePendingPurchases()
            .setListener { result, list ->
                if (result.responseCode == BillingClient.BillingResponseCode.OK && !list.isNullOrEmpty()) {
                    list.forEach { purchase ->
                        //verifyPurchase(purchase)
                    }
                }
            }.build()

        estabilishedConnection()

    }

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

        val productList = ImmutableList.of(

            QueryProductDetailsParams.Product.newBuilder()
                .setProductId("donate1")
                .setProductType(BillingClient.ProductType.INAPP)
                .build(),

            QueryProductDetailsParams.Product.newBuilder()
                .setProductId("donate3")
                .setProductType(BillingClient.ProductType.INAPP)
                .build(),

            QueryProductDetailsParams.Product.newBuilder()
                .setProductId("donate5")
                .setProductType(BillingClient.ProductType.INAPP)
                .build(),

            QueryProductDetailsParams.Product.newBuilder()
                .setProductId("donate10")
                .setProductType(BillingClient.ProductType.INAPP)
                .build(),

            QueryProductDetailsParams.Product.newBuilder()
                .setProductId("donate15")
                .setProductType(BillingClient.ProductType.INAPP)
                .build(),

        )


        val params = QueryProductDetailsParams.newBuilder()
            .setProductList(productList)
            .build()

        billingClient.queryProductDetailsAsync(
            params
        ) { result, list ->
            list.forEach {

                launchPurchaseFlow(it)

            }
        }

    }

    fun launchPurchaseFlow(
        productDetails: ProductDetails
    ) {

        assert(productDetails.subscriptionOfferDetails != null)
        val productDetailsParamsList: List<ProductDetailsParams> = ImmutableList.of(
            ProductDetailsParams.newBuilder()
                .setProductDetails(productDetails)
                .setOfferToken(productDetails.subscriptionOfferDetails!![0].offerToken)
                .build()
        )

        val billingFlowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(productDetailsParamsList)
            .build()

        billingClient.launchBillingFlow(activity, billingFlowParams)

    }



}