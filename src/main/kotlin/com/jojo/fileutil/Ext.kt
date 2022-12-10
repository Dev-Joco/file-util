package com.jojo.fileutil

import java.io.File

internal val File.subFileCount: Int
    get() = list()?.size ?: 0

internal fun File.isEmptySubFile(): Boolean {
    return subFileCount == 0
}

internal fun File.isNotEmpty(): Boolean {
    return subFileCount > 0
}

internal fun File.subFiles(): List<File> {
    return listFiles()?.toList() ?: emptyList()
}

internal fun File.subFileNames(): List<String> {
    return list()?.toList() ?: emptyList()
}