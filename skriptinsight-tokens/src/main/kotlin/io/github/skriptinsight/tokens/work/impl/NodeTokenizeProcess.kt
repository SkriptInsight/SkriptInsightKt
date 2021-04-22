package io.github.skriptinsight.tokens.work.impl

import io.github.skriptinsight.file.SkriptFile
import io.github.skriptinsight.tokens.SkriptNodeToken
import io.github.skriptinsight.tokens.SkriptToken
import io.github.skriptinsight.tokens.work.TokenizedModelProcess
import io.github.skriptinsight.tokens.work.TokenizedModelProcessData

object NodeTokenizeProcess : TokenizedModelProcess<SkriptNodeToken>() {

    override fun doWork(
        file: SkriptFile,
        lineNumber: Int,
        rawContent: String,
        context: TokenizedModelProcessData
    ): SkriptNodeToken {
        val tokens = mutableListOf<SkriptToken>()
        val outToken = SkriptNodeToken(context.node, tokens)
        val node = file[lineNumber] ?: return outToken
        val content = node.rawContent


        return outToken
    }
}

