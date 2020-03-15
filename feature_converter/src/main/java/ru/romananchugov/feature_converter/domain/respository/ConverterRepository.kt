package ru.romananchugov.feature_converter.domain.respository

import ru.romananchugov.core.base.data.BaseRepository
import ru.romananchugov.feature_converter.data.model.ConverterDataModel

internal interface ConverterRepository: BaseRepository {
    suspend fun getConverterList(base: String): ConverterDataModel
}