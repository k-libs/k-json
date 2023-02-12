package io.klibs.json.scanner

import io.klibs.json.consts.*
import io.klibs.json.consts.ASCII_COMMA
import io.klibs.json.consts.ASCII_CURLY_CLOSE
import io.klibs.json.consts.ASCII_CURLY_OPEN
import io.klibs.json.consts.ASCII_SQUARE_CLOSE
import io.klibs.json.consts.ASCII_SQUARE_OPEN
import io.klibs.json.position.JSONSourcePositionTracker
import io.klibs.json.read.*
import io.klibs.json.read.isNumberLeader
import io.klibs.json.token.*
import io.klibs.json.token.JSONTokenImpl

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

    return when (buffer.peek()) {
      ASCII_DOUBLE_QUOTE                -> fetchString()
      ASCII_COMMA                       -> fetchEntrySeparator()
      ASCII_HYPHEN,
      in ASCII_DIGIT_0 .. ASCII_DIGIT_9 -> fetchNumber()
      ASCII_LOWERCASE_T                 -> fetchTrue()
      ASCII_LOWERCASE_F                 -> fetchFalse()
      ASCII_LOWERCASE_N                 -> fetchNull()
      ASCII_SQUARE_CLOSE                -> fetchArrayEnd()
      ASCII_SQUARE_OPEN                 -> fetchArrayStart()
      ASCII_CURLY_CLOSE                 -> fetchObjectEnd()
      ASCII_CURLY_OPEN                  -> fetchObjectStart()
      else                              -> fetchInvalid()
    }
  }

  private fun skipBlanks() {
    while (true) {
      buffer.cache(2)

      if (buffer.isEmpty)
        break

      when {
        buffer.unsafeIsBlank() -> {
          buffer.skip(1)
          position.incPos(1)
        }

        buffer.isCRLF() -> {
          buffer.skip(2)
          position.incLine(2)
        }

        buffer.unsafeIsBreak() -> {
          buffer.skip(1)
          position.incLine(1)
        }

        else -> break
      }
    }
  }

  private fun skipBytes(count: Int) {
    buffer.skip(4)
    position.incPos(4)
  }

  private fun fetchStreamStart(): JSONToken {
    val pos = position.mark()
    return JSONTokenImpl(JSONTokenType.StreamStart, null, pos, pos)
  }

  private fun fetchStreamEnd(): JSONToken {
    val pos = position.mark()
    return JSONTokenImpl(JSONTokenType.StreamEnd, null, pos, pos)
  }

  private fun fetchTrue(): JSONToken {
    buffer.cache(5)

    if (!(
         buffer.check(ASCII_LOWERCASE_T, 0)
      && buffer.check(ASCII_LOWERCASE_R, 1)
      && buffer.check(ASCII_LOWERCASE_U, 2)
      && buffer.check(ASCII_LOWERCASE_E, 3)
      && buffer.isTokenEnder(4)
    ))
      return fetchInvalid()

    val start = position.mark()

    skipBytes(4)

    return JSONTokenImpl(JSONTokenType.Boolean, JSONTokenDataBooleanTrue, start, position.mark())
  }

  private fun fetchFalse(): JSONToken {
    buffer.cache(6)

    if (!(
         buffer.check(ASCII_LOWERCASE_F, 0)
      && buffer.check(ASCII_LOWERCASE_A, 1)
      && buffer.check(ASCII_LOWERCASE_L, 2)
      && buffer.check(ASCII_LOWERCASE_S, 3)
      && buffer.check(ASCII_LOWERCASE_E, 4)
      && buffer.isTokenEnder(5)
    ))
      return fetchInvalid()

    val start = position.mark()

    skipBytes(5)

    return JSONTokenImpl(JSONTokenType.Boolean, JSONTokenDataBooleanFalse, start, position.mark())
  }

  private fun fetchNull(): JSONToken {
    buffer.cache(5)

    if (!(
         buffer.check(ASCII_LOWERCASE_N, 0)
      && buffer.check(ASCII_LOWERCASE_U, 1)
      && buffer.check(ASCII_LOWERCASE_L, 2)
      && buffer.check(ASCII_LOWERCASE_L, 3)
      && buffer.isTokenEnder(4)
    ))
      return fetchInvalid()

    val start = position.mark()

    skipBytes(4)

    return JSONTokenImpl(JSONTokenType.Null, null, start, position.mark())
  }
}