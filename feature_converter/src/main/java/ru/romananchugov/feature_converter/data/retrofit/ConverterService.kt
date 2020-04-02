package ru.romananchugov.feature_converter.data.retrofit

import retrofit2.http.GET
import retrofit2.http.Query
import ru.romananchugov.feature_converter.data.model.ConverterNWDataModel

internal interface ConverterService {
    @GET("/api/android/latest")
    suspend fun getConverterListAsync(@Query("base") base: String): ConverterNWDataModel
}