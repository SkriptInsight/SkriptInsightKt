package io.github.skriptinsight.file

import hu.akarnokd.kotlin.flow.groupBy
import io.github.skriptinsight.file.node.SkriptNode
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.coroutines.suspendCoroutine

suspend fun computeNodeDataParents(nodeData: Flow<SkriptNode>) {
    // Compute indentation levels
    val indentationLevels = computeIndentationLevelsForNode(nodeData)

    indentationLevels.collect { currentLevel ->
        nodeData.collectIndexed { index, node ->
            if (node.isSectionNode && node.isOnSameIndentLevel(currentLevel)) {
                //Compute all child nodes
                val childrenNodes = nodeData.drop(index + 1).takeWhile { it.isChildrenAccordingToIndent(currentLevel) }

                childrenNodes.collect { child ->
                    //Set node as parent of the child
                    child.parent = node
                }
            }
        }
    }

}

suspend fun computeIndentationLevelsForNode(
    nodeData: Flow<SkriptNode>
): Flow<Int> {
    val firstIndent = nodeData.firstOrNull()?.indentCount ?: 0

    return flow {
        emit(0)

        emitAll(nodeData
            // First, take all the nodes that are the same indent or are a child of the first indent
            .takeWhile { it.indentCount == firstIndent || it.isChildrenAccordingToIndent(firstIndent) }
            // Then, select all indentations
            .flatMapConcat { it.indentations.asFlow() }
            // Now, group by the amount of indentations
            .groupBy { it.amount }
            // Return the key of the group (aka the amount)
            .map { it.key }
        )
    }
}

private fun SkriptNode.isOnSameIndentLevel(currentLevel: Int): Boolean {
    if (indentations.isEmpty() && currentLevel == 0) return true

    return indentCount == currentLevel
}

private fun SkriptNode.isChildrenAccordingToIndent(indent: Int): Boolean {
    return this.indentCount > indent //TODO: || node is EmptyLineNode || node is CommentLineNode
}
