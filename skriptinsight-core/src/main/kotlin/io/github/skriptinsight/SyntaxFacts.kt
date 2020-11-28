package io.github.skriptinsight

import org.intellij.lang.annotations.Language

object SyntaxFacts {

    @Language("Regexp")
    const val linePattern = ("^((?:[^#]|##)*)(\\s*#(?!#).*)$")

    @Language("Regexp")
    const val wildcard = "[^\"]*?(?:\"[^\"]*?\"[^\"]*?)*?"

    @Language("Regexp")
    const val stringMatcher = "\"([^\"]*?(?:\"\"[^\"]*)*)\""

    @Language("Regexp")
    val whitespaceRegex = "\\s"

    @Language("Regexp")
    const val functionNameRegex = "[\\p{IsAlphabetic}][\\p{IsAlphabetic}\\p{IsDigit}_]*"

    @Language("Regexp")
    val functionRegex =
        "function ($functionNameRegex)\\((.*)\\)(?: :: (.+))?"

    @Language("Regexp")
    val paramRegex = "\\s*(.+?)\\s*:(?=[^:]*$)\\s*(.+?)(?:\\s*=\\s*(.+))?\\s*"

    @Language("Regexp")
    val commandRegex = ("(?i)^command /?(\\S+)\\s*(\\s+(.+))?$")

    @Language("Regexp")
    val argumentRegex =
        ("<\\s*(?:(.+?)\\s*:\\s*)?(.+?)\\s*(?:=\\s*($wildcard))?\\s*>")


    @Language("Regexp")
    val varRegex =
        ("((the )?var(iable)? )?\\{([^{}]|%\\{|\\}%)+\\}")

    @Language("Regexp")
    val lineRegex = "^((?:[^#]|##)*)(\\s*#(?!#).*)$"

    @Language("Regexp")
    val listSplitRegex = ("\\s*,?\\s+(and|n?or)\\s+|\\s*,\\s*")
    
    @Language("Regexp")
    val functionCallRegex =
        ("($functionNameRegex)\\((.*)\\)")

    @Language("Regexp")
    val linkRegex = ("[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)")


}