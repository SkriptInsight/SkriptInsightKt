package io.github.skriptinsight.file

import io.github.skriptinsight.file.node.SkriptNode

fun computeNodeDataParents(nodeData: MutableCollection<SkriptNode>) {
    val firstIndent = nodeData.firstOrNull()?.indentCount ?: 0

    // Compute indentation levels
    val indentationLevels = computeIndentationLevelsForNode(nodeData, firstIndent)

    indentationLevels.forEach { currentLevel ->
        nodeData.forEachIndexed { index, node ->
            if (node.isSectionNode && node.isOnSameIndentLevel(currentLevel)) {
                //Compute all child nodes
                val childrenNodes = nodeData.drop(index + 1).takeWhile { it.isChildrenAccordingToIndent(currentLevel) }

                childrenNodes.forEach { child ->
                    //Set node as parent of the child
                    child.parent = node
                }
            }
        }
    }
}

private fun computeIndentationLevelsForNode(
    nodeData: MutableCollection<SkriptNode>,
    firstIndent: Int
): List<Int> {
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

private fun SkriptNode.isOnSameIndentLevel(currentLevel: Int): Boolean {
    if (indentations.isEmpty() && currentLevel == 0) return true

    return indentCount == currentLevel
}

private fun SkriptNode.isChildrenAccordingToIndent(indent: Int): Boolean {
    return this.indentCount > indent //TODO: || node is EmptyLineNode || node is CommentLineNode
}
