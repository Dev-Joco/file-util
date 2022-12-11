package com.jojo.fileutil.tree

import java.io.File

object FileTreeBuilder {

    fun build(path: String): FileTree {
        val file = File(path)
        val rootNode = Node.newInstance(file)

        if (rootNode is DirectoryNode) {
            traverse(file, rootNode)
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