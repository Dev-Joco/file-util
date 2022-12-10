package com.jojo.fileutil.util

internal operator fun Char.times(number: Int): String = buildString {
    for (i in 0 until number) append(this@times)
}

internal operator fun CharSequence.times(number: Int): String = buildString {
    for (i in 0 until number) append(this@times)
}