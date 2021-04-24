package io.github.skriptinsight.patterns.impl

import io.github.skriptinsight.patterns.AbstractSkriptPatternElement
import io.github.skriptinsight.patterns.context.PatternMatchContext
import io.github.skriptinsight.patterns.result.PatternMatchResult

class LiteralPatternElement(private val value: String) : AbstractSkriptPatternElement() {
    companion object {
        private val literalReplacementRegex = Regex("([()\\[\\]])")
    }

    override fun parse(ctx: PatternMatchContext): PatternMatchResult {
        return if (ctx.matchNextLiteral(value)) {
            PatternMatchResult.success(ctx)
        } else {
            PatternMatchResult.failure(ctx)
        }
    }

    override fun renderPattern(): String {
        return literalReplacementRegex.replace(value, "\\$1")
    }
}