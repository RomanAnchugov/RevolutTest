package ru.romananchugov.feature_converter.domain.model

import ru.romananchugov.feature_converter.presentation.model.ConverterListItem
import ru.romananchugov.feature_converter.presentation.model.ConverterPresentationModel

data class ConverterDomainModel(
    val base: String,
    val date: String,
    val rates: Map<String, Float>
)

fun ConverterDomainModel.toPresentationModel(): ConverterPresentationModel {
    return ConverterPresentationModel(
        rates.keys.map { currencyName ->
            ConverterListItem(currencyName, rates.getOrElse(currencyName) { 1f })
        }
    )
}