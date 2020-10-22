package io.github.skriptinsight.tokens.work.impl

import io.github.skriptinsight.editing.extensions.getGroupRange
import io.github.skriptinsight.editing.location.Range
import io.github.skriptinsight.file.SkriptFile
import io.github.skriptinsight.tokens.SkriptNodeToken
import io.github.skriptinsight.tokens.impl.SkriptToken
import io.github.skriptinsight.tokens.impl.WhitespaceToken
import io.github.skriptinsight.tokens.impl.WordToken
import io.github.skriptinsight.tokens.work.TokenizedModelProcess
import io.github.skriptinsight.tokens.work.TokenizedModelProcessData
import org.intellij.lang.annotations.Language
import java.util.regex.MatchResult
import java.util.regex.Pattern

object NodeTokenizeProcess : TokenizedModelProcess<SkriptNodeToken>() {

    private val tokenDefinitions: MutableList<Pair<Pattern, TokenCompute>> = mutableListOf()

    private fun <R : SkriptToken<*>> token(
        pattern: Pattern,
        creator: (SkriptFile, Range, String, MatchResult) -> SkriptToken<*>
    ) {
        tokenDefinitions.add(pattern to TokenComputeForLambda(creator))
    }

    private fun <R : SkriptToken<*>> token(
        @Language("RegExp") pattern: String,
        creator: (SkriptFile, Range, String, MatchResult) -> SkriptToken<*>
    ) {
        token<R>(Pattern.compile(pattern), creator)
    }

    init {
        token<WhitespaceToken>("\\s") { skriptFile, range, rawContent, _ ->
            WhitespaceToken(range, rawContent, skriptFile)
        }
    }

    override fun doWork(
        file: SkriptFile,
        lineNumber: Int,
        rawContent: String,
        context: TokenizedModelProcessData
    ): SkriptNodeToken {
        val tokens = mutableListOf<SkriptToken<*>>()
        val outToken = SkriptNodeToken(context.node, tokens)
        val node = file[lineNumber] ?: return outToken
        val content = node.content

        var lastPos = 0
        tokenDefinitions.forEach { (pattern, tokenComputeInstance) ->

            val matcher = pattern.matcher(content)

            while (matcher.find()) {
                tokens.add(
                    tokenComputeInstance.invoke(
                        file,
                        matcher.getGroupRange(matcher.groupCount(), node.indentCount, lineNumber),
                        matcher.group(),
                        matcher.toMatchResult()
                    )
                )
            }
        }


        return outToken
    }
}

