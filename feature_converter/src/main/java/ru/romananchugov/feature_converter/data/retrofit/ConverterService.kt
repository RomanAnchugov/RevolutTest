package ru.romananchugov.feature_converter.data.retrofit

import retrofit2.http.GET
import retrofit2.http.Query
import ru.romananchugov.feature_converter.data.model.ConverterResultDataModel

internal interface ConverterService {
    @GET("/latest")
    suspend fun getConverterListAsync(@Query("base") base: String): ConverterResultDataModel
}