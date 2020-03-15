package ru.romananchugov.feature_converter.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.romananchugov.feature_converter.data.model.ConverterItemDBDataModel

@Database(entities = [ConverterItemDBDataModel::class], version = 1)
abstract class ConverterDB : RoomDatabase() {
    abstract fun converterDao(): ConverterDAO

    companion object {

        @Volatile
        private var INSTANCE: ConverterDB? = null
        private const val DB_NAME = "Converter.db"

        fun getInstance(context: Context): ConverterDB =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                    context.applicationContext,
                    ConverterDB::class.java, DB_NAME
                )
                .fallbackToDestructiveMigration()
                .build()
    }
}