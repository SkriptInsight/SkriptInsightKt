package io.github.skriptinsight.file

import io.github.skriptinsight.editing.work.FileProcessCallable
import io.github.skriptinsight.file.node.SkriptNode
import io.github.skriptinsight.file.node.indentation.computeNodeDataParents
import io.github.skriptinsight.file.work.SkriptFileProcess
import java.io.File
import java.net.URI
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.ForkJoinPool
import kotlin.reflect.KProperty

/**
 * Represents a **Skript io.github.skriptinsight.file**.
 * @param url The path to the io.github.skriptinsight.file
 * @param nodes The data for each node (line)
 * @author NickAcPT
 */
class SkriptFile(val url: URI, val nodes: ConcurrentMap<Int, SkriptNode>) {
    init {
        computeNodeDataParents(this)
    }
    private val extraData = mutableMapOf<String, Any?>()

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(name: String): T? {
        return extraData[name] as? T?
    }
    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(property: KProperty<T>): T? {
        return get(property.name)
    }

    operator fun <T> set(property: KProperty<T>, value: T?) {
        set(property.name, value)
    }

    operator fun set(name: String, value: Any?) {
        extraData[name] = value
    }

    companion object {
        private fun fromText(url: URI, lines: List<String>): SkriptFile {
            return SkriptFile(
                url,
                ConcurrentHashMap<Int, SkriptNode>().apply {
                    lines.forEachIndexed { i, it -> this[i] = SkriptNode.fromLine(i, it) }
                }
            )
        }

        fun fromFile(url: URI): SkriptFile {
            return fromText(url, File(url).readLines())
        }

        fun fromFile(file: File): SkriptFile {
            return fromText(file.toURI(), file.readLines())
        }

        fun fromText(url: URI, text: String): SkriptFile {
            return fromText(url, text.lines())
        }

        fun fromText(text: String): SkriptFile {
            return fromText(URI("file://${UUID.randomUUID()}"), text.lines())
        }

    }

    /**
     * Computes the root nodes (aka the nodes without parents)
     */
    val rootNodes
        get() = nodes.values.filter { it.parent == null }

    operator fun get(index: Int): SkriptNode? {
        return nodes[index]
    }

    fun <R> runProcess(process: SkriptFileProcess<R>): List<R> {
        val map = nodes.map {
            FileProcessCallable(process, this, it.key, it.value.rawContent, it.value)
        }
        return ForkJoinPool.commonPool().invokeAll(map).map { it.get() }
    }

}

