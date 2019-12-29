package ru.romananchugov.feature_converter.domain

import org.koin.dsl.module
import ru.romananchugov.feature_converter.domain.use_case.ConverterUseCase
import ru.romananchugov.feature_converter.domain.use_case.ConverterUseCaseImpl

val converterDomainModule = module {
    single { ConverterUseCaseImpl(get()) as ConverterUseCase }
}