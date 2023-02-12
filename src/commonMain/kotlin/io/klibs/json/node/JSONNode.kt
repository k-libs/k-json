package io.klibs.json.node

import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@JsExport
@OptIn(ExperimentalJsExport::class)
sealed interface JSONNode {
  val type: JSONNodeType
}

