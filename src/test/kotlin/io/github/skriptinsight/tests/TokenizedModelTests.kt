package io.github.skriptinsight.tests

import io.github.skriptinsight.file.SkriptFile
import io.github.skriptinsight.tokenizedModel
import io.github.skriptinsight.tokens.TokenizedModel
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class TokenizedModelTests {
    @Test
    fun `SkriptFile TokenizedModel is not null`() {
        val file = SkriptFile.fromText(
            """
            on join:
                send "hello world"
                abs(-1)
        """.trimIndent()
        )

        val tokenizedModel = file.tokenizedModel

        val tokenizedNode = tokenizedModel[1]
        println()
    }

}