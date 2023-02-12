package io.klibs.json.node

import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@JsExport
@OptIn(ExperimentalJsExport::class)
enum class JSONNodeType {
  Array,
  Boolean,
  Null,
  Number,
  Object,
  String,
}