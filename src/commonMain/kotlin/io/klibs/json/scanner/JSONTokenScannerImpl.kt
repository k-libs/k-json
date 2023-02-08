package io.klibs.json.scanner

import io.klibs.json.consts.*
import io.klibs.json.consts.ASCII_COMMA
import io.klibs.json.consts.ASCII_CURLY_CLOSE
import io.klibs.json.consts.ASCII_CURLY_OPEN
import io.klibs.json.consts.ASCII_SQUARE_CLOSE
import io.klibs.json.consts.ASCII_SQUARE_OPEN
import io.klibs.json.position.JSONSourcePositionTracker
import io.klibs.json.read.BufferedUByteReader
import io.klibs.json.read.isNumberLeader
import io.klibs.json.read.unsafeIsSquareClose
import io.klibs.json.read.unsafeIsSquareOpen
import io.klibs.json.token.JSONToken
import io.klibs.json.token.JSONTokenImpl
import io.klibs.json.token.JSONTokenType

internal class JSONTokenScannerImpl : JSONTokenScanner {

  private var streamStarted = false
  private var streamEnded = false

  private val position = JSONSourcePositionTracker()

  private val buffer = BufferedUByteReader()

  override fun iterator(): Iterator<JSONToken> = this

  override fun hasNext() = !streamEnded

  override fun next(): JSONToken {
    if (!streamStarted) {
      streamStarted = true
      return fetchStreamStart()
    }

    skipBlanks()

    buffer.cache(1)

    if (buffer.isEmpty) {
      if (buffer.atEOF) {
        streamEnded = true
        return fetchStreamEnd()
      } else {
        throw IllegalStateException("buffer was somehow empty")
      }
    }

    if (buffer.isNumberLeader())
      return fetchNumber()

    return when (buffer.peek()) {
      ASCII_DOUBLE_QUOTE -> fetchString()
      ASCII_COMMA -> fetchEntrySeparator()
      ASCII_LOWERCASE_T -> fetchTrue()
      ASCII_LOWERCASE_F -> fetchFalse()
      ASCII_LOWERCASE_N -> fetchNull()
      ASCII_SQUARE_CLOSE -> fetchArrayEnd()
      ASCII_SQUARE_OPEN -> fetchArrayStart()
      ASCII_CURLY_CLOSE -> fetchObjectEnd()
      ASCII_CURLY_OPEN -> fetchObjectStart()
      else -> {}
    }
  }

  private fun fetchStreamStart(): JSONToken {
    val pos = position.mark()
    return JSONTokenImpl(JSONTokenType.StreamStart, null, pos, pos)
  }

  private fun fetchStreamEnd(): JSONToken {
    val pos = position.mark()
    return JSONTokenImpl(JSONTokenType.StreamEnd, null, pos, pos)
  }
}