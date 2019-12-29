package ru.romananchugov.feature_converter.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.romananchugov.core.base.presentation.BaseViewModel
import ru.romananchugov.feature_converter.domain.enum.ConverterBasesDomainEnum
import ru.romananchugov.feature_converter.domain.use_case.ConverterUseCase
import timber.log.Timber

class ConverterViewModel(
    private val useCase: ConverterUseCase
) : BaseViewModel() {
    fun test() {
        viewModelScope.launch {
            val result = useCase.getConverterList(ConverterBasesDomainEnum.AUD)
            Timber.tag("LOL").i("${result?.rates?.get(ConverterBasesDomainEnum.EUR.name)}")
        }
    }
}