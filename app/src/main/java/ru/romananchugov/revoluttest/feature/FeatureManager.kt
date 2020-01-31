package ru.romananchugov.revoluttest.feature

// Dynamic Feature modules require reversed dependency (dynamic feature module depends on app module)
// This means we have to use reflection to access module content
// See: https://medium.com/mindorks/dynamic-feature-modules-the-future-4bee124c0f1
object FeatureManager {

    //TODO: take it out to the BuildConfig
    private val featuresNames = arrayOf("converter")

    private const val FEATURE_PREFIX = "ru.romananchugov.feature_"

    private const val FEATURE_MODULE_CLASS_NAME = "FeatureModule"

    val featureKoidModules = featuresNames
        .map { featureName ->
            "$FEATURE_PREFIX$featureName.$FEATURE_MODULE_CLASS_NAME"
        }
        .map { className ->
            try {
                Class.forName(className).kotlin.objectInstance as KoinFeatureModulesProvider
            } catch (e: ClassNotFoundException) {
                throw ClassNotFoundException("whooops")
            }
        }
        .map {
            it.modules
        }
        .flatten()

}