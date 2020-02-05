package ru.romananchugov.feature_converter.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.romananchugov.feature_converter.R
import ru.romananchugov.feature_converter.databinding.ItemConverterBinding
import ru.romananchugov.feature_converter.presentation.model.ConverterCurrencyWithFlag
import ru.romananchugov.revoluttest.presentation.adapter.AdapterDelegate
import ru.romananchugov.revoluttest.presentation.model.DisplayableItem

class ConverterListAdapterDelegate : AdapterDelegate<DisplayableItem>() {
    override fun isForViewType(items: List<DisplayableItem>, position: Int): Boolean {
        return items[position] is ConverterCurrencyWithFlag
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<ItemConverterBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_converter,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        item: DisplayableItem,
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        (holder as ViewHolder).binding.item = item as ConverterCurrencyWithFlag
    }

    inner class ViewHolder(val binding: ItemConverterBinding) :
        RecyclerView.ViewHolder(binding.root)
}