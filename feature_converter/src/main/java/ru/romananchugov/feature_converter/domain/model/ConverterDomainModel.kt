package ru.romananchugov.feature_converter.domain.model

import ru.romananchugov.feature_converter.domain.enum.ConverterCurrenciesDomainEnum
import ru.romananchugov.feature_converter.presentation.model.ConverterCurrencyWithFlagItem
import ru.romananchugov.feature_converter.presentation.model.ConverterPresentationModel

//TODO: remade Pair with custom class
data class ConverterDomainModel(
    val rates: List<ConverterItemDomainModel>
)

//TODO: move this method to presentation layer
fun ConverterDomainModel.toPresentationModel(): ConverterPresentationModel {
    //base and top element
    val converterList = mutableListOf<ConverterCurrencyWithFlagItem>()

    converterList.addAll(
        rates.map {
            ConverterCurrencyWithFlagItem(
                currency = ConverterCurrenciesDomainEnum.valueOf(it.currencyName),//TODO: valueOf might be unsafe?
                _rate = it.currencyRate
            )
        }
    )

    return ConverterPresentationModel(converterList)
}