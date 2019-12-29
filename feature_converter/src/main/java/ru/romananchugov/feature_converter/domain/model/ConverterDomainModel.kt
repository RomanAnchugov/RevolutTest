package ru.romananchugov.feature_converter.domain.model

import ru.romananchugov.feature_converter.domain.enum.ConverterBasesDomainEnum

data class ConverterDomainModel(
    val base: String,
    val date: String,
    val rates: Map<String, Float>
)

data class ConverterItemDomainModel(
    val name: ConverterBasesDomainEnum,
    val rate: Float
)