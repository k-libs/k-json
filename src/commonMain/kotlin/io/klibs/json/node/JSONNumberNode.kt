package io.klibs.json.node

import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@JsExport
@OptIn(ExperimentalJsExport::class)
interface JSONNumberNode : JSONNode {
  fun isIntegral(): Boolean

  fun isExponential(): Boolean

  fun <T> unpack(fn: (String) -> T): T
}
