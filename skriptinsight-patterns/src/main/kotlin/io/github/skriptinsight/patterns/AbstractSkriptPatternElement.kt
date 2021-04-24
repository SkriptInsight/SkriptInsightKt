package io.github.skriptinsight.patterns

import io.github.skriptinsight.patterns.context.PatternMatchContext
import io.github.skriptinsight.patterns.result.PatternMatchResult

abstract class AbstractSkriptPatternElement {
    var elementIndex = -1
    var parent: AbstractSkriptPatternElement? = null

    abstract fun parse(ctx: PatternMatchContext): PatternMatchResult

    fun parse(ctx: String): PatternMatchResult {
        return parse(PatternMatchContext(ctx))
    }

    abstract fun renderPattern(): String

    override fun toString(): String {
        return renderPattern()
    }
}