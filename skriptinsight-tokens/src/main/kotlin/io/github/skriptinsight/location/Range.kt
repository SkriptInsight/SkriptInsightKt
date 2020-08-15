package io.github.skriptinsight.location

/**
 * Represents a range of zero-indexed positions in a character sequence.
 * @param start The starting position.
 * @param end The ending position.
 * @author NickAcPT
 */
data class Range(val start: Position, val end: Position) {
    fun String.substring(range: Range): String {
        return this.substring(start.resolvePosition(this)..end.resolvePosition(this))
    }
}