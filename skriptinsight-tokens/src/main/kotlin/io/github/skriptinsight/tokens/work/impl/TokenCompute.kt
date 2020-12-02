package io.github.skriptinsight.tokens.work.impl

import io.github.skriptinsight.editing.location.Range
import io.github.skriptinsight.file.SkriptFile
import io.github.skriptinsight.editing.MatchContext
import io.github.skriptinsight.tokens.SkriptToken

fun interface TokenCompute {
    operator fun invoke(
        file: SkriptFile,
        range: Range,
        rawContent: String,
        matchContext: MatchContext
    ): SkriptToken
}