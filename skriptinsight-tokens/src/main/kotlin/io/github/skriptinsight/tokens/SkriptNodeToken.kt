package io.github.skriptinsight.tokens

import io.github.skriptinsight.file.node.SkriptNode
import io.github.skriptinsight.tokens.impl.SkriptToken

class SkriptNodeToken(
    val node: SkriptNode,
    val tokens: MutableList<SkriptToken<*>> = mutableListOf()
)