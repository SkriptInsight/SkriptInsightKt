package io.github.skriptinsight.tokens.work.impl

import io.github.skriptinsight.editing.location.Range
import io.github.skriptinsight.file.SkriptFile
import io.github.skriptinsight.tokens.SkriptToken
import io.github.skriptinsight.tokens.TypedSkriptToken
import java.util.regex.MatchResult

fun interface TokenCompute {
    operator fun invoke(file: SkriptFile, range: Range, rawContent: String, matchResult: MatchResult): SkriptToken
}