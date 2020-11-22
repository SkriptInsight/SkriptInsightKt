package io.github.skriptinsight.tokens.impl

import io.github.skriptinsight.editing.location.Range
import io.github.skriptinsight.file.SkriptFile
import io.github.skriptinsight.tokens.SkriptToken

/**
 * Represents a string on a Skript file
 *
 * @param innerValue The inner value of a string (the contents)
 * @author NickAcPT
 */
class StringToken(innerValue: String, range: Range, rawContents: String, file: SkriptFile) : SkriptToken<String>(range, rawContents, file, unescapeString(innerValue)) {
    companion object {

        private fun unescapeString(value: String): String {
            return value.replace("\"\"", "\"")
        }
        private fun escapeString(value: String): String {
            return value.replace("\"", "\"\"")
        }
    }

    override fun render(): String {
        return "\"$escapedValue\""
    }

    val escapedValue get() = escapeString(value)
}


