package io.github.skriptinsight.tests

import io.github.skriptinsight.editing.location.Position
import org.junit.jupiter.api.Test

class PositionTests {
    val lower = Position(0, 1)
    val higher = Position(0, 2)

    @Test
    fun `Position lower than other is working`() {
        assert(lower < higher) { "lower < higher" }
    }

    @Test
    fun `Position higher than other is working`() {
        assert(higher > lower) { "higher > lower" }
    }

    @Test
    fun `Position equal to other`() {
        assert(higher == higher) { "higher == higher" }
        assert(higher.compareTo(higher) == 0) { "higher.compareTo(higher) == 0" }
    }
}