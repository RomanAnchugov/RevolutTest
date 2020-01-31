package ru.romananchugov.feature_converter.data.repository

import ru.romananchugov.feature_converter.data.model.toDomainModel
import ru.romananchugov.feature_converter.data.retrofit.ConverterService
import ru.romananchugov.feature_converter.domain.model.ConverterDomainModel
import ru.romananchugov.feature_converter.domain.respository.ConverterListRepository

internal class ConverterListRepositoryImpl(
    private val converterService: ConverterService
) : ConverterListRepository {

    override suspend fun getConvertationList(base: String): ConverterDomainModel? {
        return converterService.getConvertationListAsync(base).toDomainModel()
    }
}