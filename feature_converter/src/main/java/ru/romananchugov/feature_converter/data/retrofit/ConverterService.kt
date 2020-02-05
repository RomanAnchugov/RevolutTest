package ru.romananchugov.feature_converter.data.retrofit

import retrofit2.http.GET
import retrofit2.http.Query
import ru.romananchugov.feature_converter.data.model.ConverterListResultDataModel

internal interface ConverterService {
    @GET("/latest")
    suspend fun getConverterListAsync(@Query("base") base: String): ConverterListResultDataModel
}