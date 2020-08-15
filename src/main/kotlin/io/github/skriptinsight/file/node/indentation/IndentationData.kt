package io.github.skriptinsight.file.node.indentation

data class IndentationData(val type: IndentationType, val amount: Int) {
    companion object {
        fun fromIndentation(takeWhile: String): Array<IndentationData> {
            return takeWhile.groupBy { it }
                    .map {
                        IndentationData(
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