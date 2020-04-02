package ru.romananchugov.feature_converter.data.model

import com.squareup.moshi.Json
import ru.romananchugov.feature_converter.domain.model.ConverterDomainModel

internal data class ConverterNWDataModel(
    @field:Json(name = "baseCurrency") val base: String,
    @field:Json(name = "rates") val rates: Map<String, Float>
)