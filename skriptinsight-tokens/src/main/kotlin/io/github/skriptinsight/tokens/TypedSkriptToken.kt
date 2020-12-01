package io.github.skriptinsight.tokens

/**
 * Represents a typed Skript token.
 *
 * @param T The type value of this token
 * @param value The [T] value of this node.
 */
abstract class TypedSkriptToken<T>(val value: T) : SkriptToken()

