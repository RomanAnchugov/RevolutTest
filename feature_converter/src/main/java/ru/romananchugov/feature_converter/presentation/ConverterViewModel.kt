package ru.romananchugov.feature_converter.presentation

import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
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
                sendAction(ViewAction.ConverterLoadingError(R.string.default_error_message))
            }
        }

        //Subscribe to data channel in useCase
        //TODO: maybe made it in baseUseCase
        viewModelScope.launch {
            useCase.dataChannel
                .asFlow()
                .collect {
                    sendAction(ViewAction.ConverterLoaded(converterData = it.toPresentationModel()))
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

    //TODO: maybe move this logic to UseCase
    fun onConverterListItemFocus(item: ConverterCurrencyWithFlagItem) {
        useCase.setNewBase(item.getCurrencyAbbreviation())
    }

    //TODO: maybe move this logic to UseCase
    fun onBaseRateChanged(value: String?) {
        //TODO: checking for correctness


//        val newList = viewStateLiveData.value?.converterData?.itemsList?.toMutableList()
//        newList?.apply {
//
//            value?.toFloatOrNull()?.let {
//
//                val newRate = if (this[0].rate != 0f) it / this[0].rate else 1f
//                this[0].rate *= newRate
//                for (i in 1 until size) {
//                    this[i].rate *= newRate
//                }
//            }
//
//            val model = viewStateLiveData.value?.converterData?.copy(itemsList = this)
//            sendAction(ViewAction.ConverterLoaded(model))
//        }
//
        value?.toFloatOrNull()?.let {
            useCase.changeBaseValue(it)
        }

//        value?.toFloatOrNull()?.let {
//            viewModelScope.launch {
//                val newState = useCase.changeBaseValue(it)
//                val newList = newState.rates
////                viewStateLiveData.value?.converterData?.itemsList?.let {
////                    newList.forEachIndexed { index, pair ->
////                        it[index].rate = newList[index].second
////                    }
////                }
//                sendAction(ViewAction.ConverterLoaded(newState.toPresentationModel()))
//            }
//        }
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