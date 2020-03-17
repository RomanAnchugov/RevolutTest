package ru.romananchugov.feature_converter.domain.model

import ru.romananchugov.feature_converter.domain.enum.ConverterCurrenciesDomainEnum

data class ConverterItemDomainModel(
    val currencyName: String,
    val currencyRate: Float
)