package io.github.skriptinsight.tokens

open class PassthroughToken<R> : SkriptToken() {
    override fun render(): String {
        return rawContents
    }
}