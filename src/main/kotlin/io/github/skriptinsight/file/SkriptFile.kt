package io.github.skriptinsight.file

import io.github.skriptinsight.file.node.SkriptNodeData
import java.io.File
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

/**
 * Represents a **Skript file**.
 * @param url The path to the file
 * @param nodeData The data for each node (line)
 * @author NickAcPT
 */
class SkriptFile(val url: File, val nodeData: ConcurrentMap<Int, SkriptNodeData>) {

    companion object {
        private fun fromText(url: File, lines: List<String>): SkriptFile {
            return SkriptFile(
                    url,
                    ConcurrentHashMap<Int, SkriptNodeData>().apply {
                        lines.forEachIndexed { i, it -> this[i] = SkriptNodeData.fromLine(i, it) }
                    }
            )
        }

        fun fromFile(url: File): SkriptFile {
            return fromText(url, url.readLines())
        }

        fun fromText(url: File, text: String): SkriptFile {
            return fromText(url, text.lines())
        }
    }


    operator fun get(index: Int): SkriptNodeData? {
        return nodeData[index]
    }

}