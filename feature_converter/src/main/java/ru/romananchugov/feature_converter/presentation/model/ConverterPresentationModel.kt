package ru.romananchugov.feature_converter.presentation.model

/**
 * Model that uses in presentation layer for all data in @see ConverterListFragment
 */
data class ConverterPresentationModel(
    val itemsList: List<ConverterCurrencyWithFlag>
)