package io.github.skriptinsight.tokens.impl

import io.github.skriptinsight.editing.location.Range
import io.github.skriptinsight.file.SkriptFile

/**
 * Represents a token on a Skript file.
 *
 * @param T The type value of this token
 */
abstract class SkriptToken<T>(val range: Range, val rawContents: String, val file: SkriptFile, val value: T) {
    abstract fun render(): String
}

