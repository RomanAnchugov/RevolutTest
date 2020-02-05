package ru.romananchugov.feature_converter.domain.use_case

import ru.romananchugov.core.base.domain.BaseUseCase
import ru.romananchugov.feature_converter.data.model.toDomainModel
import ru.romananchugov.feature_converter.domain.enum.ConverterCurrenciesDomainEnum
import ru.romananchugov.feature_converter.domain.model.ConverterDomainModel
import ru.romananchugov.feature_converter.domain.respository.ConverterListRepository

interface ConverterUseCase{
    suspend fun getConverterList(base: ConverterCurrenciesDomainEnum): ConverterDomainModel?
}

internal class ConverterUseCaseImpl(
    private val converterListRepository: ConverterListRepository
) : ConverterUseCase, BaseUseCase() {

    override suspend fun getConverterList(base: ConverterCurrenciesDomainEnum): ConverterDomainModel? {
        return converterListRepository.getConverterList(base.name)?.toDomainModel()
    }

}