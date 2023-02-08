package io.klibs.json.token

enum class JSONTokenType {
  StreamStart,
  StreamEnd,
  ObjectStart,
  ObjectEnd,
  ArrayStart,
  ArrayEnd,
  EntrySeparator,
  String,
  Number,
  Boolean,
  Null,
  ;

  inline val isScalar
    get() = when (this) {
      String, Number, Boolean, Null -> true
      else                          -> false
    }
}