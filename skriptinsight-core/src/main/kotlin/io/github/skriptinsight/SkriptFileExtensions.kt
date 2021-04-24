package io.github.skriptinsight

import io.github.skriptinsight.file.SkriptFile
import io.github.skriptinsight.file.node.SkriptNode
import io.github.skriptinsight.file.node.indentation.computeIndentationLevelsForNode

fun SkriptFile.printStructuralTree(): String {
    return buildString {
        val fileRootNodes = rootNodes
        val indentationLevels =
            computeIndentationLevelsForNode(nodes.values)

        fileRootNodes.forEach { it.printNodeChildren(this, indentationLevels) }
    }
}

private fun SkriptNode.printNodeChildren(sb: StringBuilder, indentationLevels: List<Int>) {
    sb.apply {
        val parentSpace = parent?.normalizedIndentCount?.takeIf { it > 0 }?.plus(1) ?: 0
        append(" ".repeat(parentSpace))

        val dashAmount = (normalizedIndentCount - parentSpace).coerceAtLeast(0)
        if (dashAmount != 0)
            append('|')
        append("-".repeat(dashAmount))

        append(content)
        append(comment)
        append(System.lineSeparator())

        children?.forEach { it.printNodeChildren(this, indentationLevels) }
    }
}