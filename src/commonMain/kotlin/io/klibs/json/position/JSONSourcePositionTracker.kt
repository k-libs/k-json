package io.klibs.json.position

internal data class JSONSourcePositionTracker(
  var index: UInt = 0u,
  var line: UInt = 0u,
  var column: UInt = 0u,
) {
  inline fun incPos(chars: Int = 1) {
    index += chars.toUInt()
    column += chars.toUInt()
  }

  inline fun incLine(chars: Int = 1) {
    index += chars.toUInt()
    line++
    column = 0u
  }

  fun mark(): JSONSourcePosition = JSONSourcePositionImpl(index, line, column)

  fun mark(
    modIndex: Int,
    modLine: Int,
    modColumn: Int,
  ): JSONSourcePosition = JSONSourcePositionImpl(
    if (modIndex > 0) index + modIndex.toUInt() else index - (-modIndex).toUInt(),
    if (modLine > 0) line + modLine.toUInt() else line - (-modLine).toUInt(),
    if (modColumn > 0) column + modColumn.toUInt() else column - (-modColumn).toUInt(),
  )
}