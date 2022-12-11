package com.jojo.fileutil.tree

class FileTree(private val root: Node) {

    fun traverse() {
        traversePreOrder(root, "")
    }

    private fun traversePreOrder(node: Node, prefix: String) {
        if (node is FileNode) {
            println(buildBranchOf(node, prefix))
            return
        }

        val compactPackage = (node as DirectoryNode).makeCompactPackage()
        val firstDirectory = compactPackage.first()
        val lastDirectory = compactPackage.last()

        println(buildBranchOf(compactPackage, prefix))

        for (childNode in lastDirectory) {
            traversePreOrder(childNode, makeNextPrefix(firstDirectory, prefix))
        }

        if (isRequiredLineSpace(lastDirectory)) {
            println(buildLineSpace(firstDirectory, prefix))
        }
    }

    private fun buildBranchOf(compactPackage: List<DirectoryNode>, prefix: String): String {
        val lastDirectory = compactPackage.last()
        if (lastDirectory.isRoot)
            return lastDirectory.toString()

        val name = if (compactPackage.size <= 1) {
            lastDirectory.toString()
        } else {
            compactPackageToString(compactPackage)
        }

        val firstDirectory = compactPackage.first()

        return prefix + makeBranch(firstDirectory, name)
    }

    private fun buildBranchOf(file: FileNode, prefix: String): String {
        if (file.isRoot)
            return file.toString()

        return prefix + makeBranch(file, file.toString())
    }

    private fun makeBranch(node: Node, name: String): String {
        val branch = if (node.isLastChild) BRANCH_L else BRANCH_E
        return "$branch$DASH_2$SPACE$name"
    }

    private fun compactPackageToString(compactPackage: List<DirectoryNode>): String {
        return compactPackage.joinToString(separator = ".", prefix = "(", postfix = ")") { it.name }
    }

    private fun isRequiredLineSpace(directory: DirectoryNode): Boolean {
        return directory.isEmpty || directory.lastChild !is DirectoryNode
    }

    private fun buildLineSpace(directory: DirectoryNode, prefix: String): String {
        return if (directory.isLastChild) prefix else prefix + BRANCH_I
    }

    private fun makeNextPrefix(node: Node, prefix: String): String = when {
        node.isRoot -> prefix
        node.isLastChild -> prefix + SPACE_4
        else -> prefix + BRANCH_I + SPACE_3
    }

    companion object {
        private const val SPACE = ' '
        private const val SPACE_3 = "   "
        private const val SPACE_4 = "    "
        private const val DASH = '─'
        private const val DASH_2 = "──"
        private const val BRANCH_I = '│'
        private const val BRANCH_E = '├'
        private const val BRANCH_L = '└'
        private val LINE_SEPARATOR = System.lineSeparator()
    }
}
