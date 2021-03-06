package ru.romananchugov.feature_converter.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.android.inject
import ru.romananchugov.core.base.presentation.BaseFragment
import ru.romananchugov.core.ext.observe
import ru.romananchugov.feature_converter.R
import ru.romananchugov.feature_converter.databinding.FragmentConverterListBinding
import ru.romananchugov.feature_converter.presentation.adapter.ConverterListAdapterDelegate
import ru.romananchugov.feature_converter.presentation.adapter.ConverterListDiffCallback
import ru.romananchugov.feature_converter.presentation.ext.hideKeyboard
import ru.romananchugov.revoluttest.presentation.adapter.DelegationAdapter
import timber.log.Timber

@FlowPreview
@ExperimentalCoroutinesApi
internal class ConverterListFragment : BaseFragment<ConverterViewModel.ViewState>() {

    private val viewModel by inject<ConverterViewModel>()

    private lateinit var binding: FragmentConverterListBinding
    override val stateObserver: Observer<ConverterViewModel.ViewState>? = Observer { state ->
        binding.viewState = state
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_converter_list,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.converterRv.adapter = DelegationAdapter(
            ConverterListDiffCallback(),
            ConverterListAdapterDelegate(
                viewLifecycleOwner,
                viewModel::onConverterListItemFocus,
                viewModel::onBaseRateChanged
            )
        )
        binding.converterRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.stub.stubButton.setOnClickListener {
            viewModel.onStubRetryClick()
        }

        viewModel.init()
        observe(viewModel.viewStateLiveData, stateObserver)
    }

    override fun onPause() {
        view?.hideKeyboard()
        super.onPause()
    }
}
