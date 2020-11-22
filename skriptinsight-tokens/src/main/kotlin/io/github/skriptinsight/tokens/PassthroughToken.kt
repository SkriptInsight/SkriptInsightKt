package io.github.skriptinsight.tokens

import io.github.skriptinsight.editing.location.Range
import io.github.skriptinsight.file.SkriptFile

open class PassthroughToken<R>(range: Range, rawContents: String, file: SkriptFile, value: R) : SkriptToken<R>(
    range, rawContents, file, value
) {
    override fun render(): String {
        return rawContents
    }
}