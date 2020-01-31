package ru.romananchugov.feature_converter.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.romananchugov.feature_converter.presentation.model.ConverterPresentationModel
import ru.romananchugov.revoluttest.presentation.model.DisplayableItem

class ConverterListDiffCallback: DiffUtil.ItemCallback<DisplayableItem>() {
    override fun areItemsTheSame(oldItem: DisplayableItem, newItem: DisplayableItem): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItem: DisplayableItem, newItem: DisplayableItem): Boolean {
        return false
    }


}