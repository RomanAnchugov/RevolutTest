package ru.romananchugov.feature_converter.domain.ext

import ru.romananchugov.feature_converter.data.model.ConverterDataModel
import ru.romananchugov.feature_converter.domain.model.ConverterDomainModel

fun ConverterDataModel.toDomainModel() = ConverterDomainModel(
    rates = this.converterList.map {
        it.currencyName to it.currencyRate
    }
)