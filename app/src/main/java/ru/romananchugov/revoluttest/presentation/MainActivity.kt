package ru.romananchugov.revoluttest.presentation

import android.os.Bundle
import ru.romananchugov.core.base.presentation.BaseActivity
import ru.romananchugov.revoluttest.R

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
