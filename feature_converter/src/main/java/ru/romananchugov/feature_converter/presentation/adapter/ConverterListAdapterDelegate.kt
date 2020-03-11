package ru.romananchugov.feature_converter.presentation.adapter

import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import ru.romananchugov.feature_converter.R
import ru.romananchugov.feature_converter.databinding.ItemConverterBinding
import ru.romananchugov.feature_converter.presentation.ext.hideKeyboard
import ru.romananchugov.feature_converter.presentation.ext.showKeyboard
import ru.romananchugov.feature_converter.presentation.model.ConverterCurrencyWithFlagItem
import ru.romananchugov.revoluttest.presentation.adapter.AdapterDelegate
import ru.romananchugov.revoluttest.presentation.model.DisplayableItem
import timber.log.Timber


internal class ConverterListAdapterDelegate(
    private val viewLifecycleOwner: LifecycleOwner,
    val itemSelectedListener: (item: ConverterCurrencyWithFlagItem) -> Unit,
    val rateChangedListener: (value: String?) -> Unit
) : AdapterDelegate<DisplayableItem>() {

    private val numberInputType = InputType.TYPE_CLASS_NUMBER or
            InputType.TYPE_NUMBER_FLAG_DECIMAL or
            InputType.TYPE_NUMBER_FLAG_SIGNED

    override fun isForViewType(items: List<DisplayableItem>, position: Int): Boolean {
        return items[position] is ConverterCurrencyWithFlagItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: ItemConverterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_converter,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    private fun selectItem(editText: AppCompatEditText, item: ConverterCurrencyWithFlagItem) {
        itemSelectedListener(item)
        with(editText) {
            inputType = numberInputType
            requestFocus()
            setSelection(text?.length ?: 0)
            showKeyboard()
        }
    }

    inner class ViewHolder(val binding: ItemConverterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ConverterCurrencyWithFlagItem) {
            binding.item = item

            binding.root.setOnClickListener {
                selectItem(binding.converterItemCurrencyRateEt, item)
            }
            binding.converterItemCurrencyRateEt.setOnFocusChangeListener { view, isFocus ->
                if (isFocus) {
                    selectItem(view as AppCompatEditText, item)
                } else {
                    view.hideKeyboard()
                }
            }

            binding.converterItemCurrencyRateEt.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(newString: Editable?) {

                    if (binding.converterItemCurrencyRateEt.isFocused) {
                        rateChangedListener(newString?.toString())
                    }
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            })

            Timber.tag("LOL").i("bind1 for ${binding.converterItemCurrencyRateEt.text.toString()}")
        }

        fun bind(rate: String) {
            if(binding.converterItemCurrencyRateEt.isFocused.not()) {
                binding.converterItemCurrencyRateEt.setText(rate)
                Timber.tag("LOL")
                    .i("bind2 for ${binding.converterItemCurrencyRateEt.text.toString()}")
            }
        }
    }

    override fun onBindViewHolder(
        item: DisplayableItem,
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {

        if (payloads.isEmpty()) {
            (holder as ViewHolder).bind(item as ConverterCurrencyWithFlagItem)
        } else if (payloads.firstOrNull() is Float) {
            (holder as ViewHolder).bind(payloads.first().toString())
        }
    }

    override fun onBindViewHolder(
        item: DisplayableItem,
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        (holder as ViewHolder).bind(item as ConverterCurrencyWithFlagItem)
    }
}