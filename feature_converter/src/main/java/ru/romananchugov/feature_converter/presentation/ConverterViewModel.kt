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

    companion object {
        private const val CONVERTER_UPDATE_INTERVAL_MILLIS = 1000L
    }


    override fun init() {
        sendAction(ViewAction.ConverterLoading)

        viewModelScope.launch {
            try {
                sendAction(ViewAction.ConverterLoading)
                val result = useCase.loadConverterList(ConverterCurrenciesDomainEnum.USD)
                Timber.tag("LOL").i("Result is $result")
                sendAction(ViewAction.ConverterLoaded(converterData = result.toPresentationModel()))
            } catch (e: Exception) {
                Timber.tag("LOL").i("Error is $e")
                sendAction(ViewAction.ConverterLoadingError(R.string.default_error_message))
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
                converterData = null
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        useCase.clear()
    }

    fun onConverterListItemFocus(item: ConverterCurrencyWithFlagItem) {
        val result = useCase.setNewBase(item.getCurrencyAbbreviation())
        sendAction(ViewAction.ConverterLoaded(converterData = result.toPresentationModel()))
    }

    fun onBaseRateChanged(value: String?) {
        value?.toFloatOrNull()?.let {
            val result = useCase.changeBaseValue(it)
            sendAction(ViewAction.ConverterLoaded(converterData = result.toPresentationModel()))
        }
    }

    data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val converterData: ConverterPresentationModel? = null
    ) : BaseState

    internal sealed class ViewAction : BaseAction {
        object ConverterLoading : ViewAction()
        data class ConverterLoaded(val converterData: ConverterPresentationModel?) : ViewAction()
        data class ConverterLoadingError(@StringRes val errorMessageString: Int) : ViewAction()
    }
}