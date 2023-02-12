package io.klibs.json.factory

import io.klibs.json.node.JSONBooleanNode
import io.klibs.json.node.JSONNullNode
import io.klibs.json.node.JSONNumberNode
import io.klibs.json.node.JSONStringNode
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@JsExport
@OptIn(ExperimentalJsExport::class)
interface JSONFactory {
  fun newInt(value: Int): JSONNumberNode

  fun newString(value: String): JSONStringNode

  fun newBool(value: Boolean): JSONBooleanNode

  fun newNull(): JSONNullNode
}