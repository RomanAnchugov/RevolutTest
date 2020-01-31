package ru.romananchugov.feature_converter.data.retrofit

import retrofit2.http.GET
import retrofit2.http.Query
import ru.romananchugov.feature_converter.data.model.ConvertationListResultDataModel

internal interface ConverterService {
    @GET("/latest")
    suspend fun getConvertationListAsync(@Query("base") base: String): ConvertationListResultDataModel
}