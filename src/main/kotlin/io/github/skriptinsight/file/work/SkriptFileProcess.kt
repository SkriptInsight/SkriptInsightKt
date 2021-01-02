package io.github.skriptinsight.file.work

import io.github.skriptinsight.editing.work.FileProcess
import io.github.skriptinsight.file.SkriptFile
import io.github.skriptinsight.file.node.SkriptNode

abstract class UnitSkriptFileProcess : SkriptFileProcess<Unit>()

abstract class SkriptFileProcess<R> : FileProcess<SkriptFile, R, SkriptNode>() {

}