package io.github.skriptinsight.tests

import io.github.skriptinsight.file.SkriptFile
import io.github.skriptinsight.file.computeNodeDataParents
import io.github.skriptinsight.file.location.substring
import io.github.skriptinsight.file.node.SkriptNode
import io.github.skriptinsight.file.node.indentation.IndentationType
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
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
        assert(data.unIndentedRawContent == content)
    }

    @Test
    fun `SkriptNode has correct parent`() {
        val file = SkriptFile.fromText(
            fileUrl, """
            on chat:
                send "hi"
                if true:
                    send "hi 2"
        """.trimIndent()
        )

        runBlocking { computeNodeDataParents(file.nodes.values.asFlow()) }

        assert(file.rootNodes.size == 1)
        assert(file[0]?.parent == null)
        assert(file[1]?.parent != null)
        assert(file[2]?.parent != null)
        assert(file[3]?.parent != null)

        assert(file[0]?.children?.isNotEmpty() ?: false)
        assert(file[1]?.children?.isNullOrEmpty() ?: false)
        assert(file[2]?.children?.isNotEmpty() ?: false)
        assert(file[3]?.children?.isNullOrEmpty() ?: false)

        assert(file[0]?.children?.get(0) == file[1])

        assert(file[3]?.parent != file[0])
        assert(file[3]?.parent == file[2])
    }

    @Test
    fun `SkriptNode is section node`() {
        val file = SkriptNode.fromLine(0, "    section node:     # bruh moment")
        assert(file.isSectionNode)
    }


    @Test
    fun `SkriptFile can be created from text`() {
        assertDoesNotThrow {
            SkriptFile.fromText("section node:     # bruh moment")
        }
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