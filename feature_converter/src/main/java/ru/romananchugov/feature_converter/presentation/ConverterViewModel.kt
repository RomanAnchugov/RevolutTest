package ru.romananchugov.feature_converter.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.romananchugov.core.base.presentation.viewmodel.BaseAction
import ru.romananchugov.core.base.presentation.viewmodel.BaseState
import ru.romananchugov.core.base.presentation.viewmodel.BaseViewModel
import ru.romananchugov.feature_converter.domain.enum.ConverterBasesDomainEnum
import ru.romananchugov.feature_converter.domain.model.ConverterDomainModel
import ru.romananchugov.feature_converter.domain.use_case.ConverterUseCase
import timber.log.Timber

internal class ConverterViewModel(
    private val useCase: ConverterUseCase
) : BaseViewModel<ConverterViewModel.ViewState, ConverterViewModel.ViewAction>(ViewState()) {

    override fun init() {
        viewModelScope.launch {
            val result = useCase.getConverterList(ConverterBasesDomainEnum.AUD)
            sendAction(ViewAction.ConverterLoaded(result))
        }
    }

    override fun handleAction(viewAction: ViewAction): ViewState = when (viewAction) {
        is ViewAction.ConverterLoading -> {
            Timber.tag("LOL").i("Start to suck ass")
            ViewState(
                true,
                false,
                null
            )
        }
        is ViewAction.ConverterLoaded -> {
            Timber.tag("LOL").i("Loadede suck ass $viewAction")
            ViewState(
                false,
                false,
                viewAction.converterData
            )
        }
        is ViewAction.ConverterLoadingError -> {
            Timber.tag("LOL").i("Real suck ass")
            ViewState(
                false,
                true,
                null
            )
        }
    }

    internal data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val converterData: ConverterDomainModel? = null
    ) : BaseState

    internal sealed class ViewAction : BaseAction {
        object ConverterLoading : ViewAction()
        data class ConverterLoaded(val converterData: ConverterDomainModel?) : ViewAction()
        data class ConverterLoadingError(val errorMessage: String) : ViewAction()
    }
}