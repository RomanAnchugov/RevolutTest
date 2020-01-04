package ru.romananchugov.feature_converter.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.romananchugov.feature_converter.R
import ru.romananchugov.feature_converter.databinding.ItemConverterBinding
import ru.romananchugov.feature_converter.presentation.model.ConverterPresentationModel
import ru.romananchugov.revoluttest.presentation.adapter.AdapterDelegate
import ru.romananchugov.revoluttest.presentation.model.DisplayableItem

class ConverterListAdapter : AdapterDelegate<DisplayableItem>() {
    override fun isForViewTpye(items: List<DisplayableItem>, position: Int): Boolean {
        return items[position] is ConverterPresentationModel
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
        items: List<DisplayableItem>,
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val item = items[position] as ConverterPresentationModel
        (holder as ViewHolder).binding.item = item
    }

    inner class ViewHolder(val binding: ItemConverterBinding) :
        RecyclerView.ViewHolder(binding.root)
}