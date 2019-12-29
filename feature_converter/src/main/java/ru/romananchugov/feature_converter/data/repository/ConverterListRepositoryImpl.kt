package ru.romananchugov.feature_converter.data.repository

import ru.romananchugov.feature_converter.data.retrofit.ConverterService
import ru.romananchugov.feature_converter.domain.enum.ConverterDomainBaseEnum
import ru.romananchugov.feature_converter.domain.model.ConverterDomainModel
import ru.romananchugov.feature_converter.domain.respository.ConverterListRepository
import timber.log.Timber

internal class ConverterListRepositoryImpl(
    private val converterService: ConverterService
) : ConverterListRepository {

    override suspend fun getConvertationList(base: String): ConverterDomainModel? {

//        val result = converterService.getConvertationListAsync(base.name)

        Timber.tag("LOL").i(converterService.getConvertationListAsync(base).toString())
        return null
    }
}