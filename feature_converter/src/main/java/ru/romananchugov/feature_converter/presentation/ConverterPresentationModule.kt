package ru.romananchugov.feature_converter.presentation

import org.koin.dsl.module

val converterPresentationModule = module {
    single { ConverterViewModel(get()) }
}