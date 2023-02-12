package io.klibs.json.node

import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@JsExport
@OptIn(ExperimentalJsExport::class)
interface JSONArrayNodeIterator {
  operator fun hasNext(): Boolean

  operator fun next(): JSONNode
}