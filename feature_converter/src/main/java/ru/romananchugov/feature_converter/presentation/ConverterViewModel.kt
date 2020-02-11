package ru.romananchugov.feature_converter.presentation

import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
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

internal class ConverterViewModel(
    private val useCase: ConverterUseCase
) : BaseViewModel<ConverterViewModel.ViewState, ConverterViewModel.ViewAction>(ViewState()) {

    override fun init() {
        sendAction(ViewAction.ConverterLoading)
        viewModelScope.launch {
            try {
                sendAction(ViewAction.ConverterLoading)

                val result =
                    useCase.getConverterList(ConverterCurrenciesDomainEnum.USD)?.toPresentationModel()

                sendAction(ViewAction.ConverterLoaded(result))
            } catch (e: Exception) {
                Timber.tag("LOL").i("WOOOOPS ${e.message}")
                sendAction(ViewAction.ConverterLoadingError(R.string.default_error_message))
            }
        }
    }

    override fun handleAction(viewAction: ViewAction): ViewState = when (viewAction) {
        is ViewAction.ConverterLoading -> {
            Timber.tag("LOL").i("Action Loading")
            ViewState(
                isLoading = true,
                isError = false,
                converterData = null
            )
        }
        is ViewAction.ConverterLoaded -> {
            Timber.tag("LOL").i("Action Loaded $viewAction")
            ViewState(
                isLoading = false,
                isError = false,
                converterData = viewAction.converterData
            )
        }
        is ViewAction.ConverterLoadingError -> {
            Timber.tag("LOL").i("Action Error")
            ViewState(
                isLoading = false,
                isError = true,
                converterData = null
            )
        }
    }

    fun onConverterListItemFocus(item: ConverterCurrencyWithFlagItem) {
        val newList = viewStateLiveData.value?.converterData?.itemsList?.toMutableList()
        newList?.apply {

            val itemIndex = indexOf(item)
            //swap first and this
            val tmp = this[0]
            this[0] = this[itemIndex]
            this[itemIndex] = tmp

            val model = viewStateLiveData.value?.converterData?.copy(itemsList = this)
            sendAction(ViewAction.ConverterLoaded(model))
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