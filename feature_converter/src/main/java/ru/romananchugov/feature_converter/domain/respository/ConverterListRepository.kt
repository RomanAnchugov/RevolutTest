package ru.romananchugov.feature_converter.domain.respository

import ru.romananchugov.feature_converter.domain.enum.ConverterDomainBaseEnum
import ru.romananchugov.feature_converter.domain.model.ConverterDomainModel

interface ConverterListRepository {
    suspend fun getConvertationList(base: String): ConverterDomainModel?
}