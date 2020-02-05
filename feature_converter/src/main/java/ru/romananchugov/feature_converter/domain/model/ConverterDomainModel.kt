package ru.romananchugov.feature_converter.domain.model

import ru.romananchugov.feature_converter.domain.enum.ConverterCurrenciesDomainEnum
import ru.romananchugov.feature_converter.presentation.model.ConverterCurrencyWithFlag
import ru.romananchugov.feature_converter.presentation.model.ConverterPresentationModel

data class ConverterDomainModel(
    val base: ConverterCurrenciesDomainEnum,
    val date: String,
    val rates: Map<String, Float>
)

fun ConverterDomainModel.toPresentationModel(): ConverterPresentationModel {
    return ConverterPresentationModel(
        rates.keys.map { currencyName ->
            ConverterCurrencyWithFlag(currency = ConverterCurrenciesDomainEnum.valueOf(currencyName))//TODO: valueOf might be unsafe?
        }
    )
}