package io.github.skriptinsight.file.node.indentation

data class NodeIndentationData(val type: IndentationType, val amount: Int) {
    companion object {
        @JvmStatic
        fun fromIndentation(takeWhile: String): Array<NodeIndentationData> {
            return takeWhile.groupBy { it }
                    .map {
                        NodeIndentationData(
                                IndentationType.fromCharacter(
                                        it.key),
                                it.value.size
                        )
                    }
                    .toTypedArray()
        }
    }

    val asString: String
        get() = type.char.toString().repeat(amount)
}