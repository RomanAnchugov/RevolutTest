package ru.romananchugov.feature_converter.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.romananchugov.core.base.presentation.BaseViewModel
import ru.romananchugov.feature_converter.domain.enum.ConverterDomainBaseEnum
import ru.romananchugov.feature_converter.domain.use_case.ConverterUseCase

class ConverterViewModel(
    private val useCase: ConverterUseCase
) : BaseViewModel() {
    fun test() {
        viewModelScope.launch {
            useCase.getConverterList(ConverterDomainBaseEnum.AUD)
        }
    }
}