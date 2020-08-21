package io.github.skriptinsight.file.extensions

import io.github.skriptinsight.file.location.Position
import io.github.skriptinsight.file.location.Range
import java.util.regex.Matcher

fun Matcher.getGroupRange(id: Int, offsetStart: Int = 0, offsetEnd: Int = 0, lineNumber: Int): Range {
    return Position(lineNumber, offsetStart + start(id))..Position(lineNumber, offsetEnd + end(id))
}

fun Matcher.getGroupRange(id: Int, offset: Int = 0, lineNumber: Int): Range {
    return getGroupRange(id, offset, lineNumber)
}