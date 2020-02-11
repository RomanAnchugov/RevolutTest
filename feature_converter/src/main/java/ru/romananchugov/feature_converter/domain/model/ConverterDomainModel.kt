package ru.romananchugov.feature_converter.domain.model

import ru.romananchugov.feature_converter.domain.enum.ConverterCurrenciesDomainEnum
import ru.romananchugov.feature_converter.presentation.model.ConverterCurrencyWithFlagItem
import ru.romananchugov.feature_converter.presentation.model.ConverterPresentationModel

data class ConverterDomainModel(
    val base: ConverterCurrenciesDomainEnum,
    val date: String,
    val rates: Map<String, Float>
)

//TODO: move this method to presentation layer
fun ConverterDomainModel.toPresentationModel(): ConverterPresentationModel {
    //base and top element
    val converterList = mutableListOf(
        ConverterCurrencyWithFlagItem(
            base,
            1f
        )
    )

    converterList.addAll(
        rates.keys.map { currencyName ->
            ConverterCurrencyWithFlagItem(
                currency = ConverterCurrenciesDomainEnum.valueOf(currencyName),//TODO: valueOf might be unsafe?
                rate = rates[currencyName] ?: 1f
            )
        }
    )

    return ConverterPresentationModel(converterList)
}