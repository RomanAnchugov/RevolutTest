package ru.romananchugov.feature_converter.data.model

import com.squareup.moshi.Json
import ru.romananchugov.feature_converter.domain.model.ConverterDomainModel

internal data class ConvertationListResultDataModel(
    @field:Json(name = "base") val base: String,
    @field:Json(name = "date") val date: String,
    @field:Json(name = "rates") val rates: Map<String, Float>
)

internal fun ConvertationListResultDataModel.toDomainModel(): ConverterDomainModel {
    return ConverterDomainModel(
        base = this.base,
        date = this.date,
        rates = this.rates
    )
}