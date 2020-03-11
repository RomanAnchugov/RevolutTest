package ru.romananchugov.feature_converter.presentation.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.romananchugov.feature_converter.R
import ru.romananchugov.feature_converter.domain.enum.ConverterCurrenciesDomainEnum
import ru.romananchugov.revoluttest.presentation.model.DisplayableItem

data class ConverterCurrencyWithFlagItem(
    private val currency: ConverterCurrenciesDomainEnum = ConverterCurrenciesDomainEnum.USD,
    private val _rate: Float,
    private var _rateLiveData: MutableLiveData<Float> = MutableLiveData()
) : DisplayableItem{

    var rate = _rate
        set(value) {
            field = value
            _rateLiveData.postValue(field)
        }

    val rateLiveData: LiveData<Float>
        get() = _rateLiveData

    fun getCountryFlagId(): Int = when (currency) {
        ConverterCurrenciesDomainEnum.AUD -> {
            R.drawable.flag_au
        }
        ConverterCurrenciesDomainEnum.BGN -> {
            R.drawable.flag_bg
        }
        ConverterCurrenciesDomainEnum.BRL -> {
            R.drawable.flag_br
        }
        ConverterCurrenciesDomainEnum.CAD -> {
            R.drawable.flag_ca
        }
        ConverterCurrenciesDomainEnum.CHF -> {
            R.drawable.flag_li
        }
        ConverterCurrenciesDomainEnum.CNY -> {
            R.drawable.flag_cn
        }
        ConverterCurrenciesDomainEnum.CZK -> {
            R.drawable.flag_cz2
        }
        ConverterCurrenciesDomainEnum.DKK -> {
            R.drawable.flag_dk
        }
        ConverterCurrenciesDomainEnum.EUR -> {
            R.drawable.flag_eu
        }
        ConverterCurrenciesDomainEnum.GBP -> {
            R.drawable.flag_gb
        }
        ConverterCurrenciesDomainEnum.HKD -> {
            R.drawable.flag_hk
        }
        ConverterCurrenciesDomainEnum.HRK -> {
            R.drawable.flag_hr
        }
        ConverterCurrenciesDomainEnum.HUF -> {
            R.drawable.flag_hu
        }
        ConverterCurrenciesDomainEnum.IDR -> {
            R.drawable.flag_id
        }
        ConverterCurrenciesDomainEnum.ILS -> {
            R.drawable.flag_il
        }
        ConverterCurrenciesDomainEnum.INR -> {
            R.drawable.flag_bt
        }
        ConverterCurrenciesDomainEnum.ISK -> {
            R.drawable.flag_is
        }
        ConverterCurrenciesDomainEnum.JPY -> {
            R.drawable.flag_jp
        }
        ConverterCurrenciesDomainEnum.KRW -> {
            R.drawable.flag_kr
        }
        ConverterCurrenciesDomainEnum.MXN -> {
            R.drawable.flag_mx
        }
        ConverterCurrenciesDomainEnum.MYR -> {
            R.drawable.flag_my
        }
        ConverterCurrenciesDomainEnum.NOK -> {
            R.drawable.flag_no
        }
        ConverterCurrenciesDomainEnum.NZD -> {
            R.drawable.flag_nz
        }
        ConverterCurrenciesDomainEnum.PHP -> {
            R.drawable.flag_ph
        }
        ConverterCurrenciesDomainEnum.PLN -> {
            R.drawable.flag_pl
        }
        ConverterCurrenciesDomainEnum.RON -> {
            R.drawable.flag_ro
        }
        ConverterCurrenciesDomainEnum.RUB -> {
            R.drawable.flag_ru
        }
        ConverterCurrenciesDomainEnum.SEK -> {
            R.drawable.flag_se
        }
        ConverterCurrenciesDomainEnum.SGD -> {
            R.drawable.flag_sg
        }
        ConverterCurrenciesDomainEnum.THB -> {
            R.drawable.flag_th
        }
        ConverterCurrenciesDomainEnum.TRY -> {
            R.drawable.flag_tr
        }
        ConverterCurrenciesDomainEnum.USD -> {
            R.drawable.flag_us
        }
        ConverterCurrenciesDomainEnum.ZAR -> {
            R.drawable.flag_za
        }
    }

    fun getCurrencyAbbreviation(): String = currency.name

    fun getCurrencyFullName(): Int = when (currency) {
        ConverterCurrenciesDomainEnum.AUD -> R.string.AUD
        ConverterCurrenciesDomainEnum.BGN -> R.string.BGN
        ConverterCurrenciesDomainEnum.BRL -> R.string.BRL
        ConverterCurrenciesDomainEnum.CAD -> R.string.CAD
        ConverterCurrenciesDomainEnum.CHF -> R.string.CHF
        ConverterCurrenciesDomainEnum.CNY -> R.string.CNY
        ConverterCurrenciesDomainEnum.CZK -> R.string.CZK
        ConverterCurrenciesDomainEnum.DKK -> R.string.DKK
        ConverterCurrenciesDomainEnum.EUR -> R.string.EUR
        ConverterCurrenciesDomainEnum.GBP -> R.string.GBP
        ConverterCurrenciesDomainEnum.HKD -> R.string.HKD
        ConverterCurrenciesDomainEnum.HRK -> R.string.HRK
        ConverterCurrenciesDomainEnum.HUF -> R.string.HUF
        ConverterCurrenciesDomainEnum.IDR -> R.string.IDR
        ConverterCurrenciesDomainEnum.ILS -> R.string.ILS
        ConverterCurrenciesDomainEnum.INR -> R.string.INR
        ConverterCurrenciesDomainEnum.ISK -> R.string.ISK
        ConverterCurrenciesDomainEnum.JPY -> R.string.JPY
        ConverterCurrenciesDomainEnum.KRW -> R.string.KRW
        ConverterCurrenciesDomainEnum.MXN -> R.string.MXN
        ConverterCurrenciesDomainEnum.MYR -> R.string.MYR
        ConverterCurrenciesDomainEnum.NOK -> R.string.NOK
        ConverterCurrenciesDomainEnum.NZD -> R.string.NZD
        ConverterCurrenciesDomainEnum.PHP -> R.string.PHP
        ConverterCurrenciesDomainEnum.PLN -> R.string.PLN
        ConverterCurrenciesDomainEnum.RON -> R.string.RON
        ConverterCurrenciesDomainEnum.RUB -> R.string.RUB
        ConverterCurrenciesDomainEnum.SEK -> R.string.SEK
        ConverterCurrenciesDomainEnum.SGD -> R.string.SGD
        ConverterCurrenciesDomainEnum.THB -> R.string.THB
        ConverterCurrenciesDomainEnum.TRY -> R.string.TRY
        ConverterCurrenciesDomainEnum.USD -> R.string.USD
        ConverterCurrenciesDomainEnum.ZAR -> R.string.ZAR
    }

    fun getCurrencyRate(): String = rate.toString()
}

