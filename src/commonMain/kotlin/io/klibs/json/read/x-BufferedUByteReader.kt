package io.klibs.json.read

import io.klibs.json.consts.*

internal inline fun BufferedUByteReader.isSquareOpen(offset: Int = 0) =
  size > offset && get(offset) == ASCII_SQUARE_OPEN

internal inline fun BufferedUByteReader.unsafeIsSquareOpen(offset: Int = 0) =
  get(offset) == ASCII_SQUARE_OPEN

internal fun BufferedUByteReader.isSquareClose(offset: Int = 0) =
  size > offset && get(offset) == ASCII_SQUARE_CLOSE

internal fun BufferedUByteReader.unsafeIsSquareClose(offset: Int = 0) =
  get(offset) == ASCII_SQUARE_CLOSE

internal fun BufferedUByteReader.isCurlyOpen(): Boolean {}
internal fun BufferedUByteReader.isCurlyClose(): Boolean {}

internal fun BufferedUByteReader.isComma(): Boolean {}

internal fun BufferedUByteReader.isNumberLeader(): Boolean {}

internal fun BufferedUByteReader.isDoubleQuote(): Boolean {}