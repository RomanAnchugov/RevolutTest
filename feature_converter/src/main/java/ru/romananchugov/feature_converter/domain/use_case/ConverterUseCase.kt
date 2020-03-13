package ru.romananchugov.feature_converter.domain.use_case

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import ru.romananchugov.core.base.domain.BaseUseCase
import ru.romananchugov.feature_converter.data.model.toDomainModel
import ru.romananchugov.feature_converter.domain.enum.ConverterCurrenciesDomainEnum
import ru.romananchugov.feature_converter.domain.ext.swap
import ru.romananchugov.feature_converter.domain.model.ConverterDomainModel
import ru.romananchugov.feature_converter.domain.respository.ConverterRepository
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

/**
 * Some vocabulary
 *
 * base - currency that in the top, and on dependency of which we calculate other currencies
 *
 * So we have list with bases, and actual converter list
 * We map and mult base to newBasevalue
 */
@ExperimentalCoroutinesApi
interface ConverterUseCase : BaseUseCase {
    //TODO: maybe made it in BaseUseCase with generic
    val dataChannel: BroadcastChannel<ConverterDomainModel>

    suspend fun loadConverterList(base: ConverterCurrenciesDomainEnum)
    fun setNewBase(baseAbbr: String)
    fun changeBaseValue(newValue: Float)
}

@ExperimentalCoroutinesApi
internal class ConverterUseCaseImpl(
    private val converterListRepository: ConverterRepository
) : ConverterUseCase {

    companion object {
        private const val CONVERTER_UPDATE_INTERVAL_MILLIS = 1000L
    }

    override val dataChannel: BroadcastChannel<ConverterDomainModel> = BroadcastChannel(1)

    override val coroutineContext: CoroutineContext
        get() = SupervisorJob() + Dispatchers.Main

    private lateinit var baseValues: ConverterDomainModel
    private lateinit var lastResult: ConverterDomainModel
    private var lastBase = "USD"

    //First method, that start allLoading
    //TODO: rename it
    override suspend fun loadConverterList(base: ConverterCurrenciesDomainEnum) {
        launch {
            lastResult = converterListRepository.getConverterList(lastBase).toDomainModel()
            baseValues = lastResult.copy()
            while (isActive) {
                val newBaseValues =
                    converterListRepository.getConverterList(lastBase).toDomainModel()
                mapNewBase(newBaseValues)
                dataChannel.offer(lastResult)
                delay(CONVERTER_UPDATE_INTERVAL_MILLIS)
            }
        }
    }

    //Set new base in converter list
    //Actually just swap two elements of new and past base currencies
    override fun setNewBase(baseAbbr: String) {
        lastBase = baseAbbr
        var newPosition = -1
        lastResult.rates.forEachIndexed { index, pair ->
            if (pair.first == baseAbbr) {
                newPosition = index
            }
        }
        val newList = lastResult.rates.toMutableList()
        recalcBases(newList[0].first, newList[newPosition].first)
        newList.swap(0, newPosition)
        lastResult = lastResult.copy(rates = newList)
        //TODO: maybe made it(lastResult and offer) in BaseUseCase
        dataChannel.offer(lastResult)
    }

    //Method call, when user change/rewrite value in top(base)
    //We just recalculate whole list
    override fun changeBaseValue(newValue: Float) {
        val newList = lastResult.rates.toMutableList()
        newList[0] = newList[0].copy(second = newValue)
        for (i in 1 until newList.size) {
            val lastBase = getBaseValue(newList[i].first)
            lastBase?.let {
                newList[i] = newList[i].copy(second = lastBase.second * newValue)
            }
        }
        lastResult = lastResult.copy(rates = newList)
        dataChannel.offer(lastResult)
    }

    override fun clear() {
        coroutineContext.cancel()
        dataChannel.close()
    }

    //When we change actual base, due to mapping of bases, we
    //have to change 1f in past base and value in new base
    //So new base set to 1f, old to ratio
    //We need this method to have and opportunity make recalculation in offline
    private fun recalcBases(oldBase: String, newBase: String) {
        var oldIndex = -1
        var newIndex = -1
        val newList = baseValues.rates.toMutableList()
        newList.forEachIndexed { index, pair ->
            if (pair.first == oldBase) oldIndex = index
            if (pair.first == newBase) newIndex = index
        }
        newList[oldIndex] =
            newList[oldIndex].copy(second = newList[oldIndex].second / newList[newIndex].second)
        newList[newIndex] = newList[newIndex].copy(second = 1f)
        baseValues = baseValues.copy(rates = newList)
    }

    //Set new basesList, and after that recalculate actual converter list
    //Because order of bases and actual list may be different
    private fun mapNewBase(newBases: ConverterDomainModel) {
        val newConverterList = mutableListOf<Pair<String, Float>>()
        baseValues = baseValues.copy(rates = newBases.rates)
        for (i in lastResult.rates.indices) {
            getBaseValue(lastResult.rates[i].first)?.let {
                newConverterList.add(it.first to it.second * lastResult.rates[0].second)
            }
        }
        lastResult = lastResult.copy(rates = newConverterList)
    }

    //Get rate for specific currencyName
    private fun getBaseValue(currencyName: String): Pair<String, Float>? {
        return baseValues.rates.find { it.first == currencyName }
    }
}