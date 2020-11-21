package io.github.skriptinsight.tests

import io.github.skriptinsight.file.node.indentation.NodeIndentationData
import io.github.skriptinsight.file.node.indentation.IndentationType
import org.junit.jupiter.api.Test

class IndentationTests {
    @Test
    fun `IndentationType is correct`() {
        assert(IndentationType.fromCharacter(' ') == IndentationType.SPACE)
        assert(IndentationType.fromCharacter('\t') == IndentationType.TAB)
        assert(IndentationType.fromCharacter('a') == IndentationType.UNKNOWN)
    }

    @Test
    fun `IndentationData count is correct`() {
        val indentationCount = 1
        listOf(IndentationType.TAB, IndentationType.SPACE).forEach { indent ->
            NodeIndentationData.fromIndentation(indent.char.toString().repeat(indentationCount)).forEach {
                assert(it.amount == indentationCount)
                assert(it.type == indent)
            }
        }
    }

    @Test
    fun `IndentationData can handle mixed indentation`() {
        val indents = listOf(IndentationType.TAB, IndentationType.SPACE)
        val indentString = indents.joinToString("") { it.char.toString().repeat(4) }
        val data = NodeIndentationData.fromIndentation(indentString)
        assert(data.size == indents.size) { "${data.size} == ${indents.size}" }
    }
}