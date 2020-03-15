package ru.romananchugov.feature_converter.data.model

import com.squareup.moshi.Json
import ru.romananchugov.feature_converter.domain.model.ConverterDomainModel

internal data class ConverterNWDataModel(
    @field:Json(name = "base") val base: String,
    @field:Json(name = "date") val date: String,
    @field:Json(name = "rates") val rates: Map<String, Float>
)

//TODO: by clean it should be placed in domain layer, so create an Mappers class with this
// extention and in others layers
internal fun ConverterNWDataModel.toDomainModel(): ConverterDomainModel {
    //adding base element
    return ConverterDomainModel(
        rates = rates.toList()
    )
}