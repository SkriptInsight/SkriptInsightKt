package io.github.skriptinsight.file

import io.github.skriptinsight.file.node.SkriptNode
import java.io.File
import java.net.URI
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
        computeNodeDataParents(nodes.values)
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

        fun fromText(url: URI, text: String): SkriptFile {
            return fromText(url, text.lines())
        }

    }


    operator fun get(index: Int): SkriptNode? {
        return nodes[index]
    }

}

