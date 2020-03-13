package ru.romananchugov.core.base.domain

import kotlinx.coroutines.CoroutineScope

interface BaseUseCase: CoroutineScope {
    fun clear()
}