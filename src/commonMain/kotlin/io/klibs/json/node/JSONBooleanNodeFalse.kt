package io.klibs.json.node

internal object JSONBooleanNodeFalse : JSONBooleanNode {
  override val type = JSONNodeType.Boolean
  override val value = false
}