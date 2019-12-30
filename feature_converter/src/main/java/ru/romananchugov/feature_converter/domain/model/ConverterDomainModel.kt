package ru.romananchugov.feature_converter.domain.model

data class ConverterDomainModel(
    val base: String,
    val date: String,
    val rates: Map<String, Float>
)