package io.klibs.json.scanner

import io.klibs.json.token.JSONToken

interface JSONTokenScanner {
  operator fun hasNext(): Boolean
  operator fun next(): JSONToken
}