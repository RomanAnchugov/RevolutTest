package ru.romananchugov.feature_converter.data.repository

import ru.romananchugov.feature_converter.data.model.ConverterResultDataModel
import ru.romananchugov.feature_converter.data.retrofit.ConverterService
import ru.romananchugov.feature_converter.domain.respository.ConverterRepository

internal class ConverterListRepositoryImpl(
    private val converterService: ConverterService
) : ConverterRepository {
    override suspend fun getConverterList(base: String): ConverterResultDataModel {
        val apiResponse = converterService.getConverterListAsync(base)
        val ratesMapWithBase = mutableMapOf(apiResponse.base to 1f)//add base element
        ratesMapWithBase.putAll(apiResponse.rates)
        return ConverterResultDataModel(
            base = apiResponse.base,
            date = apiResponse.date,
            rates = ratesMapWithBase
        )
    }
}