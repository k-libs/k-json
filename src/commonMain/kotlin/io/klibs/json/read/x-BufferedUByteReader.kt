package io.klibs.json.read

import io.klibs.json.consts.*

internal inline fun BufferedUByteReader.check(octet: UByte, offset: Int = 0) =
  size > offset && get(offset) == octet

internal inline fun BufferedUByteReader.isSquareOpen(offset: Int = 0) =
  size > offset && get(offset) == ASCII_SQUARE_OPEN

internal fun BufferedUByteReader.isSquareClose(offset: Int = 0) =
  size > offset && get(offset) == ASCII_SQUARE_CLOSE

internal fun BufferedUByteReader.isCurlyOpen(): Boolean {}
internal fun BufferedUByteReader.isCurlyClose(): Boolean {}

internal fun BufferedUByteReader.isComma(): Boolean {}

internal fun BufferedUByteReader.isDoubleQuote(): Boolean {}


internal fun BufferedUByteReader.isCRLF(offset: Int = 0) =
  size > offset + 1 && get(offset) == ASCII_CARRIAGE_RETURN && get(offset + 1) == ASCII_LINE_FEED

internal fun BufferedUByteReader.isEOF(offset: Int = 0) =
  size <= offset && atEOF


internal inline fun BufferedUByteReader.unsafeIsComma(offset: Int = 0) =
  get(offset) == ASCII_COMMA

internal inline fun BufferedUByteReader.unsafeIsCurlyOpen(offset: Int = 0) =
  get(offset) == ASCII_CURLY_OPEN

internal inline fun BufferedUByteReader.unsafeIsCurlyClose(offset: Int = 0) =
  get(offset) == ASCII_CURLY_CLOSE

internal inline fun BufferedUByteReader.unsafeIsSquareOpen(offset: Int = 0) =
  get(offset) == ASCII_SQUARE_OPEN

internal inline fun BufferedUByteReader.unsafeIsSquareClose(offset: Int = 0) =
  get(offset) == ASCII_SQUARE_CLOSE

internal inline fun BufferedUByteReader.unsafeIsBlank(offset: Int = 0) =
  when (get(offset)) { ASCII_SPACE, ASCII_TAB -> true; else -> false }

internal inline fun BufferedUByteReader.unsafeIsBreak(offset: Int = 0) =
  get(offset) == ASCII_LINE_FEED || get(offset) == ASCII_CARRIAGE_RETURN

internal inline fun BufferedUByteReader.unsafeIsBlankOrBreak(offset: Int = 0) =
  when (get(offset)) { ASCII_SPACE, ASCII_LINE_FEED, ASCII_TAB, ASCII_CARRIAGE_RETURN -> true; else -> false }


internal inline fun BufferedUByteReader.isTokenEnder(offset: Int = 0) =
  isEOF(offset)
    || unsafeIsBlankOrBreak(offset)
    || unsafeIsComma(offset)
    || unsafeIsCurlyClose(offset)
    || unsafeIsSquareClose(offset)