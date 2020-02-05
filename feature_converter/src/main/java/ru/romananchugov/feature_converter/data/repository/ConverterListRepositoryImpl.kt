package ru.romananchugov.feature_converter.data.repository

import ru.romananchugov.feature_converter.data.model.ConverterListResultDataModel
import ru.romananchugov.feature_converter.data.retrofit.ConverterService
import ru.romananchugov.feature_converter.domain.respository.ConverterListRepository

internal class ConverterListRepositoryImpl(
    private val converterService: ConverterService
) : ConverterListRepository {

    override suspend fun getConverterList(base: String): ConverterListResultDataModel {
        return converterService.getConverterListAsync(base)
    }
}