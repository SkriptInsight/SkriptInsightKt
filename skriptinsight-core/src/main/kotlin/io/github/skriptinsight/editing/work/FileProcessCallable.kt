package io.github.skriptinsight.editing.work

import java.util.concurrent.Callable

class FileProcessCallable<F, R, C>(
    private val process: FileProcess<F, R, C>,
    private val file: F,
    private val lineNumber: Int,
    private val rawContent: String,
    private val context: C
) : Callable<R> {
    override fun call(): R? {
        return try {
            process.doWork(file, lineNumber, rawContent, context)
        } catch (e: Throwable) {
            e.printStackTrace()
            null
        }
    }
}
