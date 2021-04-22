package io.github.skriptinsight.editing

import java.util.regex.MatchResult
import java.util.regex.Matcher

data class MatchContext(
    val matcher: Matcher,
    val matchResult: MatchResult,
    internal val indentCount: Int,
    internal val lineNumber: Int
) {

}