package io.github.skriptinsight.tokens

import io.github.skriptinsight.editing.location.Range
import io.github.skriptinsight.file.SkriptFile

/**
 * Represents a token on a Skript file.
 *
 * @property file The file associated with this token
 * @property range The range of this token on the original file
 * @property rawContents The raw contents of this token
 * @author NickAcPT
 */
abstract class SkriptToken {

    lateinit var range: Range
    lateinit var rawContents: String
    lateinit var file: SkriptFile

    abstract fun render(): String

    internal fun setupToken(range: Range, rawContents: String, file: SkriptFile) {
        this.range = range
        this.rawContents = rawContents
        this.file = file
    }
}