package ru.romananchugov.feature_converter

import ru.romananchugov.feature_converter.data.converterDataModule
import ru.romananchugov.feature_converter.domain.converterDomainModule
import ru.romananchugov.feature_converter.presentation.converterPresentationModule
import ru.romananchugov.revoluttest.feature.KoinFeatureModulesProvider

object FeatureModule : KoinFeatureModulesProvider {
    override val modules =
        listOf(converterDataModule, converterDomainModule, converterPresentationModule)
}