package io.github.skriptinsight.file.node.indentation

import io.github.skriptinsight.file.SkriptFile
import io.github.skriptinsight.file.node.SkriptNode
import io.github.skriptinsight.file.work.UnitSkriptFileProcess

class ComputeNodeDataParentAndChildrenProcess(private val currentLevel: Int) : UnitSkriptFileProcess() {
    override fun doWork(file: SkriptFile, lineNumber: Int, rawContent: String, context: SkriptNode) {
        if (context.isSectionNode && context.isOnSameIndentLevel(currentLevel)) {
            //Compute all child nodes
            val childrenNodes = file.nodes.values
                .drop(lineNumber + 1)
                .takeWhile { it.isChildrenAccordingToIndent(currentLevel) }

            childrenNodes.forEach { child ->
                //Set node as parent of the child
                child.parent = context
            }
        }
    }
}