package io.github.skriptinsight.tokens.work.impl

import io.github.skriptinsight.SyntaxFacts.functionCallRegex
import io.github.skriptinsight.SyntaxFacts.stringMatcher
import io.github.skriptinsight.SyntaxFacts.whitespaceRegex
import io.github.skriptinsight.editing.extensions.getGroupRange
import io.github.skriptinsight.editing.location.Range
import io.github.skriptinsight.file.SkriptFile
import io.github.skriptinsight.tokens.SkriptNodeToken
import io.github.skriptinsight.tokens.SkriptToken
import io.github.skriptinsight.tokens.impl.FunctionCallToken
import io.github.skriptinsight.tokens.impl.StringToken
import io.github.skriptinsight.tokens.impl.WhitespaceToken
import io.github.skriptinsight.tokens.work.TokenizedModelProcess
import io.github.skriptinsight.tokens.work.TokenizedModelProcessData
import org.intellij.lang.annotations.Language
import java.util.regex.MatchResult
import java.util.regex.Pattern

object NodeTokenizeProcess : TokenizedModelProcess<SkriptNodeToken>() {

    private val tokenDefinitions: MutableList<Pair<Pattern, TokenCompute>> = mutableListOf()

    private fun <R : SkriptToken> token(
        pattern: Pattern,
        creator: TokenCompute
    ) {
        tokenDefinitions.add(pattern to creator)
    }

    private fun <R : SkriptToken> token(
        @Language("RegExp") pattern: String,
        creator: (SkriptFile, Range, String, MatchResult) -> SkriptToken
    ) {
        token<R>(Pattern.compile(pattern), creator)
    }

    init {
        token<StringToken>(functionCallRegex) { skriptFile, range, rawContent, matchResult ->
            FunctionCallToken(matchResult)
        }
        token<StringToken>(stringMatcher) { skriptFile, range, rawContent, matchResult ->
            StringToken(matchResult.group(1))
        }
        token<WhitespaceToken>(whitespaceRegex) { skriptFile, range, rawContent, _ ->
            WhitespaceToken()
        }
    }

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

        tokenDefinitions.forEach { (pattern, tokenComputeInstance) ->

            val matcher = pattern.matcher(content)

            while (matcher.find()) {
                val matchRange = matcher.getGroupRange(matcher.groupCount(), node.indentCount, lineNumber)
                //If this node isn't within a previously matched token, add it
                if (tokens.none { t -> t.range.contains(matchRange) }) {
                    val matchResult = matcher.toMatchResult()
                    val rawMatchContent = matcher.group()
                    tokens.add(
                        tokenComputeInstance.invoke(
                            file,
                            matchRange,
                            rawMatchContent,
                            matchResult
                        ).also { it.setupToken(matchRange, rawMatchContent, file) }
                    )
                }
            }
        }

        return outToken
    }
}

