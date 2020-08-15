package io.github.skriptinsight.tests

import io.github.skriptinsight.file.node.indentation.IndentationData
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
            IndentationData.fromIndentation(indent.char.toString().repeat(indentationCount)).forEach {
                assert(it.amount == indentationCount)
                assert(it.type == indent)
            }
        }
    }

    @Test
    fun `IndentationData knows about mixed indentations`() {
        val indents = listOf(IndentationType.TAB, IndentationType.SPACE)
        repeat(5) { count ->
            val indentString = indents.joinToString("") { it.char.toString().repeat(count) }
            val data = IndentationData.fromIndentation(indentString)
            if (count != 0) {
                assert(data.size == indents.size) { "${data.size} == ${indents.size}" }
            }
        }
    }
}