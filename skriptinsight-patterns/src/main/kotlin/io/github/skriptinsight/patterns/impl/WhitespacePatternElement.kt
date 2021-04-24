package io.github.skriptinsight.patterns.impl

import io.github.skriptinsight.patterns.AbstractSkriptPatternElement
import io.github.skriptinsight.patterns.context.PatternMatchContext
import io.github.skriptinsight.patterns.result.PatternMatchResult

class WhitespacePatternElement : AbstractSkriptPatternElement() {
    override fun parse(ctx: PatternMatchContext): PatternMatchResult {
        var count = 0
        while (ctx.peekNextChar(1).isWhitespace()) {
            count++
            ctx.currentPosition++
        }
        return if (count > 0) {
            PatternMatchResult.success(ctx)
        } else {
            PatternMatchResult.failure(ctx)
        }
    }

    override fun renderPattern(): String {
        return " "
    }
}