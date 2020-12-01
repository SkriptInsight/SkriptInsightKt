package io.github.skriptinsight.tokens

import io.github.skriptinsight.file.SkriptFile
import io.github.skriptinsight.tokens.work.TokenizedModelProcess
import io.github.skriptinsight.tokens.work.impl.NodeTokenizeProcess
import java.util.concurrent.ConcurrentHashMap

class TokenizedModel(val file: SkriptFile) {
    val tokens: MutableMap<Int, SkriptNodeToken> = ConcurrentHashMap()

    init {
        tokens.putAll(
            runProcess(NodeTokenizeProcess)
                .map { it.node.lineNumber to SkriptNodeToken(it.node, it.tokens.sortedBy { t -> t.range.start.column }.toMutableList()) }
        )
    }

    fun <R> runProcess(process: TokenizedModelProcess<R>): List<R> {
        return file.runProcess(process)
    }

    operator fun get(lineNumber: Int): SkriptNodeToken? {
        return tokens[lineNumber]
    }
}