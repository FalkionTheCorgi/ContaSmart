package com.example.accountspayable.Data

import android.content.Context
import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import com.example.accountspayable.R

class VariableTest(
    context: Context,
    composeRule: SemanticsNodeInteractionsProvider
) {

    val summaryReceipt = composeRule.onNodeWithTag(
        context.getString(R.string.card_summary_exist_receipt_tag),
        useUnmergedTree = true
    )
    val summaryExpenditure = composeRule.onNodeWithTag(
        context.getString(R.string.card_summary_exist_expenditure_tag),
        useUnmergedTree = true
    )
    val summaryData = composeRule.onNodeWithTag(
        context.getString(R.string.card_summary_exist_data_tag),
        useUnmergedTree = true
    )
    val summaryRemainder = composeRule.onNodeWithTag(
        context.getString(R.string.card_summary_exist_remainder_tag),
        useUnmergedTree = true
    )
    val summaryPerson1Name = composeRule.onNodeWithTag(
        context.getString(R.string.card_summary_exist_person1_tag),
        useUnmergedTree = true
    )
    val summaryPerson2Name = composeRule.onNodeWithTag(
        context.getString(R.string.card_summary_exist_person2_tag),
        useUnmergedTree = true
    )
    val summaryPerson3Name = composeRule.onNodeWithTag(
        context.getString(R.string.card_summary_exist_person3_tag),
        useUnmergedTree = true
    )
    val summaryPerson4Name = composeRule.onNodeWithTag(
        context.getString(R.string.card_summary_exist_person4_tag),
        useUnmergedTree = true
    )
    val summaryPerson1Value = composeRule.onNodeWithTag(
        context.getString(R.string.card_summary_exist_person1_value_tag),
        useUnmergedTree = true
    )
    val summaryPerson2Value = composeRule.onNodeWithTag(
        context.getString(R.string.card_summary_exist_person2_value_tag),
        useUnmergedTree = true
    )
    val summaryPerson3Value = composeRule.onNodeWithTag(
        context.getString(R.string.card_summary_exist_person3_value_tag),
        useUnmergedTree = true
    )
    val summaryPerson4Value = composeRule.onNodeWithTag(
        context.getString(R.string.card_summary_exist_person4_value_tag),
        useUnmergedTree = true
    )
    val itemTag = composeRule.onNodeWithTag(
        context.getString(R.string.card_item_tag),
        useUnmergedTree = true
    )
    val fieldName = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_item_name_tag
        ), useUnmergedTree = true
    )

    val fieldValue = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_item_value_tag
        ),
        useUnmergedTree = true
    )

    val fieldDeadline = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_item_deadline_tag
        ),
        useUnmergedTree = true
    )

    val fieldDescription = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_item_description_tag
        ),
        useUnmergedTree = true
    )

    val fieldIcon = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_item_dropdown_button_tag
        ),
        useUnmergedTree = true
    )


    val fieldIconDropDownItemLight = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_item_dropdown_item_tag,
            itemsDropDown.first()
        ),
        useUnmergedTree = true
    )

    val fieldIconDropDownItemWater = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_item_dropdown_item_tag,
            itemsDropDown[1]
        ),
        useUnmergedTree = true
    )

    val fieldIconDropDownItemMarket = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_item_dropdown_item_tag,
            itemsDropDown[2]
        ),
        useUnmergedTree = true
    )

    val fieldIconDropDownItemRouter = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_item_dropdown_item_tag,
            itemsDropDown[3]
        ),
        useUnmergedTree = true
    )

    val fieldIconDropDownItemCard= composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_item_dropdown_item_tag,
            itemsDropDown[4]
        ),
        useUnmergedTree = true
    )

    val fieldIconDropDownItemRestaurant = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_item_dropdown_item_tag,
            itemsDropDown[5]
        ),
        useUnmergedTree = true
    )

    val fieldIconDropDownItemPhone = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_item_dropdown_item_tag,
            itemsDropDown[6]
        ),
        useUnmergedTree = true
    )

    val fieldIconDropDownItemHouse = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_item_dropdown_item_tag,
            itemsDropDown[7]
        ),
        useUnmergedTree = true
    )

    val fieldIconDropDownItemGame = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_item_dropdown_item_tag,
            itemsDropDown[8]
        ),
        useUnmergedTree = true
    )

    val fieldIconDropDownItemOther = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_item_dropdown_item_tag,
            itemsDropDown[9]
        ),
        useUnmergedTree = true
    )

    val fieldNameCheckbox1 = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_item_name_person1_item_tag
        ),
        useUnmergedTree = true
    )

    val fieldNameCheckbox2 = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_item_name_person2_item_tag
        ),
        useUnmergedTree = true
    )

    val fieldNameCheckbox3 = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_item_name_person3_item_tag
        ),
        useUnmergedTree = true
    )

    val fieldNameCheckbox4 = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_item_name_person4_item_tag
        ),
        useUnmergedTree = true
    )

    val fieldCheckboxItem1 = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_item_checkbox_person1_item_tag
        ),
        useUnmergedTree = true
    )

    val fieldCheckboxItem2 = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_item_checkbox_person2_item_tag
        ),
        useUnmergedTree = true
    )

    val fieldCheckboxItem3 = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_item_checkbox_person3_item_tag
        ),
        useUnmergedTree = true
    )

    val fieldCheckboxItem4 = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_item_checkbox_person4_item_tag
        ),
        useUnmergedTree = true
    )

    val fieldBtnSave = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_item_btn_save_tag
        ),
        useUnmergedTree = true
    )

    val fieldBtmSheetMonth = composeRule.onNodeWithTag(
        context.getString(R.string.bottomsheet_calendar_month_btn_tag),
        useUnmergedTree = true
    )

    val fieldBtmSheetMonthJan = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_calendar_month_item_tag,
            returnMonth(context)[0]
        ),
        useUnmergedTree = true
    )

    val fieldBtmSheetMonthFeb = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_calendar_month_item_tag,
            returnMonth(context)[1]
        ),
        useUnmergedTree = true
    )

    val fieldBtmSheetMonthMar = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_calendar_month_item_tag,
            returnMonth(context)[2]
        ),
        useUnmergedTree = true
    )

    val fieldBtmSheetMonthApr = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_calendar_month_item_tag,
            returnMonth(context)[3]
        ),
        useUnmergedTree = true
    )

    val fieldBtmSheetMonthMay = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_calendar_month_item_tag,
            returnMonth(context)[4]
        ),
        useUnmergedTree = true
    )

    val fieldBtmSheetMonthJun = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_calendar_month_item_tag,
            returnMonth(context)[5]
        ),
        useUnmergedTree = true
    )

    val fieldBtmSheetMonthJul = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_calendar_month_item_tag,
            returnMonth(context)[6]
        ),
        useUnmergedTree = true
    )

    val fieldBtmSheetMonthAug = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_calendar_month_item_tag,
            returnMonth(context)[7]
        ),
        useUnmergedTree = true
    )

    val fieldBtmSheetMonthSep = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_calendar_month_item_tag,
            returnMonth(context)[8]
        ),
        useUnmergedTree = true
    )

    val fieldBtmSheetMonthOct = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_calendar_month_item_tag,
            returnMonth(context)[9]
        ),
        useUnmergedTree = true
    )

    val fieldBtmSheetMonthNov = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_calendar_month_item_tag,
            returnMonth(context)[10]
        ),
        useUnmergedTree = true
    )

    val fieldBtmSheetMonthDec = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_calendar_month_item_tag,
            returnMonth(context)[11]
        ),
        useUnmergedTree = true
    )

    val fieldBtmSheetYear = composeRule.onNodeWithTag(
        context.getString(R.string.bottomsheet_calendar_year_btn_tag),
        useUnmergedTree = true
    )

    val fieldBtmSheetYear2023 = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_calendar_year_item_tag,
            years[0]
        ),
        useUnmergedTree = true
    )

    val fieldBtmSheetYear2024 = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_calendar_year_item_tag,
            years[1]
        ),
        useUnmergedTree = true
    )

    val fieldBtmSheetYear2025 = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_calendar_year_item_tag,
            years[2]
        ),
        useUnmergedTree = true
    )

    val fieldBtmSheetYear2026 = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_calendar_year_item_tag,
            years[3]
        ),
        useUnmergedTree = true
    )

    val fieldBtmSheetYear2027 = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_calendar_year_item_tag,
            years[4]
        ),
        useUnmergedTree = true
    )

    val fieldBtmSheetYear2028 = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_calendar_year_item_tag,
            years[5]
        ),
        useUnmergedTree = true
    )

    val fieldBtmSheetYear2029 = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_calendar_year_item_tag,
            years[6]
        ),
        useUnmergedTree = true
    )

    val fieldBtmSheetYear2030 = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_calendar_year_item_tag,
            years[7]
        ),
        useUnmergedTree = true
    )

    val fieldBtmSheetYear2031 = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_calendar_year_item_tag,
            years[8]
        ),
        useUnmergedTree = true
    )

    val fieldBtmSheetYear2032 = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_calendar_year_item_tag,
            years[9]
        ),
        useUnmergedTree = true
    )

    val fieldBtmSheetYear2033 = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_calendar_year_item_tag,
            years[10]
        ),
        useUnmergedTree = true
    )

    val fieldBtmSheetCalendarBtnSave = composeRule.onNodeWithTag(
        context.getString(
            R.string.bottomsheet_calendar_btn_save_tag
        ),
        useUnmergedTree = true
    )

}

