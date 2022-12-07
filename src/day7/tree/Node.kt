package day7.tree

typealias NodeType = String

const val File: NodeType = "FILE"
const val Directory: NodeType = "DIRECTORY"

open class Node(
    val type: NodeType,
    var children: List<Node>,
    private val size: Int,
    ) {
    val totalSize: Int get() = if (type == File) size else children.sumOf { it.totalSize }

    fun addChild(node: Node) {
        if (type != Directory) {
            throw InvalidOperationException("cannot add children to node of type $type")
        }
        children = children + node
    }
}