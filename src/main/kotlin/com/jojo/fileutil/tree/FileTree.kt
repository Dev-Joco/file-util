package com.jojo.fileutil.tree

import java.io.File

class FileTree(private val root: Node) {

    fun traverse() {
        traverseInOrder(root, "")
    }

    private fun traverseInOrder(node: Node, prefix: String) {
        println(buildBranch(node, prefix))

        if (isRequireLineSpace(node)) {
            println(buildLineSpace(node, prefix))
        }

        for (child in (node as? DirectoryNode ?: return).children) {
            traverseInOrder(child, buildNextPrefix(node, prefix))
        }
    }

    private fun buildBranch(node: Node, prefix: String): String {
        if (node.isRoot)
            return node.toString()

        val branch = if (node.isLastChild) BRANCH_L else BRANCH_E
        return "$prefix$branch$DASH_2$SPACE$node"
    }

    private fun isRequireLineSpace(node: Node): Boolean = when (node) {
        is FileNode -> node.isLastChild
        is DirectoryNode -> node.isLeaf
    }

    private fun buildLineSpace(node: Node, prefix: String): String {
        return if (node.isLastChild) prefix else prefix + BRANCH_I
    }

    private fun buildNextPrefix(node: Node, prefix: String): String = when {
        node.isRoot -> prefix
        node.isLastChild -> prefix + SPACE_4
        else -> prefix + BRANCH_I + SPACE_3
    }

    companion object {
        private const val SPACE = ' '
        private const val SPACE_3 = "   "
        private const val SPACE_4 = "    "
        private const val DASH = '─'
        private const val DASH_2 = "─"
        private const val BRANCH_I = '│'
        private const val BRANCH_E = '├'
        private const val BRANCH_L = '└'
        private val LINE_SEPARATOR = System.lineSeparator()
    }
}

sealed class Node : Comparable<Node> {

    abstract val name: String
    abstract var parent: DirectoryNode?
    abstract val isLeaf: Boolean
    abstract val isLastChild: Boolean

    val isRoot: Boolean
        get() = parent == null

    override fun compareTo(other: Node): Int = when {
        this is DirectoryNode && other is FileNode -> -1
        this is FileNode && other is DirectoryNode -> 1
        else -> 0
    }

    companion object {
        fun newInstance(file: File): Node = if (file.isFile) {
            FileNode(file.name)
        } else {
            DirectoryNode(file.name)
        }
    }
}

data class DirectoryNode(
    override val name: String
) : Node() {

    override var parent: DirectoryNode? = null
    override val isLeaf: Boolean get() = children.isEmpty()
    override val isLastChild: Boolean get() = parent?.children?.last() == this || isRoot

    val compactPackage: MutableList<Node> = mutableListOf()
    val children: MutableList<Node> = mutableListOf()
    val isOnlyChild: Boolean get() = children.size == 1
    val firstChild: Node get() = children.first()

    override fun toString(): String = "($name)"
}

data class FileNode(
    override val name: String
) : Node() {

    override var parent: DirectoryNode? = null
    override val isLeaf: Boolean = true
    override val isLastChild: Boolean get() = parent?.children?.last() == this || isRoot

    override fun toString(): String = name
}