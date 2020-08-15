package io.github.skriptinsight.file.node

import io.github.skriptinsight.file.node.indentation.IndentationData

/**
 * Represents a line from a [Skript file][io.github.skriptinsight.file.SkriptFile].
 *
 * @param lineNumber The number of this line.
 * @param rawContent The raw content of this line.
 * @param indentationData The information about the indentation of this line
 * @author NickAcPT
 */
data class SkriptNodeData(val lineNumber: Int, val rawContent: String, val indentationData: Array<IndentationData>) {
    companion object {
        fun fromLine(lineNumber: Int, content: String): SkriptNodeData {
            return SkriptNodeData(lineNumber,
                    content,
                    IndentationData.fromIndentation(content.takeWhile { it.isWhitespace() })
            )
        }
    }

    /**
     * The un-indented content of this line
     */
    val content: String = rawContent.dropWhile { it.isWhitespace() }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SkriptNodeData) return false

        if (lineNumber != other.lineNumber) return false
        if (rawContent != other.rawContent) return false
        if (!indentationData.contentEquals(other.indentationData)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = lineNumber.hashCode()
        result = 31 * result + rawContent.hashCode()
        result = 31 * result + indentationData.contentHashCode()
        return result
    }
}