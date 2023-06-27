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