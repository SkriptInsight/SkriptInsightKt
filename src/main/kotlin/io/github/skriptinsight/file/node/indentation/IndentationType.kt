package io.github.skriptinsight.file.node.indentation

enum class IndentationType(val char: Char = Char.MIN_VALUE, val size: Int = 0) {
    SPACE(' ', 1),
    TAB('\t', 4),
    UNKNOWN;

    companion object {
        @JvmStatic
        fun fromCharacter(char: Char): IndentationType {
            return values().firstOrNull { it.char == char } ?: UNKNOWN
        }
    }
}