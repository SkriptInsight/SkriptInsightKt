package io.github.skriptinsight.file.node.indentation

enum class IndentationType(val char: Char = Char.MIN_VALUE) {
    SPACE(' '),
    TAB('\t'),
    UNKNOWN();

    companion object {
        fun fromCharacter(char: Char): IndentationType {
            return values().firstOrNull { it.char == char } ?: UNKNOWN
        }
    }
}