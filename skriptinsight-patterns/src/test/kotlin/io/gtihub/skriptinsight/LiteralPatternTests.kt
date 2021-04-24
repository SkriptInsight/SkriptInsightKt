package io.gtihub.skriptinsight

import io.github.skriptinsight.patterns.context.PatternMatchContext
import io.github.skriptinsight.patterns.impl.LiteralPatternElement
import org.junit.jupiter.api.Test

class LiteralPatternTests {

    private val toMatch = LiteralPatternElement("abc")

    @Test
    fun `Literal Pattern successfully matches`() {
        val successMatch = toMatch.parse("abc")
        assert(successMatch.isSuccess)
        assert(!successMatch.isOptionalMatch)
    }

    @Test
    fun `Literal Pattern failure match`() {
        val failMatch = toMatch.parse("cba")
        assert(!failMatch.isSuccess)
        assert(!failMatch.isOptionalMatch)
    }

    @Test
    fun `Literal Pattern partial context match`() {
        val partialCtx = PatternMatchContext("abcdef")
        val partialMatch = toMatch.parse(partialCtx)

        assert(partialMatch.isSuccess)
        assert(!partialMatch.isOptionalMatch)
        assert(partialCtx.currentPosition < partialCtx.maxLength)

        assert(partialCtx.peekNext(3) == "def")
    }

}