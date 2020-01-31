package ru.romananchugov.feature_converter.presentation

import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.romananchugov.core.base.presentation.viewmodel.BaseAction
import ru.romananchugov.core.base.presentation.viewmodel.BaseState
import ru.romananchugov.core.base.presentation.viewmodel.BaseViewModel
import ru.romananchugov.feature_converter.R
import ru.romananchugov.feature_converter.domain.enum.ConverterBasesDomainEnum
import ru.romananchugov.feature_converter.domain.model.toPresentationModel
import ru.romananchugov.feature_converter.domain.use_case.ConverterUseCase
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
                    useCase.getConverterList(ConverterBasesDomainEnum.USD)?.toPresentationModel()

                sendAction(ViewAction.ConverterLoaded(result))
            } catch (e: Exception) {
                Timber.tag("LOL").i("WOOOOPS ${e.message}")
                sendAction(ViewAction.ConverterLoadingError(R.string.default_error_message))
            }
        }
    }

    override fun handleAction(viewAction: ViewAction): ViewState = when (viewAction) {
        is ViewAction.ConverterLoading -> {
            Timber.tag("LOL").i("Start to suck ass")
            ViewState(
                isLoading = true,
                isError = false,
                converterData = null
            )
        }
        is ViewAction.ConverterLoaded -> {
            Timber.tag("LOL").i("Loadede suck ass $viewAction")
            ViewState(
                isLoading = false,
                isError = false,
                converterData = viewAction.converterData
            )
        }
        is ViewAction.ConverterLoadingError -> {
            Timber.tag("LOL").i("Real suck ass")
            ViewState(
                isLoading = false,
                isError = true,
                converterData = null
            )
        }
    }

    internal data class ViewState(
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