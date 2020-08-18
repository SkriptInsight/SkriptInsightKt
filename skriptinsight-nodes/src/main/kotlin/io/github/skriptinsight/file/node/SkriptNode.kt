package io.github.skriptinsight.file.node

import io.github.skriptinsight.file.linePattern
import io.github.skriptinsight.file.location.Position
import io.github.skriptinsight.file.location.Range
import io.github.skriptinsight.file.node.indentation.NodeIndentationData

/**
 * Represents a line from a [Skript file][io.github.skriptinsight.file.SkriptFile].
 *
 * @param lineNumber The number of this node.
 * @param rawContent The raw content of this node.
 * @param indentations The information about the indentation of this node
 * @author NickAcPT
 */
data class SkriptNode(val lineNumber: Int, val rawContent: String, val indentations: Array<NodeIndentationData>) {
    companion object {
        @JvmStatic
        fun fromLine(lineNumber: Int, content: String): SkriptNode {
            return SkriptNode(
                lineNumber,
                content,
                NodeIndentationData.fromIndentation(content.takeWhile { it.isWhitespace() })
            )
        }
    }

    /**
     * The un-indented content of this node.
     */
    val unindentedRawContent: String = rawContent.dropWhile { it.isWhitespace() }

    /**
     * The count of the indentation of this node.
     */
    val indentCount = indentations.sumBy { it.type.size * it.amount }

    /**
     * The length of the indentation of this node.
     */
    val indentLength = rawContent.takeWhile { it.isWhitespace() }.count()

    var contentRange: Range? = null
    var commentRange: Range? = null

    var content: String = ""
    var comment: String = ""

    init {
        val linePatternMatcher = linePattern.matcher(unindentedRawContent)
        // Check if line has a comment
        if (linePatternMatcher.matches()) {
            //Replacement is required because of the 'double-escape' on the comment char
            val originalContent = linePatternMatcher.group(1).replace("##", "#")
            content = originalContent.trimEnd()
            comment = linePatternMatcher.group(2)

            //Compute range for content and comment
            contentRange =
                pos(indentLength + linePatternMatcher.start(1))..pos(indentLength + linePatternMatcher.end(1) - (originalContent.length - content.length))
            commentRange =
                pos(indentLength + linePatternMatcher.start(2))..pos(indentLength + linePatternMatcher.end(2))
        } else {
            //No comment. Default to un-indented raw content and no comment
            content = unindentedRawContent.trimEnd()
            contentRange = pos(indentLength)..pos(indentLength + content.length)
            commentRange = contentRange!!.end..contentRange!!.end
        }
    }

    val isSectionNode: Boolean = content.endsWith(":")

    /**
     * The parent of this node.
     */
    var parent: SkriptNode? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SkriptNode) return false

        if (lineNumber != other.lineNumber) return false
        if (rawContent != other.rawContent) return false
        if (!indentations.contentEquals(other.indentations)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = lineNumber.hashCode()
        result = 31 * result + rawContent.hashCode()
        result = 31 * result + indentations.contentHashCode()
        return result
    }

    private fun pos(column: Int): Position {
        return Position(lineNumber, column)
    }
}