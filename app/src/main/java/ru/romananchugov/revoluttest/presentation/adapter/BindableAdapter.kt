package ru.romananchugov.revoluttest.presentation.adapter

/**
 * Interface that helps to use data for data producing to recycler
 * e.g.: ConverterListFragment
 */
interface BindableAdapter<T> {
    fun setData(data: T?)
}