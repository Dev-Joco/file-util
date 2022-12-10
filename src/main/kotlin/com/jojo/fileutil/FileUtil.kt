package com.jojo.fileutil

import java.io.File

object FileUtil {

    private const val SPACE = ' '
    private const val DASH = '─'
    private const val COL1 = '│'
    private const val COL2 = '├'
    private const val COL3 = '└'
    private val LINE_SEPARATOR = System.lineSeparator()

    private val directoryFirstComparator = Comparator<File> { f1, f2 ->
        if (f1.isDirectory && f2.isFile) -1
        else if (f1.isFile && f2.isDirectory) 1
        else 0
    }

    fun printSubTree(path: String) {
        val file = File(path)

        traverse(file)
    }

    private fun traverse(file: File) {
        println(buildFileName("", file))

        traverseInOrder("", file)
    }

    private fun traverseInOrder(overlapParents: String, parent: File, depth: Int = 0, prefix: String = "") {
        val subFiles = parent.listFiles()
            ?.sortedWith(directoryFirstComparator)
            ?: return

        for ((index, file) in subFiles.withIndex()) {
            val isLast = (index == subFiles.lastIndex)

            println(buildCurrentLine(
                prefix = prefix,
                overlapParents = overlapParents,
                file = file,
                isLast = isLast,
            ))

            traverseInOrder(
                overlapParents = "",
                parent = file,
                depth = depth + 1,
                prefix = buildNextPrefix(prefix, isLast),
            )
        }
    }

    private fun traverseInOrder2(overlapParents: String, parent: File, depth: Int = 0, prefix: String = "") {
        val subFiles = parent.listFiles()
            ?.sortedWith(directoryFirstComparator)
            ?: return



        if (subFiles.size == 1 && subFiles.first().isDirectory) {
            traverseInOrder2(
                overlapParents = "$overlapParents${subFiles.first().name}.",
                parent = subFiles.first(),
                depth = depth,
                prefix = prefix,
            )
        } else {
            for ((index, file) in subFiles.withIndex()) {
                val isLast = (index == subFiles.lastIndex)

                println(buildCurrentLine(
                    prefix = prefix,
                    overlapParents = overlapParents,
                    file = file,
                    isLast = isLast,
                ))

                traverseInOrder2(
                    overlapParents = "",
                    parent = file,
                    depth = depth + 1,
                    prefix = buildNextPrefix(prefix, isLast),
                )
            }
        }
    }

    private fun buildCurrentLine(
        prefix: String,
        overlapParents: String,
        file: File,
        isLast: Boolean
    ): String {
        val format = "${prefix}%c${DASH * 2}$SPACE${buildFileName(overlapParents, file)}"
        val isExistsSubFiles = if (file.isFile) {
            false
        } else {
            file.list()?.isNotEmpty() == true
        }

        return if (!isLast) {
            if (file.isFile || isExistsSubFiles) {
                format.format(COL2)
            } else {
                format.format(COL2) + LINE_SEPARATOR + prefix + COL1
            }
        } else {
            if (isExistsSubFiles) {
                format.format(COL3)
            } else {
                format.format(COL3) + LINE_SEPARATOR + prefix
            }
        }
    }

    private fun buildNextPrefix(prefix: String, isLast: Boolean): String {
        return if (!isLast) {
            prefix + COL1 + (SPACE * 3)
        } else {
            prefix + (SPACE * 4)
        }
    }

    private fun buildFileName(overlapParents: String, file: File): String {
        return if (file.isDirectory) "($overlapParents${file.name})" else file.name
    }

    private operator fun Char.times(number: Int): String = buildString {
        for (i in 0 until number) append(this@times)
    }

    private operator fun CharSequence.times(number: Int): String = buildString {
        for (i in 0 until number) append(this@times)
    }
}