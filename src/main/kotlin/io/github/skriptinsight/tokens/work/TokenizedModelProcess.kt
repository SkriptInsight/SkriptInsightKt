package io.github.skriptinsight.tokens.work

import io.github.skriptinsight.file.SkriptFile
import io.github.skriptinsight.file.node.SkriptNode
import io.github.skriptinsight.file.work.SkriptFileProcess

abstract class TokenizedModelProcess<R> : SkriptFileProcess<R>() {
    override fun doWork(file: SkriptFile, lineNumber: Int, rawContent: String, context: SkriptNode): R {
        return doWork(file, lineNumber, rawContent, TokenizedModelProcessData(context))
    }

    abstract fun doWork(file: SkriptFile, lineNumber: Int, rawContent: String, context: TokenizedModelProcessData): R
}