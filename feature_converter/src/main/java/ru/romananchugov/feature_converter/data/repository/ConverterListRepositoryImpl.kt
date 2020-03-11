package ru.romananchugov.feature_converter.data.repository

import ru.romananchugov.feature_converter.data.model.ConverterResultDataModel
import ru.romananchugov.feature_converter.data.retrofit.ConverterService
import ru.romananchugov.feature_converter.domain.ext.swap
import ru.romananchugov.feature_converter.domain.respository.ConverterRepository

internal class ConverterListRepositoryImpl(
    private val converterService: ConverterService
) : ConverterRepository {

    private lateinit var lastConverterResult: ConverterResultDataModel
    private var lastConverterBase = "USD"//at start load with this base

    override suspend fun getConverterList(): ConverterResultDataModel {
        val apiResponse = converterService.getConverterListAsync(lastConverterBase)
        val ratesMapWithBase = mutableMapOf(apiResponse.base to 1f)//add base element
        ratesMapWithBase.putAll(apiResponse.rates)
        val responseWithBase = ConverterResultDataModel(
            base = apiResponse.base,
            date = apiResponse.date,
            rates = ratesMapWithBase
        )
        lastConverterResult = responseWithBase
        lastConverterBase = apiResponse.base
        return responseWithBase
    }

    override suspend fun getConverterLastResult(): ConverterResultDataModel {
        return lastConverterResult
    }
}