package ru.romananchugov.feature_converter.data.retrofit

import com.squareup.moshi.Json
import ru.romananchugov.feature_converter.domain.model.ConverterDomainModel

internal data class ConvertationListResultDataModel(
    @field:Json(name = "base") val base: String,
    @field:Json(name = "date") val date: String
//    @field:Json(name = "details")
//    val details: String
)

internal fun ConvertationListResultDataModel.toDomainModel(): ConverterDomainModel {
    return ConverterDomainModel(
        base = "123",
        date = "er",
        rates = emptyMap()
    )
}