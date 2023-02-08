package io.klibs.json.read

class BufferedUByteReader {
  val size: Int

  val atEOF: Boolean

  val isEmpty: Boolean

  fun cache(chars: Int)

  fun peek(): UByte

  fun pop(): UByte

  fun skip(bytes: Int)

  operator fun get(offset: Int): UByte
}