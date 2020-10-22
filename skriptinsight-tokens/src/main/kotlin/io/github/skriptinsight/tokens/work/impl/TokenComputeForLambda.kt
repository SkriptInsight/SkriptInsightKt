package io.github.skriptinsight.tokens.work.impl

import io.github.skriptinsight.editing.location.Range
import io.github.skriptinsight.file.SkriptFile
import io.github.skriptinsight.tokens.impl.SkriptToken
import java.util.regex.MatchResult

class TokenComputeForLambda(private val creator: (SkriptFile, Range, String, MatchResult) -> SkriptToken<*>) :
    TokenCompute {
    override fun invoke(
        file: SkriptFile,
        range: Range,
        rawContent: String,
        matchResult: MatchResult
    ): SkriptToken<*> {
        return creator.invoke(file, range, rawContent, matchResult)
    }
}