package io.github.skriptinsight.editing.location

/**
 * Represents a range of zero-indexed positions in a character sequence.
 * (start, end]
 * @param start The starting position.
 * @param end The ending position.
 * @author NickAcPT
 */
data class Range(val start: Position, val end: Position) {
    fun isWithin(other: Range): Boolean {
        return start >= other.start && end <= other.end
    }
}

/**
 * Returns a substring specified by the given [range] of indices.
 */
fun String.substring(range: Range): String {
    return this.substring(range.start.resolvePosition(this) until range.end.resolvePosition(this))
}