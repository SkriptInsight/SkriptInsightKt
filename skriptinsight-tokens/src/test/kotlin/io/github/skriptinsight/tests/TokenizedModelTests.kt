package io.github.skriptinsight.tests

import io.github.skriptinsight.file.SkriptFile
import io.github.skriptinsight.tokenizedModel
import org.junit.jupiter.api.Test

class TokenizedModelTests {
    @Test
    fun `SkriptFile TokenizedModel is not null`() {
        val file = SkriptFile.fromText(
            """
            on join:
                send "hi"
        """.trimIndent()
        )
        val model = file.tokenizedModel

        val nodeToken = model[1]

        println()
    }

}