package io.klibs.json.util

import kotlin.jvm.JvmInline

@JvmInline
@Suppress("OPT_IN_USAGE")
value class ConstUByteArray(private val raw: UByteArray) {
  val size
    get() = raw.size

  operator fun get(i: Int) = raw[i]

  fun contentHashCode() = raw.contentHashCode()
}