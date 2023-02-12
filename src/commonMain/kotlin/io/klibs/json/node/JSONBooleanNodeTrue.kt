package io.klibs.json.node

internal object JSONBooleanNodeTrue : JSONBooleanNode {
  override val type = JSONNodeType.Boolean
  override val value = true
}