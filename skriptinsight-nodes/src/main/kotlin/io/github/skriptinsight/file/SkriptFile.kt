package io.github.skriptinsight.file

import io.github.skriptinsight.file.node.SkriptNode
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.runBlocking
import java.io.File
import java.net.URI
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

/**
 * Represents a **Skript file**.
 * @param url The path to the file
 * @param nodes The data for each node (line)
 * @author NickAcPT
 */
class SkriptFile(val url: URI, val nodes: ConcurrentMap<Int, SkriptNode>) {
    init {
    }

    companion object {
        suspend fun fromTextSuspend(url: URI, lines: List<String>): SkriptFile {
            return SkriptFile(
                url,
                ConcurrentHashMap<Int, SkriptNode>().apply {
                    lines.forEachIndexed { i, it -> this[i] = SkriptNode.fromLine(i, it) }
                }
            ).apply {
                computeNodeDataParents(nodes.values.asFlow())
            }
        }

        private fun fromText(url: URI, lines: List<String>): SkriptFile = runBlocking {
            return@runBlocking fromTextSuspend(url, lines)
        }

        fun fromFile(url: URI): SkriptFile {
            return fromText(url, File(url).readLines())
        }

        suspend fun fromFileSuspend(url: URI): SkriptFile {
            return fromTextSuspend(url, File(url).readLines())
        }

        fun fromFile(file: File): SkriptFile {
            return fromText(file.toURI(), file.readLines())
        }

        suspend fun fromFileSuspend(file: File): SkriptFile {
            return fromTextSuspend(file.toURI(), file.readLines())
        }

        fun fromText(url: URI, text: String): SkriptFile {
            return fromText(url, text.lines())
        }

        suspend fun fromTextSuspend(url: URI, text: String): SkriptFile {
            return fromTextSuspend(url, text.lines())
        }

        fun fromText(text: String): SkriptFile {
            return fromText(URI("file://${UUID.randomUUID()}"), text.lines())
        }

        suspend fun fromTextSuspend(text: String): SkriptFile {
            return fromTextSuspend(URI("file://${UUID.randomUUID()}"), text.lines())
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

}

