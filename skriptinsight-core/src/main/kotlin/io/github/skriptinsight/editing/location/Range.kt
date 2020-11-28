package io.github.skriptinsight.editing.location

/**
 * Represents a range of zero-indexed positions in a character sequence.
 * (start, end]
 * @param start The starting position.
 * @param end The ending position.
 * @author NickAcPT
 */
data class Range(val start: Position, val end: Position) {

    /**
     * Checks whether [other] [Range] is within this [Range].
     * @return true if [other] [Range] is inside this [Range], false otherwise
     */
    fun contains(other: Range): Boolean {
        return other.start >= start && other.end <= end
    }

    /**
     * Checks whether [other] [Position] is within this [Range].
     * @return true if [other] [Position] is inside this [Range], false otherwise
     */
    fun contains(other: Position): Boolean {
        return other >= start  && other <= end
    }
}

/**
 * Returns a substring specified by the given [range] of indices.
 */
fun String.substring(range: Range): String {
    return this.substring(range.start.resolvePosition(this) until range.end.resolvePosition(this))
}