package io.klibs.json.position

internal data class JSONSourcePositionImpl(
  override val index: UInt,
  override val line: UInt,
  override val column: UInt,
) : JSONSourcePosition