package ru.romananchugov.feature_converter.domain.respository

import ru.romananchugov.feature_converter.data.model.ConverterResultDataModel

internal interface ConverterRepository {
    suspend fun getConverterList(): ConverterResultDataModel
    suspend fun getConverterLastResult(): ConverterResultDataModel
}