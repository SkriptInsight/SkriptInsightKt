package io.github.skriptinsight.tokens

import io.github.skriptinsight.file.node.SkriptNode

class SkriptNodeToken(
    val node: SkriptNode,
    val tokens: MutableList<SkriptToken<*>> = mutableListOf()
)