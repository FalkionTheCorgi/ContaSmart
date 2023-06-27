package com.example.accountspayable

import android.content.Context
import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.unit.toSize
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.accountspayable.Data.*
import com.example.accountspayable.Room.DataBase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class TestListItems {

    private val context = ApplicationProvider.getApplicationContext<Context>()

    @get:Rule
    val composeRule = createAndroidComposeRule(MainActivity::class.java)

    val variables = VariableTest(context, composeRule)


    @Before
    fun setup(){
        runBlocking {
            DataBase.getDataBase(context).summary().insertSummary(SUMMARY_CREATED_TODAY)
            DataBase.getDataBase(context).item().insertItem(ITEM_ENTITY_LESS_3DAYS_1)
            DataBase.getDataBase(context).item().insertItem(ITEM_ENTITY_LESS_3DAYS_2)
        }
    }

    @Test
    fun testClickAndVerifyCardSummary(): Unit = runBlocking {

        clickCardSummary()

        composeRule.apply{

            variables.summaryData.assertIsDisplayed()
            variables.summaryData.assertTextEquals(
                context.getString(R.string.summary_month_year,
                    returnMonthString(
                        context,
                        GlobalVariables.monthSelected.first() ?: 1
                    ),
                    GlobalVariables.yearSelected.first() ?: 2023
                )
            )

            variables.summaryReceipt.assertIsDisplayed()
            variables.summaryReceipt.assertTextEquals(
                context.getString(
                    R.string.summary_receipt,
                    String.format("%.2f", SUMMARY_CREATED_TODAY.revenue)
                )
            )

            variables.summaryExpenditure.assertIsDisplayed()
            variables.summaryExpenditure.assertTextEquals(
                context.getString(
                    R.string.summary_expenditure,
                    String.format("%.2f", ITEM_ENTITY_LESS_3DAYS_1.price)
                )
            )

            variables.summaryRemainder.assertIsDisplayed()
            variables.summaryRemainder.assertTextEquals(
                context.getString(
                    R.string.summary_remaining,
                    returnRevenueToExpenditure()
                )
            )

            variables.summaryPerson1Name.assertIsDisplayed()
            variables.summaryPerson1Name.assertTextEquals(
                SUMMARY_CREATED_TODAY.person1.replaceFirstChar(Char::uppercase)
            )

            variables.summaryPerson2Name.assertIsDisplayed()
            variables.summaryPerson2Name.assertTextEquals(
                SUMMARY_CREATED_TODAY.person2.replaceFirstChar(Char::uppercase)
            )

            variables.summaryPerson3Name.assertIsDisplayed()
            variables.summaryPerson3Name.assertTextEquals(
                SUMMARY_CREATED_TODAY.person3.replaceFirstChar(Char::uppercase)
            )

            variables.summaryPerson4Name.assertIsDisplayed()
            variables.summaryPerson4Name.assertTextEquals(
                SUMMARY_CREATED_TODAY.person4.replaceFirstChar(Char::uppercase)
            )

            variables.summaryPerson1Value.assertIsDisplayed()
            variables.summaryPerson1Value.assertTextEquals(
                returnSummaryValueByPerson(context, p1 = true)
            )

            variables.summaryPerson2Value.assertIsDisplayed()
            variables.summaryPerson2Value.assertTextEquals(
                returnSummaryValueByPerson(context, p2 = true)
            )

            variables.summaryPerson3Value.assertIsDisplayed()
            variables.summaryPerson3Value.assertTextEquals(
                returnSummaryValueByPerson(context, p3 = true)
            )

            variables.summaryPerson4Value.assertIsDisplayed()
            variables.summaryPerson4Value.assertTextEquals(
                returnSummaryValueByPerson(context, p4 = true)
            )

            clickCardSummary()

            variables.summaryReceipt.assertDoesNotExist()
        }

    }
    @Test
    fun testVerificationItem(): Unit = runBlocking{

        composeRule.apply{

            listItems.forEachIndexed { index, element ->

                clickCardItem(index)

                val item = NodeItem(
                    index = index,
                    context = context,
                    composeRule = composeRule
                )

                item.itemValue.assertIsDisplayed()
                item.itemValue.assertTextEquals(
                    context.getString(
                        R.string.price_item,
                        "R$",
                        String.format("%.2f", element.price)
                    )
                )

                /*if (index > 0){
                    onNodeWithTag(context.getString(R.string.lazy_column_list_tag))
                        .performScrollToIndex(index)
                    waitForIdle()

                }*/

                if(element.vencimento > 0) {
                    item.itemDeadline.assertIsDisplayed()
                    item.itemDeadline.assertTextEquals(
                        context.getString(
                            R.string.day_deadline,
                            element.vencimento
                        )
                    )
                } else {
                    item.itemDeadline.assertDoesNotExist()
                }

                if(element.description.isNotEmpty()) {

                    item.itemDescription.assertIsDisplayed()
                    item.itemDescription.assertTextEquals(
                        context.getString(
                            R.string.description_item,
                            element.description
                        )
                    )
                }else{
                    item.itemDescription.assertDoesNotExist()
                }

                if (element.person1.isNotEmpty()) {

                    item.itemPerson1.assertTextEquals(element.person1)
                    item.itemPerson1Checkbox.assertIsDisplayed()

                } else {

                    item.itemPerson1.assertDoesNotExist()

                }

                if (element.person2.isNotEmpty()) {

                    item.itemPerson2.assertIsDisplayed()
                    item.itemPerson2.assertTextEquals(element.person2)
                    item.itemPerson2Checkbox.assertIsDisplayed()

                } else {

                    item.itemPerson2.assertDoesNotExist()

                }

                if (element.person3.isNotEmpty()) {

                    item.itemPerson3.assertIsDisplayed()
                    item.itemPerson3.assertTextEquals(element.person3)
                    item.itemPerson3Checkbox.assertIsDisplayed()

                } else {

                    item.itemPerson3.assertDoesNotExist()

                }

                if (element.person4.isNotEmpty()) {

                    item.itemPerson4.assertIsDisplayed()
                    item.itemPerson4.assertTextEquals(element.person4)
                    item.itemPerson4Checkbox.assertIsDisplayed()

                } else {

                    item.itemPerson4.assertDoesNotExist()

                }

                clickCardItem(index)

            }

        }

    }

    private fun clickCardItem(index: Int) {
        with(composeRule){
            val node = onAllNodesWithTag(context.getString(R.string.card_item_tag))
            node[index].assertIsDisplayed().performClick()
        }
    }
    private fun clickCardSummary(){
        with(composeRule){
            onNodeWithTag(context.getString(R.string.card_summary_exist_tag)).assertIsDisplayed().performClick()
        }
    }

    @After
    fun cleanup() {

        runBlocking {
            DataBase.getDataBase(context).item().deleteAllItems()
            DataBase.getDataBase(context).summary().deleteAllSummary()
        }

    }

}