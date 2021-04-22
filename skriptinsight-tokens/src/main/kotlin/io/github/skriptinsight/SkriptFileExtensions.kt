package io.github.skriptinsight

import io.github.skriptinsight.file.SkriptFile
import io.github.skriptinsight.file.node.SkriptNode
import io.github.skriptinsight.file.node.indentation.computeIndentationLevelsForNode
import io.github.skriptinsight.tokens.TokenizedModel

var SkriptFile.tokenizedModel: TokenizedModel
    get() {
        return this[this::tokenizedModel] ?: TokenizedModel(this).also { tokenizedModel = it }
    }
    set(value) {
        this[this::tokenizedModel] = value
    }