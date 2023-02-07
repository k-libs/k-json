package io.klibs.json.position

/**
 * JSON Source Position Implementation
 *
 * Basic implementation of the [JSONSourcePosition] interface.
 *
 * @author Elizabeth Paige Harper - https://github.com/foxcapades
 * @since 0.1.0
 */
internal data class JSONSourcePositionImpl(
  private val pool: SourcePositionPool,
  override var index: UInt,
  override var line: UInt,
  override var column: UInt,
) : JSONSourcePosition {

  override fun returnToPool() {
    pool.reclaim(this)
  }

  fun clear() {
    index = 0u
    line = 0u
    column = 0u
  }

  override fun toString() = "{index=$index, line=$line, column=$column}"
}