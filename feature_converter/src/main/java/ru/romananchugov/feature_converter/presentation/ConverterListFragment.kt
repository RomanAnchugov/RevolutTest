package ru.romananchugov.feature_converter.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.romananchugov.core.base.presentation.BaseFragment
import ru.romananchugov.feature_converter.R

class ConverterListFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_converter_list, container, false)
    }


}
