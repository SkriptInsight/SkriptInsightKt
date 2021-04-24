package io.github.skriptinsight.patterns.result

import io.github.skriptinsight.patterns.AbstractSkriptPatternElement
import io.github.skriptinsight.patterns.context.PatternMatchContext

data class PatternMatchResult(
    val type: PatternMatchResultType,
    val context: PatternMatchContext,
    val matchedElement: AbstractSkriptPatternElement?
) {
    companion object {
        fun success(ctx: PatternMatchContext): PatternMatchResult {
            return PatternMatchResult(
                PatternMatchResultType.SUCCESS,
                ctx,
                ctx.currentElement
            )
        }

        fun optionalSuccess(ctx: PatternMatchContext): PatternMatchResult {
            return success(ctx).apply { isOptionalMatch = true }
        }

        fun failure(ctx: PatternMatchContext): PatternMatchResult {
            return PatternMatchResult(PatternMatchResultType.FAILURE, ctx, ctx.currentElement)
        }
    }

    var parseMark: Int = 0

    var isOptionalMatch: Boolean = false

    val isSuccess: Boolean
        get() = type == PatternMatchResultType.SUCCESS
}
