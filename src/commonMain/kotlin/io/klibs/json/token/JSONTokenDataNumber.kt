package io.klibs.json.token

import io.klibs.json.util.ConstUByteArray
import kotlin.jvm.JvmInline

@JvmInline
value class JSONTokenDataNumber(val value: ConstUByteArray) : JSONTokenData