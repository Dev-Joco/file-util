package com.jojo.fileutil.tree

import java.io.File
import java.lang.Exception

object FileTreeBuilder {

    fun build(path: String): FileTree? {
        val file = File(path)
        val rootNode = Node.newInstance(file)

        try {
            if (rootNode is DirectoryNode) {
                traverse(file, rootNode)
            }
        } catch (e: Exception) {
            return null
        }

        return FileTree(rootNode)
    }

    private fun traverse(file: File, parent: DirectoryNode) {
        val subFiles = file.listFiles() ?: return

        for (subFile in subFiles) {
            val child = Node.newInstance(subFile)
            child.parent = parent

            parent.add(child)

            if (child is DirectoryNode) {
                traverse(subFile, child)
            }

            parent.sort()
        }
    }
}