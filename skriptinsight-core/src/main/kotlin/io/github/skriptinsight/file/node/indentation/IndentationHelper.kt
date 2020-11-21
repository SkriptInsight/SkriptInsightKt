package io.github.skriptinsight.file.node.indentation

import io.github.skriptinsight.file.SkriptFile
import io.github.skriptinsight.file.node.SkriptNode

fun computeNodeDataParents(file: SkriptFile) {
    // Compute indentation levels
    val indentationLevels = computeIndentationLevelsForNode(file.nodes.values)

    indentationLevels.forEach {
        file.runProcess(ComputeNodeDataParentAndChildrenProcess(it))
    }
}

fun computeIndentationLevelsForNode(
    nodeData: MutableCollection<SkriptNode>
): List<Int> {
    val firstIndent = nodeData.firstOrNull()?.indentCount ?: 0

    return nodeData
        // First, take all the nodes that are the same indent or are a child of the first indent
        .takeWhile { it.indentCount == firstIndent || it.isChildrenAccordingToIndent(firstIndent) }
        // Then, select all indentations
        .flatMap { it.indentations.asIterable() }
        // Now, group by the amount of indentations
        .groupBy { it.amount }
        // Return the key of the group (aka the amount)
        .map { it.key }
        .toMutableList().apply {
            //Insert level zero
            add(0, 0)
        }
}

fun SkriptNode.isOnSameIndentLevel(currentLevel: Int): Boolean {
    if (indentations.isEmpty() && currentLevel == 0) return true

    return indentCount == currentLevel
}

internal fun SkriptNode.isChildrenAccordingToIndent(indent: Int): Boolean {
    return this.indentCount > indent //TODO: || node is EmptyLineNode || node is CommentLineNode
}
