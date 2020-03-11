package ru.romananchugov.feature_converter.domain.ext

//TODO: indexes checks
fun <T> MutableList<T>.swap(pos1: Int, pos2: Int): MutableList<T> {
    val tmp = this[pos1]
    this[pos1] = this[pos2]
    this[pos2] = tmp
    return this
}