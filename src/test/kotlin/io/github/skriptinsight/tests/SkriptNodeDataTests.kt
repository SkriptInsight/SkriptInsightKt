package io.github.skriptinsight.tests

import io.github.skriptinsight.file.node.SkriptNodeData
import io.github.skriptinsight.file.node.indentation.IndentationType
import org.junit.jupiter.api.Test

class SkriptNodeDataTests {

    @Test
    fun `SkriptNodeData retains line number`() {
        val data = createTestNodeData()
        assert(data.lineNumber == lineNumber)
    }
    @Test
    fun `SkriptNodeData un-escaped content is corrent`() {
        val data = createTestNodeData()
        assert(data.content == content)
    }

    private val indentType = IndentationType.SPACE
    private val lineNumber = 1
    private val indentationCount = 1
    private val indent = indentType.char.toString().repeat(indentationCount + 1)
    private val content = "skriptinsight rocks:"
    private val rawContent = indent + content
    private fun createTestNodeData(): SkriptNodeData {
        return SkriptNodeData.fromLine(lineNumber, rawContent)
    }

}