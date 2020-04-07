package ru.romananchugov.feature_converter.presentation

import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import ru.romananchugov.core.base.presentation.viewmodel.BaseAction
import ru.romananchugov.core.base.presentation.viewmodel.BaseState
import ru.romananchugov.core.base.presentation.viewmodel.BaseViewModel
import ru.romananchugov.feature_converter.R
import ru.romananchugov.feature_converter.domain.enum.ConverterCurrenciesDomainEnum
import ru.romananchugov.feature_converter.domain.model.toPresentationModel
import ru.romananchugov.feature_converter.domain.use_case.ConverterUseCase
import ru.romananchugov.feature_converter.presentation.model.ConverterCurrencyWithFlagItem
import ru.romananchugov.feature_converter.presentation.model.ConverterPresentationModel
import timber.log.Timber

@ExperimentalCoroutinesApi
@FlowPreview
internal class ConverterViewModel(
    private val useCase: ConverterUseCase
) : BaseViewModel<ConverterViewModel.ViewState, ConverterViewModel.ViewAction>(ViewState()) {

    override fun init() {
        sendAction(ViewAction.ConverterLoading)

        viewModelScope.launch {
            try {
                sendAction(ViewAction.ConverterLoading)
                useCase.loadConverterList(ConverterCurrenciesDomainEnum.USD)
            } catch (e: Exception) {
                Timber.tag("LOL").i("Exception in ViewModel $e")
                sendAction(ViewAction.ConverterLoadingError(R.string.default_error_message))
            }
        }

        viewModelScope.launch {
            for (result in useCase.stateChannel) {
                sendAction(ViewAction.ConverterLoaded(result.toPresentationModel()))
            }
        }
    }

    override fun handleAction(viewAction: ViewAction): ViewState = when (viewAction) {
        is ViewAction.ConverterLoading -> {
            ViewState(
                isLoading = true,
                isError = false,
                converterData = null
            )
        }
        is ViewAction.ConverterLoaded -> {
            ViewState(
                isLoading = false,
                isError = false,
                converterData = viewAction.converterData
            )
        }
        is ViewAction.ConverterLoadingError -> {
            ViewState(
                isLoading = false,
                isError = true,
                errorMessageId = viewAction.errorMessageString,
                converterData = null
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        useCase.clear()
    }

    fun onConverterListItemFocus(item: ConverterCurrencyWithFlagItem) {
        viewModelScope.launch {
            useCase.setNewBase(item.getCurrencyAbbreviation())
        }
    }

    fun onBaseRateChanged(value: String?) {
        value?.toFloatOrNull()?.let {
            viewModelScope.launch {
                useCase.changeBaseValue(it)
            }
        }
    }

    fun onStubRetryClick() {
        //TODO: maybe remade it, bring out some login in separate method
        init()
    }

    data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        @StringRes
        val errorMessageId: Int = R.string.default_error_message,
        val converterData: ConverterPresentationModel? = null
    ) : BaseState

    internal sealed class ViewAction : BaseAction {
        object ConverterLoading : ViewAction()
        data class ConverterLoaded(val converterData: ConverterPresentationModel?) : ViewAction()
        data class ConverterLoadingError(@StringRes val errorMessageString: Int) : ViewAction()
    }
}