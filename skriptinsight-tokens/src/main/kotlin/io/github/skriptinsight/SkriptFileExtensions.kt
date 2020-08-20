package io.github.skriptinsight

import io.github.skriptinsight.file.SkriptFile
import io.github.skriptinsight.file.computeIndentationLevelsForNode
import io.github.skriptinsight.file.node.SkriptNode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import java.io.File

suspend fun SkriptFile.printStructuralTree(): String {
    return buildString {
        val fileRootNodes = rootNodes

        fileRootNodes.forEach { it.printNodeChildren(this) }
    }
}

private fun SkriptNode.printNodeChildren(sb: StringBuilder) {
    sb.apply {
        val parentSpace = parent?.normalizedIndentCount?.plus(1) ?: 0
        append(" ".repeat(parentSpace))

        val dashAmount = (normalizedIndentCount - parentSpace).coerceAtLeast(0)
        if (dashAmount != 0)
            append('|')
        append("-".repeat(dashAmount))

        append(content)
        append(comment)
        append(System.lineSeparator())

        children?.forEach { it.printNodeChildren(this) }
    }
}