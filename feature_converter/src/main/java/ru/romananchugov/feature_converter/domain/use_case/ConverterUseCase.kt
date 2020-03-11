package ru.romananchugov.feature_converter.domain.use_case

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import ru.romananchugov.core.base.domain.BaseUseCase
import ru.romananchugov.feature_converter.data.model.toDomainModel
import ru.romananchugov.feature_converter.domain.enum.ConverterCurrenciesDomainEnum
import ru.romananchugov.feature_converter.domain.ext.swap
import ru.romananchugov.feature_converter.domain.model.ConverterDomainModel
import ru.romananchugov.feature_converter.domain.respository.ConverterRepository

@ExperimentalCoroutinesApi
interface ConverterUseCase{
    //TODO: maybe made it in BaseUseCase with generic
    val dataChannel: BroadcastChannel<ConverterDomainModel>

    suspend fun loadConverterList(base: ConverterCurrenciesDomainEnum)
    fun setNewBase(baseAbbr: String)
    fun changeBaseValue(newValue: Float)
}

@ExperimentalCoroutinesApi
internal class ConverterUseCaseImpl(
    private val converterListRepository: ConverterRepository
) : ConverterUseCase, BaseUseCase() {
    override val dataChannel: BroadcastChannel<ConverterDomainModel> = BroadcastChannel(1)

    private lateinit var baseValues: ConverterDomainModel
    private lateinit var lastResult: ConverterDomainModel

    override suspend fun loadConverterList(base: ConverterCurrenciesDomainEnum) {
        lastResult = converterListRepository.getConverterList().toDomainModel()
        baseValues = lastResult.copy()
        dataChannel.offer(lastResult)
    }

    override fun setNewBase(baseAbbr: String) {
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

    override fun changeBaseValue(newValue: Float) {
        val newList = lastResult.rates.toMutableList()
        val ratio = newValue / lastResult.rates[0].second
        newList[0] = newList[0].copy(second = newValue)
        for (i in 1 until newList.size) {
            if (newList[i].second == 0f) {
                val lastBase = getBaseValue(newList[i].first)
                lastBase?.let {
                    newList[i] = newList[i].copy(second = lastBase.second * newValue)
                }
            } else {
                newList[i] = newList[i].copy(second = newList[i].second * ratio)
            }
        }
        lastResult = lastResult.copy(rates = newList)
        dataChannel.offer(lastResult)
    }

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

    private fun getBaseValue(currency: String): Pair<String, Float>? {
        return baseValues.rates.find { it.first == currency }
    }
}