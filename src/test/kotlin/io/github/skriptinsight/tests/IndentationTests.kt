package io.github.skriptinsight.tests

import io.github.skriptinsight.file.node.indentation.IndentationData
import io.github.skriptinsight.file.node.indentation.IndentationType
import org.junit.jupiter.api.Test

class IndentationTests {
    @Test
    fun `IndentationType knows indentations`() {
        assert(IndentationType.fromCharacter(' ') == IndentationType.SPACE)
        assert(IndentationType.fromCharacter('\t') == IndentationType.TAB)
        assert(IndentationType.fromCharacter('a') == IndentationType.UNKNOWN)
    }

    @Test
    fun `IndentationData knows how to count`() {
        listOf(IndentationType.TAB, IndentationType.SPACE).forEach { indent ->
            repeat(5) { count ->
                IndentationData.fromIndentation(indent.char.toString().repeat(count)).forEach {
                    assert(it.amount == count)
                    assert(it.type == indent)
                }
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