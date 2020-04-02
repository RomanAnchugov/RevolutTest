package ru.romananchugov.feature_converter.data.repository

import android.util.Log
import ru.romananchugov.feature_converter.data.db.ConverterDB
import ru.romananchugov.feature_converter.data.model.ConverterDataModel
import ru.romananchugov.feature_converter.data.model.ConverterItemDBDataModel
import ru.romananchugov.feature_converter.data.model.ConverterItemDataModel
import ru.romananchugov.feature_converter.data.retrofit.ConverterService
import ru.romananchugov.feature_converter.domain.respository.ConverterRepository
import timber.log.Timber

internal class ConverterListRepositoryImpl(
    private val converterService: ConverterService,
    private val converterDataBase: ConverterDB
) : ConverterRepository {

    override suspend fun getConverterList(base: String): ConverterDataModel {
        try {
            val apiResponse = converterService.getConverterListAsync(base)
            val converterList = mutableListOf(
                ConverterItemDataModel(
                    currencyName = apiResponse.base,
                    currencyRate = 1f
                )
            )//add base element

            converterList.addAll(
                apiResponse.rates.map {
                    Timber.tag("LOL").i("${it.key} is ${it.value}")
                    ConverterItemDataModel(
                        currencyName = it.key,
                        currencyRate = it.value
                    )
                }
            )

            converterDataBase.converterDao().clearConverterTable()
            converterList.forEach {
                converterDataBase.converterDao().addCurrency(
                    ConverterItemDBDataModel(
                        it.currencyName,
                        it.currencyRate
                    )
                )
            }

            return ConverterDataModel(
                converterList = converterList
            )
        } catch (e: Exception) {
            val converterList = converterDataBase.converterDao().getConverter()
            if (converterList.isEmpty().not()) {
                return ConverterDataModel(
                    converterList = converterList.map {
                        ConverterItemDataModel(
                            currencyName = it.currencyName,
                            currencyRate = it.currencyRate
                        )
                    }
                )
            } else {
                throw e
            }
        }
    }
}