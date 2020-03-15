package ru.romananchugov.feature_converter.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "converter")
data class ConverterItemDBDataModel(
    @PrimaryKey val currencyName: String,
    val currencyRate: Float
)