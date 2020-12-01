package io.github.skriptinsight.tokens.impl

import io.github.skriptinsight.tokens.TypedSkriptToken

/**
 * Represents a string on a Skript file
 *
 * @param innerValue The inner value of a string (the contents)
 * @author NickAcPT
 */
class StringToken(value: String) : TypedSkriptToken<String>(unescapeString(value)) {

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

    val escapedValue get() = escapeString(value ?: "")
}


