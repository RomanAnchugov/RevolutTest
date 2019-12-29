package ru.romananchugov.feature_converter.data.retrofit

import retrofit2.http.GET
import retrofit2.http.Query

internal interface ConverterService {
    @GET("/latest")
    suspend fun getConvertationListAsync(@Query("base") base: String): ConvertationListResultDataModel
//    @GET("/")
//    suspend fun getConvertationListAsync(): ConvertationListResultDataModel
}