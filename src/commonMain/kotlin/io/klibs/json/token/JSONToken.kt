package io.klibs.json.token

import io.klibs.json.position.JSONSourcePosition

sealed interface JSONToken {
  val type: JSONTokenType
  val data: JSONTokenData?
  val start: JSONSourcePosition
  val end: JSONSourcePosition
}

