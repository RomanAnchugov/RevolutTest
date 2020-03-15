package ru.romananchugov.feature_converter.data.db

import androidx.room.*
import ru.romananchugov.feature_converter.data.model.ConverterItemDBDataModel

@Dao
interface ConverterDAO {
    @Query("SELECT * FROM converter")
    suspend fun getConverter(): List<ConverterItemDBDataModel>

    @Query("DELETE FROM converter")
    suspend fun clearConverterTable()

    @Insert
    suspend fun addCurrency(converItemDBDataModel: ConverterItemDBDataModel)

    @Update
    suspend fun updateCurrency(converterItemDBDataModel: ConverterItemDBDataModel)

    @Delete
    suspend fun deleteConverter(converItemDBDataModel: ConverterItemDBDataModel)
}