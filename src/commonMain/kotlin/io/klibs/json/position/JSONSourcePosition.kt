package io.klibs.json.position

import io.klibs.json.util.pool.JSONPoolableResource

/**
 * Represents a "cursor" position in an input JSON stream or document.
 *
 * **Examples**
 *
 * *In the following examples the "cursor" position will be represented by the
 * caret character (`'^'`).*
 *
 * Given the following JSON input:
 * ```json
 * 1 | {
 * 2 |   "hello": "goodbye"
 * 3 | }
 * ```
 *
 * The start of the JSON stream position would be `(0, 0, 0)`:
 * ```json
 * 1 | {
 * * | ^
 * 2 |   "hello": "goodbye"
 * 3 | }
 * ```
 *
 * The start of the string token `"hello"` would be `(4, 1, 2)`:
 * ```json
 * 1 | {
 * 2 |   "hello": "goodbye"
 * * |   ^
 * 3 | }
 * ```
 *
 * The end of the JSON stream would be `(24, 2, 1)`:
 * ```json
 * 1 | {
 * 2 |   "hello": "goodbye"
 * 3 | }
 * * |  ^
 * ```
 *
 * @author Elizabeth Paige Harper - https://github.com/foxcapades
 * @since v0.1.0
 */
sealed interface JSONSourcePosition : JSONPoolableResource {

  /**
   * The overall index of the position in the input JSON stream or document.
   */
  val index: UInt

  /**
   * The line number the position appears on in the input JSON stream or
   * document.
   */
  val line: UInt

  /**
   * The column index of the position on the specified [line] in the input JSON
   * stream or document.
   */
  val column: UInt

  operator fun compareTo(other: JSONSourcePosition) =
    when {
      this.index < other.index -> -1
      this.index > other.index -> 1
      else                     -> 0
    }
}
