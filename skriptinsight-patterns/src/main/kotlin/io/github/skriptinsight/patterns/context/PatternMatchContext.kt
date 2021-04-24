package io.github.skriptinsight.patterns.context

import io.github.skriptinsight.patterns.AbstractSkriptPatternElement

data class PatternMatchContext(var text: String) {
    var currentLine: Int = 0
    var currentPosition: Int = 0

    var currentElement: AbstractSkriptPatternElement? = null

    val maxLength
        get() = text.length

    fun matchNextLiteral(literal: String): Boolean {
        val next = peekNext(literal.length)
        if (next.length != literal.length)
            return false
        return (next.contentEquals(literal, true)).also {
            if (it) readNext(literal.length)
        }
    }

    fun peekNext(count: Int): String {
        return if (count >= 0 && currentPosition + count <= maxLength) {
            text.substring(currentPosition, currentPosition + count)
        } else {
            ""
        }
    }

    fun peekNextChar(count: Int): Char {
        return if (count >= 0 && currentPosition + count <= maxLength) {
            text[currentPosition]
        } else {
            0.toChar()
        }
    }

    fun peekPrevious(count: Int): String {
        return if (count < 0 || currentPosition > maxLength) {
            ""
        } else {
            val pos = (currentPosition - count).coerceIn(0, maxLength)
            text.substring(pos, (pos + 1).coerceAtMost(maxLength))
        }
    }

    fun readNext(count: Int): String {
        return peekNext(count).also { currentPosition += count }
    }

    fun readUntilPosition(pos: Int): String {
        return readNext(pos - currentPosition)
    }


}