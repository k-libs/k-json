package io.klibs.json.token

import io.klibs.json.position.JSONSourcePosition

internal data class JSONTokenImpl(
  override val type: JSONTokenType,
  override val data: JSONTokenData?,
  override val start: JSONSourcePosition,
  override val end: JSONSourcePosition
) : JSONToken