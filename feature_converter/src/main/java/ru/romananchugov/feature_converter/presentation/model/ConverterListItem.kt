package ru.romananchugov.feature_converter.presentation.model

import ru.romananchugov.revoluttest.presentation.model.DisplayableItem

/**
 * Model that uses as item in @see ConverterListAdapterDelegate
 */
data class ConverterListItem(
    val currencyName: String,
    val currencyRate: Float

) : DisplayableItem