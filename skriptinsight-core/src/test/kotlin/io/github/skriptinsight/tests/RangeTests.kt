package io.github.skriptinsight.tests

import io.github.skriptinsight.editing.location.Position
import io.github.skriptinsight.editing.location.Range
import org.junit.jupiter.api.Test

class RangeTests {
    val start = Position(0, 1)
    val end = Position(0, 6)
    var range = Range(start, end)

    val inside = Position(0, 3)
    val outside = Position(0, 10)

    @Test
    fun `Position is inside Range`() {
        assert(range.contains(inside)) { "range.contains(inside)" }
    }

    @Test
    fun `Position is outside Range`() {
        assert(!range.contains(outside)) { "!range.contains(outside)" }
    }

    @Test
    fun `Range bounds are within Range`() {
        assert(range.contains(start)) { "range.contains(start)" }
        assert(range.contains(end)) { "range.contains(end)" }
    }

    @Test
    fun `Extreme position is outside Range`() {
        var range = Range(Position(1, 15), Position(1, 25))
        assert(!range.contains(Range(Position(1, 4), Position(1, 5)))) { "!range.contains(Range(Position(1, 4), Position(1, 5)))"}
    }
}
