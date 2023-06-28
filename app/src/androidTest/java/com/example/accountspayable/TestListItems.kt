package com.example.accountspayable

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.accountspayable.Data.*
import com.example.accountspayable.Room.DataBase
import com.example.accountspayable.Room.Item.ItemEntity
import com.google.android.datatransport.cct.StringMerger
import kotlinx.coroutines.android.awaitFrame
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
    fun test_verificar_card_sumario() {

        clickCardSummary()

        composeRule.apply {

            verifyCardSummary(
                revenue = String.format("%.2f", SUMMARY_CREATED_TODAY.revenue),
                price = String.format("%.2f", ITEM_ENTITY_LESS_3DAYS_1.price + ITEM_ENTITY_LESS_3DAYS_2.price),
                revenueToExpenditure = returnRevenueToExpenditure(
                    revenue = SUMMARY_CREATED_TODAY.revenue.toDouble(),
                    listItems = listItems
                ),
                person1 = SUMMARY_CREATED_TODAY.person1,
                person2 = SUMMARY_CREATED_TODAY.person2,
                person3 = SUMMARY_CREATED_TODAY.person3,
                person4 = SUMMARY_CREATED_TODAY.person4,
                list = listItems
            )

        }

    }
    @Test
    fun test_verificar_card_item(){

        composeRule.apply{

            listItems.forEachIndexed { index, element ->

                verifyCardItem(element = element, index = index)

            }

        }

    }

    @Test
    fun test_adicionar_item(){

        clickFloatActionButton()

        composeRule.apply {

            waitUntilTimeout(1000)

            variables.fieldName.assertIsDisplayed()
            variables.fieldName.performTextInput(ITEM_NAME)

            variables.fieldValue.assertIsDisplayed()
            variables.fieldValue.performTextInput(ITEM_VALUE)

            fillBottomSheetItemCommonData(
                checkPerson1 = true,
                checkPerson3 = true
            )

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
    fun test_adicionar_item_com_nome_vazio(){

        clickFloatActionButton()

        composeRule.apply {

            variables.fieldValue.assertIsDisplayed()
            variables.fieldValue.performTextInput(ITEM_VALUE)

            fillBottomSheetItemCommonData()

            variables.fieldName.assertIsDisplayed()

        }

    }

    @Test
    fun test_adicionar_item_com_valor_vazio(){

        clickFloatActionButton()

        composeRule.apply {

            variables.fieldName.assertIsDisplayed()
            variables.fieldName.performTextInput(ITEM_NAME)

            fillBottomSheetItemCommonData()

            variables.fieldName.assertIsDisplayed()

        }

    }

    @Test
    fun test_colocar_vencimento_incorreto(){

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
    fun test_criar_sumario(){

        changeMonthAndYear(month = 9, year = 2023)

        clickBtnRegisterSummary()

        composeRule.apply {

            variables.fieldBtmSheetSummaryRevenue.assertIsDisplayed()
            variables.fieldBtmSheetSummaryRevenue.performTextInput(SUMMARY_REVENUE)

            variables.fieldBtmSheetSummaryPerson1.assertIsDisplayed()
            variables.fieldBtmSheetSummaryPerson1.performTextInput(SUMMARY_PERSON1)

            variables.fieldBtmSheetSummaryPerson2.assertIsDisplayed()
            variables.fieldBtmSheetSummaryPerson2.performTextInput(SUMMARY_PERSON2)

            variables.fieldBtmSheetSummaryPerson3.assertIsDisplayed()
            variables.fieldBtmSheetSummaryPerson3.performTextInput(SUMMARY_PERSON3)

            variables.fieldBtmSheetSummaryPerson4.assertIsDisplayed()
            variables.fieldBtmSheetSummaryPerson4.performTextInput(SUMMARY_PERSON4)

            variables.fieldBtmSheetSummaryBtnSave.assertIsDisplayed()
            variables.fieldBtmSheetSummaryBtnSave.performClick()

            clickCardSummary()

            verifyCardSummary(
                revenue = String.format("%.2f", SUMMARY_REVENUE.toDouble()),
                price = String.format("%.2f", 0.0),
                revenueToExpenditure = SUMMARY_REVENUE,
                person1 = SUMMARY_PERSON1,
                person2 = SUMMARY_PERSON2,
                person3 = SUMMARY_PERSON3,
                person4 = SUMMARY_PERSON4,
                list = listOf()
            )


        }

    }

    @Test
    fun test_criar_sumario_sem_preencher_receita(){
        changeMonthAndYear(month = 9, year = 2023)

        clickBtnRegisterSummary()

        composeRule.apply {

            variables.fieldBtmSheetSummaryPerson1.assertIsDisplayed()
            variables.fieldBtmSheetSummaryPerson1.performTextInput(SUMMARY_PERSON1)

            variables.fieldBtmSheetSummaryPerson2.assertIsDisplayed()
            variables.fieldBtmSheetSummaryPerson2.performTextInput(SUMMARY_PERSON2)

            variables.fieldBtmSheetSummaryPerson3.assertIsDisplayed()
            variables.fieldBtmSheetSummaryPerson3.performTextInput(SUMMARY_PERSON3)

            variables.fieldBtmSheetSummaryPerson4.assertIsDisplayed()
            variables.fieldBtmSheetSummaryPerson4.performTextInput(SUMMARY_PERSON4)

            variables.fieldBtmSheetSummaryBtnSave.assertIsDisplayed()
            variables.fieldBtmSheetSummaryBtnSave.performClick()

            Espresso.closeSoftKeyboard()

            variables.fieldBtmSheetSummaryRevenue.assertIsDisplayed()

        }
    }

    @Test
    fun test_criar_item_em_outro_mes_verificar_retornar_para_mes_atual_e_verificar(): Unit = runBlocking{

        changeMonthAndYear(month = 9, year = 2023)

        clickBtnRegisterSummary()

        composeRule.apply {

            variables.fieldBtmSheetSummaryRevenue.assertIsDisplayed()
            variables.fieldBtmSheetSummaryRevenue.performTextInput(SUMMARY_REVENUE)

            variables.fieldBtmSheetSummaryPerson1.assertIsDisplayed()
            variables.fieldBtmSheetSummaryPerson1.performTextInput(SUMMARY_PERSON1)

            variables.fieldBtmSheetSummaryPerson2.assertIsDisplayed()
            variables.fieldBtmSheetSummaryPerson2.performTextInput(SUMMARY_PERSON2)

            variables.fieldBtmSheetSummaryPerson3.assertIsDisplayed()
            variables.fieldBtmSheetSummaryPerson3.performTextInput(SUMMARY_PERSON3)

            variables.fieldBtmSheetSummaryPerson4.assertIsDisplayed()
            variables.fieldBtmSheetSummaryPerson4.performTextInput(SUMMARY_PERSON4)

            variables.fieldBtmSheetSummaryBtnSave.assertIsDisplayed()
            variables.fieldBtmSheetSummaryBtnSave.performClick()

            clickCardSummary()

            verifyCardSummary(
                revenue = String.format("%.2f", SUMMARY_REVENUE.toDouble()),
                price = String.format("%.2f", 0.0),
                revenueToExpenditure = SUMMARY_REVENUE,
                person1 = SUMMARY_PERSON1,
                person2 = SUMMARY_PERSON2,
                person3 = SUMMARY_PERSON3,
                person4 = SUMMARY_PERSON4,
                list = listOf()
            )

            clickFloatActionButton()

            waitUntilTimeout(1000)

            variables.fieldName.assertIsDisplayed()
            variables.fieldName.performTextInput(ITEM_NAME)

            variables.fieldValue.assertIsDisplayed()
            variables.fieldValue.performTextInput(ITEM_VALUE)

            fillBottomSheetItemCommonData(
                checkPerson3 = true
            )

            variables.fieldName.assertIsNotDisplayed()

            val item = ItemEntity(
                itemName = ITEM_NAME,
                price = ITEM_VALUE.toDouble(),
                description = ITEM_DESCRIPTION,
                icon = itemsDropDown[2],
                person1 = "",
                checkedPerson1 = false,
                person2 = "",
                checkedPerson2 = false,
                person3 = "Pessoa3",
                checkedPerson3 = false,
                person4 = "",
                checkedPerson4 = false,
                priceOfPerson = ITEM_VALUE.toDouble().div(2),
                vencimento = ITEM_DEADLINE_CORRECT.toInt(),
                month = GlobalVariables.monthSelected.first() ?: 1,
                year = GlobalVariables.yearSelected.first() ?: 2023
            )

            verifyCardItem(
                element = item,
                index = 0
            )

            clickCardSummary()

            verifyCardSummary(
                revenue = String.format("%.2f", SUMMARY_REVENUE.toDouble()),
                price = String.format("%.2f", item.price),
                revenueToExpenditure = returnRevenueToExpenditure(
                    revenue = SUMMARY_REVENUE.toDouble(),
                    listItems = listOf(item)
                ),
                person1 = SUMMARY_PERSON1,
                person2 = SUMMARY_PERSON2,
                person3 = SUMMARY_PERSON3,
                person4 = SUMMARY_PERSON4,
                list = listOf(item)
            )

            changeMonthAndYear(
                getTodayDate()?.monthValue ?: 1,
                getTodayDate()?.year ?: 2023
            )

            clickCardSummary()

            verifyCardSummary(
                revenue = String.format("%.2f", SUMMARY_CREATED_TODAY.revenue),
                price = String.format("%.2f", ITEM_ENTITY_LESS_3DAYS_1.price + ITEM_ENTITY_LESS_3DAYS_2.price),
                revenueToExpenditure = returnRevenueToExpenditure(
                    revenue = SUMMARY_CREATED_TODAY.revenue.toDouble(),
                    listItems = listItems
                ),
                person1 = SUMMARY_CREATED_TODAY.person1,
                person2 = SUMMARY_CREATED_TODAY.person2,
                person3 = SUMMARY_CREATED_TODAY.person3,
                person4 = SUMMARY_CREATED_TODAY.person4,
                list = listItems
            )

            listItems.forEachIndexed { index, element ->

                verifyCardItem(element = element, index = index)

            }

        }
    }

    private fun changeMonthAndYear(month: Int, year: Int){
        composeRule.apply {

            onNodeWithTag(
                context.getString(R.string.topbar_calendar_icon_tag)
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

    private fun verifyCardSummary(
        revenue: String,
        price: String,
        revenueToExpenditure: String,
        person1: String,
        person2: String,
        person3: String,
        person4: String,
        list: List<ItemEntity>
        ) = runBlocking{

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
                revenue
            )
        )

         variables.summaryExpenditure.assertIsDisplayed()
         variables.summaryExpenditure.assertTextEquals(
            context.getString(
                R.string.summary_expenditure,
                price
            )
        )

        variables.summaryRemainder.assertIsDisplayed()
        variables.summaryRemainder.assertTextEquals(
            context.getString(
                R.string.summary_remaining,
                revenueToExpenditure
            )
        )

        variables.summaryPerson1Name.assertIsDisplayed()
        variables.summaryPerson1Name.assertTextEquals(
            person1.replaceFirstChar(Char::uppercase)
        )

        variables.summaryPerson2Name.assertIsDisplayed()
        variables.summaryPerson2Name.assertTextEquals(
            person2.replaceFirstChar(Char::uppercase)
        )

        variables.summaryPerson3Name.assertIsDisplayed()
        variables.summaryPerson3Name.assertTextEquals(
            person3.replaceFirstChar(Char::uppercase)
        )

        variables.summaryPerson4Name.assertIsDisplayed()
        variables.summaryPerson4Name.assertTextEquals(
            person4.replaceFirstChar(Char::uppercase)
        )

        variables.summaryPerson1Value.assertIsDisplayed()
        variables.summaryPerson1Value.assertTextEquals(
            returnSummaryValueByPerson(context, p1 = true, listItems = list)
        )

        variables.summaryPerson2Value.assertIsDisplayed()
        variables.summaryPerson2Value.assertTextEquals(
            returnSummaryValueByPerson(context, p2 = true, listItems = list)
        )

        variables.summaryPerson3Value.assertIsDisplayed()
        variables.summaryPerson3Value.assertTextEquals(
            returnSummaryValueByPerson(context, p3 = true, listItems = list)
        )

        variables.summaryPerson4Value.assertIsDisplayed()
        variables.summaryPerson4Value.assertTextEquals(
            returnSummaryValueByPerson(context, p4 = true, listItems = list)
        )

        clickCardSummary()

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
        deadline: String = ITEM_DEADLINE_CORRECT,
        checkPerson1: Boolean = false,
        checkPerson2: Boolean = false,
        checkPerson3: Boolean = false,
        checkPerson4: Boolean = false

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
            if (checkPerson1) {
                variables.fieldNameCheckbox1.assertIsDisplayed()
                variables.fieldNameCheckbox1.assertTextEquals(SUMMARY_CREATED_TODAY.person1)
                variables.fieldCheckboxItem1.assertIsDisplayed()
                variables.fieldCheckboxItem1.performClick()
            }
        } else {
            variables.fieldNameCheckbox1.assertDoesNotExist()
            variables.fieldCheckboxItem1.assertDoesNotExist()
        }

        if (SUMMARY_CREATED_TODAY.person2.isNotEmpty()){
            if (checkPerson2) {
                variables.fieldNameCheckbox2.assertIsDisplayed()
                variables.fieldNameCheckbox2.assertTextEquals(SUMMARY_CREATED_TODAY.person2)
                variables.fieldCheckboxItem2.assertIsDisplayed()
                variables.fieldCheckboxItem2.performClick()
            }
        }else{
            variables.fieldNameCheckbox2.assertDoesNotExist()
            variables.fieldCheckboxItem2.assertDoesNotExist()
        }

        if (SUMMARY_CREATED_TODAY.person3.isNotEmpty()){
            if (checkPerson3) {
                variables.fieldNameCheckbox3.assertIsDisplayed()
                variables.fieldNameCheckbox3.assertTextEquals(SUMMARY_CREATED_TODAY.person3)
                variables.fieldCheckboxItem3.assertIsDisplayed()
                variables.fieldCheckboxItem3.performClick()
            }
        }else{
            variables.fieldNameCheckbox3.assertDoesNotExist()
            variables.fieldCheckboxItem3.assertDoesNotExist()
        }

        if (SUMMARY_CREATED_TODAY.person4.isNotEmpty()){
            if (checkPerson4) {
                variables.fieldNameCheckbox4.assertIsDisplayed()
                variables.fieldNameCheckbox4.assertTextEquals(SUMMARY_CREATED_TODAY.person4)
                variables.fieldCheckboxItem4.assertIsDisplayed()
                variables.fieldCheckboxItem4.performClick()
            }
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

    private fun clickBtnRegisterSummary(){

        composeRule.apply {

            onNodeWithTag(
                context.getString(R.string.card_summary_non_exist_btn_create_tag),
                useUnmergedTree = true
            ).assertIsDisplayed().performClick()

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