package ru.romananchugov.feature_converter.data

import android.content.Context
import androidx.room.Room
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.romananchugov.feature_converter.data.db.ConverterDB
import ru.romananchugov.feature_converter.data.repository.ConverterListRepositoryImpl
import ru.romananchugov.feature_converter.data.retrofit.ConverterService
import ru.romananchugov.feature_converter.domain.respository.ConverterRepository

val converterDataModule = module {

    single { provideHttpLoggingInterceptor() }

    single { provideOkHttpClient(get()) }

    single { provideRetrofit(get()) }

    single { provideConverterService(get()) }

    single {
        ConverterListRepositoryImpl(
            get(),
            get()
        ) as ConverterRepository
    }

    single {
        ConverterDB.getInstance(androidContext())
    }
}

private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    return logging
}

private fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
    OkHttpClient().newBuilder().addInterceptor(httpLoggingInterceptor).build()

private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
    //TODO: take BASE_URL out to somewhere(mb BuildConfig?)
    .baseUrl("https://revolut.duckdns.org/")
    .client(okHttpClient)
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

private fun provideConverterService(retrofit: Retrofit): ConverterService =
    retrofit.create(ConverterService::class.java)

private fun provideConverterDB(context: Context): ConverterDB = Room.databaseBuilder(
        context,
        ConverterDB::class.java,
        "ConverterDatabase"
    )
    .build()