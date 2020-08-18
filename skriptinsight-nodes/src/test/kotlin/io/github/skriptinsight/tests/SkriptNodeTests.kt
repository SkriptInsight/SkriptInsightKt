package io.github.skriptinsight.tests

import io.github.skriptinsight.file.SkriptFile
import io.github.skriptinsight.file.location.substring
import io.github.skriptinsight.file.node.SkriptNode
import io.github.skriptinsight.file.node.indentation.IndentationType
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.net.URI

class SkriptNodeTests {

    @Test
    fun `SkriptNode retains line number`() {
        val data = createTestNodeData()
        assert(data.lineNumber == lineNumber)
    }

    @Test
    fun `SkriptNode un-escaped content is correct`() {
        val data = createTestNodeData()
        assert(data.unindentedRawContent == content)
    }

    @Test
    fun `SkriptNode has correct parent`() {
        val file = SkriptFile.fromText(
            fileUrl, """
            on chat:
                send "hi"
        """.trimIndent()
        )
    }

    @Test
    fun `SkriptNode is section node`() {
        val file = SkriptNode.fromLine(0, "    section node:     # bruh moment")
        assert(file.isSectionNode)
    }

    @ParameterizedTest
    @ValueSource(strings = ["    section node:     # bruh moment", "    section node:    "])
    fun `SkriptNode content section is correct`(content: String) {
        val node = SkriptNode.fromLine(0, content)
        assert(node.rawContent.substring(node.contentRange!!) == "section node:") { node.rawContent.substring(node.contentRange!!) + " == " + "section node:" }
        val expectedComment = if (content.contains("#")) "# bruh moment" else ""
        assert(node.rawContent.substring(node.commentRange!!) == expectedComment) {
            node.rawContent.substring(
                node.commentRange!!
            ) + expectedComment
        }
    }

    val fileUrl = URI("memory://file.sk")
    private val indentType = IndentationType.SPACE
    private val lineNumber = 1
    private val indentationCount = 1
    private val indent = indentType.char.toString().repeat(indentationCount + 1)
    private val content = "skriptinsight rocks:"
    private val rawContent = indent + content
    private fun createTestNodeData(): SkriptNode {
        return SkriptNode.fromLine(lineNumber, rawContent)
    }

}