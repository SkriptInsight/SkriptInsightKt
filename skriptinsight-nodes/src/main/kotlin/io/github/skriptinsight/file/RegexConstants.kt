package io.github.skriptinsight.file

import java.util.regex.Pattern

val linePattern: Pattern = Pattern.compile("^((?:[^#]|##)*)(\\s*#(?!#).*)$")
