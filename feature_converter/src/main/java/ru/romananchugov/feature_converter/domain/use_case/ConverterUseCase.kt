package ru.romananchugov.feature_converter.domain.use_case

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import ru.romananchugov.core.base.domain.BaseUseCase
import ru.romananchugov.feature_converter.domain.enum.ConverterCurrenciesDomainEnum
import ru.romananchugov.feature_converter.domain.ext.swap
import ru.romananchugov.feature_converter.domain.ext.toDomainModel
import ru.romananchugov.feature_converter.domain.model.ConverterDomainModel
import ru.romananchugov.feature_converter.domain.model.ConverterItemDomainModel
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
                Timber.tag("LOL").i("Iteration")
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
            if (pair.currencyName == baseAbbr) {
                newPosition = index
            }
        }

        val newList = lastResult.rates.toMutableList()
        newList.swap(0, newPosition)
        lastResult = lastResult.copy(rates = newList)

        //TODO: maybe made it(lastResult and offer) in BaseUseCase
        dataChannel.offer(lastResult)
    }

    //Method call, when user change/rewrite value in top(base)
    //We just recalculate whole list
    override fun changeBaseValue(newValue: Float) {
        val newList = lastResult.rates.toMutableList()
        newList[0] = newList[0].copy(currencyRate = newValue)

        getBaseValue(newList[0].currencyName)?.currencyRate?.let {
            val ratio = newValue / it
            for (i in 0 until newList.size) {
                val currencyBase = getBaseValue(newList[i].currencyName)
                currencyBase?.let {
                    newList[i] = newList[i].copy(currencyRate = currencyBase.currencyRate * ratio)
                }
            }
            lastResult = lastResult.copy(rates = newList)
            dataChannel.offer(lastResult)
        }
    }

    override fun clear() {
        coroutineContext.cancel()
        dataChannel.close()
    }

    //Set new basesList, and after that recalculate actual converter list
    private fun mapNewBase(newBases: ConverterDomainModel) {
        val newConverterList = mutableListOf<ConverterItemDomainModel>()
        baseValues = baseValues.copy(rates = newBases.rates)
        getBaseValue(lastResult.rates[0].currencyName)?.let { currency ->
            val ratio = lastResult.rates[0].currencyRate / currency.currencyRate
            for (i in lastResult.rates.indices) {
                getBaseValue(lastResult.rates[i].currencyName)?.let {
                    newConverterList.add(
                        ConverterItemDomainModel(
                            currencyName = it.currencyName,
                            currencyRate = it.currencyRate * ratio
                        )
                    )
                }
            }
            lastResult = lastResult.copy(rates = newConverterList)
        }
    }

    //Get rate for specific currencyName
    private fun getBaseValue(currencyName: String): ConverterItemDomainModel? {
        return baseValues.rates.find { it.currencyName == currencyName }
    }
}