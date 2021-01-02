package io.github.skriptinsight.tokens.impl

import io.github.skriptinsight.editing.MatchContext
import io.github.skriptinsight.editing.extensions.getGroupRange
import io.github.skriptinsight.editing.extensions.group
import io.github.skriptinsight.editing.location.Range
import io.github.skriptinsight.tokens.SkriptToken

class FunctionCallToken(matchResult: MatchContext) : SkriptToken() {
    var functionName = matchResult.matchResult.group(1)
    var functionNameRange = matchResult.getGroupRange(1)

    var contents = matchResult.group(2)
    var contentsRange: Range = matchResult.getGroupRange(2)

    override fun render(): String {
        return "$functionName($contents)"
    }

}