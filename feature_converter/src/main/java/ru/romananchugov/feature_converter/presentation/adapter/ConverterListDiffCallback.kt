package ru.romananchugov.feature_converter.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.romananchugov.feature_converter.presentation.model.ConverterCurrencyWithFlagItem
import ru.romananchugov.revoluttest.presentation.model.DisplayableItem
import timber.log.Timber

class ConverterListDiffCallback: DiffUtil.ItemCallback<DisplayableItem>() {

    override fun areItemsTheSame(oldItem: DisplayableItem, newItem: DisplayableItem): Boolean {
        val oItem = oldItem as ConverterCurrencyWithFlagItem
        val nItem = newItem as ConverterCurrencyWithFlagItem
        return oItem.getCurrencyAbbreviation() == nItem.getCurrencyAbbreviation()
    }

    override fun areContentsTheSame(oldItem: DisplayableItem, newItem: DisplayableItem): Boolean {
        val oItem = oldItem as ConverterCurrencyWithFlagItem
        val nItem = newItem as ConverterCurrencyWithFlagItem

        val result = oItem.rate == nItem.rate
        return result
    }

    override fun getChangePayload(oldItem: DisplayableItem, newItem: DisplayableItem): Any? {
        val oItem = oldItem as ConverterCurrencyWithFlagItem
        val nItem = newItem as ConverterCurrencyWithFlagItem
        return when {
            oItem.rate != nItem.rate -> nItem.rate
            else -> null
        }
    }

}