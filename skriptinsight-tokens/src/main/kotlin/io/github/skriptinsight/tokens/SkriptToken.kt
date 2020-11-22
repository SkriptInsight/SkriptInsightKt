package io.github.skriptinsight.tokens

import io.github.skriptinsight.editing.location.Range
import io.github.skriptinsight.file.SkriptFile

/**
 * Represents a token on a Skript file.
 *
 * @param T The type value of this token
 * @param file The file associated with this token
 * @param range The range of this token on the original file
 * @param rawContents The raw contents of this token
 * @param value The [T] value of this node.
 * @author NickAcPT
 */
abstract class SkriptToken<T>(val range: Range, val rawContents: String, val file: SkriptFile, val value: T) {
    abstract fun render(): String
}

