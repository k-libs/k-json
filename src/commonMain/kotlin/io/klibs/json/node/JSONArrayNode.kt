package io.klibs.json.node

import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@JsExport
@OptIn(ExperimentalJsExport::class)
interface JSONArrayNode : JSONNode {
  val size: Int

  operator fun get(index: Int): JSONNode

  operator fun set(index: Int, value: JSONNode)

  operator fun iterator(): JSONArrayNodeIterator

  fun forEach(fn: (node: JSONNode) -> Unit)

  fun forEachIndexed(fn: (index: Int, node: JSONNode) -> Unit)
}

