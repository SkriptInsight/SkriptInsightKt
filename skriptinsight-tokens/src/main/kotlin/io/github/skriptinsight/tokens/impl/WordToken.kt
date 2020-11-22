package io.github.skriptinsight.tokens.impl

import io.github.skriptinsight.editing.location.Range
import io.github.skriptinsight.file.SkriptFile
import io.github.skriptinsight.tokens.PassthroughToken

class WordToken(range: Range, rawContents: String, file: SkriptFile) :
    PassthroughToken<Unit>(range, rawContents, file, Unit)