class NodeItem(
    index: Int,
    context: Context,
    composeRule: SemanticsNodeInteractionsProvider
){
    val itemValue = composeRule.onAllNodesWithTag(
        context.getString(R.string.card_item_value_tag),
        useUnmergedTree = true
    )[index]

    val itemDeadline = composeRule.onNodeWithTag(
        context.getString(R.string.card_item_deadline_tag),
        useUnmergedTree = true
    )

    val itemDescription = composeRule.onNodeWithTag(
        context.getString(R.string.card_item_description_tag),
        useUnmergedTree = true
    )

    val itemPerson1 = composeRule.onNodeWithTag(
        context.getString(R.string.card_item_person1_tag),
        useUnmergedTree = true
    )

    val itemPerson1Checkbox = composeRule.onNodeWithTag(
        context.getString(R.string.card_item_person1_checkbox_tag),
        useUnmergedTree = true
    )

    val itemPerson2 = composeRule.onNodeWithTag(
        context.getString(R.string.card_item_person2_tag),
        useUnmergedTree = true
    )

    val itemPerson2Checkbox = composeRule.onNodeWithTag(
        context.getString(R.string.card_item_person2_checkbox_tag),
        useUnmergedTree = true
    )

    val itemPerson3 = composeRule.onNodeWithTag(
        context.getString(R.string.card_item_person3_tag),
        useUnmergedTree = true
    )

    val itemPerson3Checkbox = composeRule.onNodeWithTag(
        context.getString(R.string.card_item_person3_checkbox_tag),
        useUnmergedTree = true
    )

    val itemPerson4 = composeRule.onNodeWithTag(
        context.getString(R.string.card_item_person4_tag),
        useUnmergedTree = true
    )

    val itemPerson4Checkbox = composeRule.onNodeWithTag(
        context.getString(R.string.card_item_person4_checkbox_tag),
        useUnmergedTree = true
    )
}