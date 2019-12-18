package ru.romananchugov.revoluttest

import android.os.Bundle
import ru.romananchugov.core.base.presentation.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
