package io.github.skriptinsight.file

import org.intellij.lang.annotations.Language
import java.util.regex.Pattern

val linePattern: Pattern = Pattern.compile("^((?:[^#]|##)*)(\\s*#(?!#).*)$")

const val functionNamePattern = "[\\p{IsAlphabetic}][\\p{IsAlphabetic}\\p{IsDigit}_]*";
val functionNamePatternRegex = Pattern.compile(functionNamePattern)

val functionPattern = Pattern.compile("function ($functionNamePattern)\\((.*)\\)(?: :: (.+))?", Pattern.CASE_INSENSITIVE)

val paramPattern = Pattern.compile("\\s*(.+?)\\s*:(?=[^:]*$)\\s*(.+?)(?:\\s*=\\s*(.+))?\\s*")

val commandPattern = Pattern.compile("(?i)^command /?(\\S+)\\s*(\\s+(.+))?$")

@Language("regexp")
const val wildcard = "[^\"]*?(?:\"[^\"]*?\"[^\"]*?)*?"

@Language("regexp")
const val stringPattern = "\"([^\"]*?(?:\"\"[^\"]*)*)\""

val whitespacePattern = Pattern.compile("\\s+")

val argumentPattern = Pattern.compile("<\\s*(?:(.+?)\\s*:\\s*)?(.+?)\\s*(?:=\\s*($wildcard))?\\s*>")