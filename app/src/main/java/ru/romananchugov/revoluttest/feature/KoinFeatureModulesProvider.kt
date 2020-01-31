package ru.romananchugov.revoluttest.feature

import org.koin.core.module.Module

interface KoinFeatureModulesProvider {
    val modules: List<Module>
}