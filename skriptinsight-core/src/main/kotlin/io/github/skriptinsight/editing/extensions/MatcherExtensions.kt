package io.github.skriptinsight.editing.extensions

import io.github.skriptinsight.editing.MatchContext
import io.github.skriptinsight.editing.location.Position
import io.github.skriptinsight.editing.location.Range
import java.util.regex.Matcher

@JvmOverloads
fun Matcher.getGroupRange(id: Int, offsetStart: Int = 0, offsetEnd: Int = 0, lineNumber: Int): Range {
    return Position(lineNumber, offsetStart + start(id))..Position(lineNumber, offsetEnd + end(id))
}

@JvmName("getSimpleGroupRange")
@JvmOverloads
fun Matcher.getGroupRange(id: Int, offset: Int = 0, lineNumber: Int): Range {
    return getGroupRange(id, offsetStart = offset, offsetEnd = offset, lineNumber = lineNumber)
}

@JvmName("getGroupRange")
fun MatchContext.getGroupRange(id: Int): Range {
    return this.matcher.getGroupRange(id, offsetStart = indentCount, offsetEnd = indentCount, lineNumber = lineNumber)
}

/**
 * Returns the input subsequence matched by the previous match.
 *
 *
 *  For a matcher *m* with input sequence *s*,
 * the expressions *m.*`group()` and
 * *s.*`substring(`*m.*`start(),`&nbsp;*m.*`end())`
 * are equivalent.
 *
 *
 *  Note that some patterns, for example `a*`, match the empty
 * string.  This method will return the empty string when the pattern
 * successfully matches the empty string in the input.
 *
 * @return The (possibly empty) subsequence matched by the previous match,
 * in string form
 *
 * @throws  IllegalStateException
 * If no match has yet been attempted,
 * or if the previous match operation failed
 */
fun MatchContext.group(): String = matcher.group()

/**
 * Returns the input subsequence captured by the given group during the
 * previous match operation.
 *
 *
 *  For a matcher *m*, input sequence *s*, and group index
 * *g*, the expressions *m.*`group(`*g*`)` and
 * *s.*`substring(`*m.*`start(`*g*`),`&nbsp;*m.*`end(`*g*`))`
 * are equivalent.
 *
 *
 *  [Capturing groups](Pattern.html#cg) are indexed from left
 * to right, starting at one.  Group zero denotes the entire pattern, so
 * the expression `m.group(0)` is equivalent to `m.group()`.
 *
 *
 *
 *  If the match was successful but the group specified failed to match
 * any part of the input sequence, then `null` is returned. Note
 * that some groups, for example `(a*)`, match the empty string.
 * This method will return the empty string when such a group successfully
 * matches the empty string in the input.
 *
 * @param  group
 * The index of a capturing group in this matcher's pattern
 *
 * @return  The (possibly empty) subsequence captured by the group
 * during the previous match, or `null` if the group
 * failed to match part of the input
 *
 * @throws  IllegalStateException
 * If no match has yet been attempted,
 * or if the previous match operation failed
 *
 * @throws  IndexOutOfBoundsException
 * If there is no capturing group in the pattern
 * with the given index
 */
fun MatchContext.group(group: Int): String = matcher.group(group)