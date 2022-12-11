package com.jojo.fileutil.tree

import java.io.File

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

    private val children: MutableList<Node> = mutableListOf()

    val size: Int get() = children.size
    val firstChild: Node get() = children.first()
    val lastChild: Node get() = children.last()
    val isEmpty: Boolean get() = children.isEmpty()

    operator fun get(index: Int): Node = children[index]

    operator fun iterator(): Iterator<Node> = children.iterator()

    override fun toString(): String = "($name)"

    fun add(node: Node) {
        children.add(node)
    }

    fun sort() {
        children.sort()
    }

    fun makeCompactPackage(): List<DirectoryNode> {
        val pack = mutableListOf(this)

        var curr: DirectoryNode = this
        var next: Node? = curr.children.getOrNull(0)

        while (curr.children.size == 1 && next is DirectoryNode) {
            pack.add(next)
            curr = next
            next = next.children.getOrNull(0)
        }

        return pack
    }
}

data class FileNode(
    override val name: String
) : Node() {

    override var parent: DirectoryNode? = null
    override val isLeaf: Boolean = true
    override val isLastChild: Boolean get() = parent?.lastChild == this || isRoot

    override fun toString(): String = name
}