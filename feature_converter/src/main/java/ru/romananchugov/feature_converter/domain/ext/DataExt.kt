package ru.romananchugov.feature_converter.domain.ext

import ru.romananchugov.feature_converter.data.model.ConverterDataModel
import ru.romananchugov.feature_converter.domain.enum.ConverterCurrenciesDomainEnum
import ru.romananchugov.feature_converter.domain.model.ConverterDomainModel
import ru.romananchugov.feature_converter.domain.model.ConverterItemDomainModel

fun ConverterDataModel.toDomainModel() = ConverterDomainModel(
    rates = this.converterList.map {
        ConverterItemDomainModel(
            currencyName = it.currencyName,//TODO: might be unsafe?
            currencyRate = it.currencyRate
        )
    }
)