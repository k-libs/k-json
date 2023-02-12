package io.klibs.json.node

import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@JsExport
@OptIn(ExperimentalJsExport::class)
sealed interface JSONNullNode : JSONNode

internal object JSONNullNodeImpl : JSONNode {
  override val type = JSONNodeType.Null
}