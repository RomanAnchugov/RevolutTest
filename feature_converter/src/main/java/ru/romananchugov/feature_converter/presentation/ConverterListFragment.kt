package ru.romananchugov.feature_converter.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.ext.android.inject
import ru.romananchugov.core.base.presentation.BaseFragment
import ru.romananchugov.core.ext.observe
import ru.romananchugov.feature_converter.R
import ru.romananchugov.feature_converter.databinding.FragmentConverterListBinding
import ru.romananchugov.feature_converter.presentation.adapter.ConverterListAdapter
import ru.romananchugov.feature_converter.presentation.model.ConverterPresentationModel
import ru.romananchugov.revoluttest.presentation.adapter.DelegationAdapter

internal class ConverterListFragment : BaseFragment<ConverterViewModel.ViewState>() {

    private val viewModel by inject<ConverterViewModel>()

    private val adapter = DelegationAdapter(ConverterListAdapter())

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
        binding.lifecycleOwner = this
        binding.converterRv.adapter = adapter
        binding.converterRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        adapter.add(ConverterPresentationModel("Test"))
        adapter.add(ConverterPresentationModel("Test"))
        adapter.add(ConverterPresentationModel("Test"))
        adapter.add(ConverterPresentationModel("Test"))
        adapter.add(ConverterPresentationModel("Test"))
        adapter.add(ConverterPresentationModel("Test"))
        adapter.add(ConverterPresentationModel("Test"))
        adapter.add(ConverterPresentationModel("Test"))
        adapter.add(ConverterPresentationModel("Test"))
        adapter.add(ConverterPresentationModel("Test"))


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init()
        observe(viewModel.viewStateLiveData, stateObserver)

        stateObserver?.let { viewModel.viewStateLiveData.observe(this, stateObserver) }
    }
}
