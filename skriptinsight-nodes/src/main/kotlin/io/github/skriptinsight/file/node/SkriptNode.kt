package io.github.skriptinsight.file.node

import io.github.skriptinsight.editing.extensions.getGroupRange
import io.github.skriptinsight.file.linePattern
import io.github.skriptinsight.editing.location.Position
import io.github.skriptinsight.editing.location.Range
import io.github.skriptinsight.file.node.indentation.NodeIndentationData
import java.util.regex.Matcher

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
    val unIndentedRawContent: String = rawContent.dropWhile { it.isWhitespace() }

    /**
     * The normalized count of the indentation of this node.
     */
    val normalizedIndentCount = indentations.sumBy { it.type.size * it.amount }

    /**
     * The count of the indentation of this node.
     */
    val indentCount = rawContent.takeWhile { it.isWhitespace() }.count()

    var contentRange: Range? = null
    var commentRange: Range? = null

    var content: String = ""
    var comment: String = ""

    init {
        val linePatternMatcher = linePattern.matcher(unIndentedRawContent)
        // Check if line has a comment
        if (linePatternMatcher.matches()) {
            //Replacement is required because of the 'double-escape' on the comment char
            val originalContent = linePatternMatcher.group(1).replace("##", "#")
            content = originalContent.trimEnd()
            comment = linePatternMatcher.group(2)

            //Compute range for content and comment
            val trimmedCharsAmount = originalContent.length - content.length
            contentRange = groupRange(linePatternMatcher, 1, indentCount, offsetEnd = indentCount - trimmedCharsAmount)
            commentRange = groupRange(linePatternMatcher, 2, indentCount)
        } else {
            //No comment. Default to un-indented raw content and no comment
            content = unIndentedRawContent.trimEnd()
            contentRange = pos(indentCount)..pos(indentCount + content.length)
            commentRange = contentRange!!.end..contentRange!!.end
        }
    }

    val isSectionNode: Boolean = content.endsWith(":")

    private var _parent: SkriptNode? = null

    /**
     * The parent of this node.
     */
    var parent: SkriptNode?
        get() = _parent
        set(value) {
            //Remove from old parent
            _parent?.children?.remove(this)
            //Add to new parent
            value?.children?.add(this)

            _parent = value
        }

    var children: MutableList<SkriptNode>? = mutableListOf()

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
    private fun groupRange(matcher: Matcher, group: Int, offsetStart: Int = 0, offsetEnd: Int = offsetStart): Range {
        return matcher.getGroupRange(group, offsetStart, offsetEnd, lineNumber)
    }
}