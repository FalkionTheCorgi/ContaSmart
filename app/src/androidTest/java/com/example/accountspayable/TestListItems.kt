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
import com.example.accountspayable.Room.Item.ItemEntity
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

                verifyCardItem(element = element, index = index)

            }

        }

    }

    @Test
    fun testAdicionarItem(){

        clickFloatActionButton()

        composeRule.apply {

            variables.fieldName.assertIsDisplayed()
            variables.fieldName.performTextInput(ITEM_NAME)

            variables.fieldValue.assertIsDisplayed()
            variables.fieldValue.performTextInput(ITEM_VALUE)

            fillBottomSheetItemCommonData()

            variables.fieldName.assertIsNotDisplayed()

            verifyCardItem(
                element = ItemEntity(
                    itemName = ITEM_NAME,
                    price = ITEM_VALUE.toDouble(),
                    description = ITEM_DESCRIPTION,
                    icon = itemsDropDown[3],
                    person1 = "Pessoa1",
                    checkedPerson1 = false,
                    person2 = "",
                    checkedPerson2 = false,
                    person3 = "Pessoa3",
                    checkedPerson3 = false,
                    person4 = "",
                    checkedPerson4 = false,
                    priceOfPerson = ITEM_VALUE.toDouble().div(3),
                    vencimento = ITEM_DEADLINE_CORRECT.toInt(),
                    month = getTodayDate()?.monthValue ?: -1,
                    year = getTodayDate()?.year ?: 2023
                ),
                index = 2
            )

        }


    }

    @Test
    fun testAddItemWithNameEmpty(){

        clickFloatActionButton()

        composeRule.apply {

            variables.fieldValue.assertIsDisplayed()
            variables.fieldValue.performTextInput(ITEM_VALUE)

            fillBottomSheetItemCommonData()

            variables.fieldName.assertIsDisplayed()

        }

    }

    @Test
    fun testAddItemWithValueEmpty(){

        clickFloatActionButton()

        composeRule.apply {

            variables.fieldName.assertIsDisplayed()
            variables.fieldName.performTextInput(ITEM_NAME)

            fillBottomSheetItemCommonData()

            variables.fieldName.assertIsDisplayed()

        }

    }

    @Test
    fun putDeadlineIncorretBottomSheetItem(){

        clickFloatActionButton()

        composeRule.apply {

            variables.fieldName.assertIsDisplayed()
            variables.fieldName.performTextInput(ITEM_NAME)

            variables.fieldValue.assertIsDisplayed()
            variables.fieldValue.performTextInput(ITEM_VALUE)

            fillBottomSheetItemCommonData(deadline = ITEM_DEADLINE_ERROR)

            variables.fieldName.assertIsDisplayed()

        }

    }

    @Test
    fun changeMonthYearAndCreateSummary(){
        changeMonthAndYear(month = 9, year = 2023)
    }

    private fun changeMonthAndYear(month: Int, year: Int){
        composeRule.apply {

            onNodeWithTag(
                context.getString(R.string.topbar_calendar_icon)
            ).assertIsDisplayed().performClick()

            variables.fieldBtmSheetMonth.assertIsDisplayed().performClick()

            onNodeWithTag(
                context.getString(
                    R.string.bottomsheet_calendar_month_item_tag,
                    returnMonthById(
                        context = context,
                        id = month - 1
                    ),
                ),
                useUnmergedTree = true
            ).assertIsDisplayed().performClick()

            variables.fieldBtmSheetYear.assertIsDisplayed().performClick()

            onNodeWithTag(
                context.getString(
                    R.string.bottomsheet_calendar_year_item_tag,
                    year.toString()
                ),
                useUnmergedTree = true
            ).assertIsDisplayed().performClick()

            variables.fieldBtmSheetCalendarBtnSave.assertIsDisplayed().performClick()

        }
    }
    private fun verifyCardItem(element: ItemEntity, index: Int){

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

    private fun fillBottomSheetItemCommonData(
        deadline: String = ITEM_DEADLINE_CORRECT
    ){
        variables.fieldDeadline.assertIsDisplayed()
        variables.fieldDeadline.performTextInput(deadline)

        variables.fieldDescription.assertIsDisplayed()
        variables.fieldDescription.performTextInput(ITEM_DESCRIPTION)

        variables.fieldIcon.assertIsDisplayed()
        variables.fieldIcon.performClick()

        variables.fieldIconDropDownItemRouter.assertIsDisplayed()
        variables.fieldIconDropDownItemRouter.performClick()

        if (SUMMARY_CREATED_TODAY.person1.isNotEmpty()){
            variables.fieldNameCheckbox1.assertIsDisplayed()
            variables.fieldNameCheckbox1.assertTextEquals(SUMMARY_CREATED_TODAY.person1)
            variables.fieldCheckboxItem1.assertIsDisplayed()
            variables.fieldCheckboxItem1.performClick()
        } else {
            variables.fieldNameCheckbox1.assertDoesNotExist()
            variables.fieldCheckboxItem1.assertDoesNotExist()
        }

        if (SUMMARY_CREATED_TODAY.person2.isNotEmpty()){
            variables.fieldNameCheckbox2.assertIsDisplayed()
            variables.fieldNameCheckbox2.assertTextEquals(SUMMARY_CREATED_TODAY.person2)
            variables.fieldCheckboxItem2.assertIsDisplayed()
        }else{
            variables.fieldNameCheckbox2.assertDoesNotExist()
            variables.fieldCheckboxItem2.assertDoesNotExist()
        }

        if (SUMMARY_CREATED_TODAY.person3.isNotEmpty()){
            variables.fieldNameCheckbox3.assertIsDisplayed()
            variables.fieldNameCheckbox3.assertTextEquals(SUMMARY_CREATED_TODAY.person3)
            variables.fieldCheckboxItem3.assertIsDisplayed()
            variables.fieldCheckboxItem3.performClick()
        }else{
            variables.fieldNameCheckbox3.assertDoesNotExist()
            variables.fieldCheckboxItem3.assertDoesNotExist()
        }

        if (SUMMARY_CREATED_TODAY.person4.isNotEmpty()){
            variables.fieldNameCheckbox4.assertIsDisplayed()
            variables.fieldNameCheckbox4.assertTextEquals(SUMMARY_CREATED_TODAY.person4)
            variables.fieldCheckboxItem4.assertIsDisplayed()
        }else{
            variables.fieldNameCheckbox4.assertDoesNotExist()
            variables.fieldCheckboxItem4.assertDoesNotExist()
        }

        variables.fieldBtnSave.assertIsDisplayed()
        variables.fieldBtnSave.performClick()
    }
    private fun clickFloatActionButton(){
        with(composeRule){
            onNodeWithTag(context.getString(R.string.float_action_button_tag), useUnmergedTree = true).assertIsDisplayed().performClick()
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