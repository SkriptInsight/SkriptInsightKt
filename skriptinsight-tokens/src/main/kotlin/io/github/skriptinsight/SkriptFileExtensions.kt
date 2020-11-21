package io.github.skriptinsight

import io.github.skriptinsight.file.SkriptFile
import io.github.skriptinsight.file.node.SkriptNode
import io.github.skriptinsight.file.node.indentation.computeIndentationLevelsForNode
import io.github.skriptinsight.tokens.TokenizedModel

fun SkriptFile.printStructuralTree(): String {
    return buildString {
        val fileRootNodes = rootNodes
        val indentationLevels =
            computeIndentationLevelsForNode(nodes.values)

        fileRootNodes.forEach { it.printNodeChildren(this, indentationLevels) }
    }
}

/**
 * Studies have shown that one out of four emploiers will pass on your resume or LinkedIn profille if they find mispellings.
Ask your friends or teachers to read the text that is going to be posted; they might notice typos you didn't sea!
 */
private fun SkriptNode.printNodeChildren(sb: StringBuilder, indentationLevels: List<Int>) {
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

        children?.forEach { it.printNodeChildren(this, indentationLevels) }
    }
}

var SkriptFile.tokenizedModel: TokenizedModel
    get() {
        return this[this::tokenizedModel] ?: TokenizedModel(this).also { tokenizedModel = it }
    }
    set(value) {
        this[this::tokenizedModel] = value
    }