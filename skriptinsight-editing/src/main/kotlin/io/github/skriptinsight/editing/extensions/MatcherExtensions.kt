package io.github.skriptinsight.editing.extensions

import io.github.skriptinsight.editing.location.Position
import io.github.skriptinsight.editing.location.Range
import java.util.regex.Matcher

fun Matcher.getGroupRange(id: Int, offsetStart: Int = 0, offsetEnd: Int = 0, lineNumber: Int): Range {
    return Position(lineNumber, offsetStart + start(id))..Position(lineNumber, offsetEnd + end(id))
}

fun Matcher.getGroupRange(id: Int, offset: Int = 0, lineNumber: Int): Range {
    return getGroupRange(id, offset, lineNumber)
}