package ru.romananchugov.feature_converter.domain.respository

import ru.romananchugov.feature_converter.data.model.ConverterListResultDataModel

internal interface ConverterListRepository {
    suspend fun getConverterList(base: String): ConverterListResultDataModel?
}