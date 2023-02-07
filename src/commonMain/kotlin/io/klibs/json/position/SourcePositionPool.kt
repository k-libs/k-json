package io.klibs.json.position

import io.klibs.collections.Stack

/**
 * Source Position Pool
 *
 * A resource pool for [JSONSourcePosition] instances.
 *
 * @author Elizabeth Paige Harper - https://github.com/foxcapades
 * @since 0.1.0
 *
 * @constructor Constructs a new [SourcePositionPool] instance.
 *
 * @param initialCapacity The initial capacity of the pool's underlying
 * [JSONSourcePosition] holding data container.
 *
 * Default = `4`
 *
 * @param poolScaleFactor The factor controlling the growth of the pool's
 * underlying [JSONSourcePosition] holding data container.
 *
 * Default = `2.0`
 *
 * @param initialLoad The number of [JSONSourcePosition] instances that should
 * be automatically created and pooled on creation of this [SourcePositionPool]
 * instance.
 *
 * Default = `1`
 *
 * @param maxPoolSize The max number of [JSONSourcePosition] instances that the
 * pool will keep in its internal data store.
 *
 * If this pool already contains this many waiting `JSONSourcePosition`
 * instances, additional `JSONSourcePosition` instances that are returned to the
 * pool will simply be discarded
 *
 * Setting this value to `0` will effectively turn this pool into a simple
 * `JSONSourcePosition` factory as no position instances will be kept for reuse.
 *
 * Default = `20`
 *
 * @throws IllegalArgumentException If any of the following conditions are
 * `true`:
 *
 * * `initialCapacity < 0`
 * * `poolScaleFactor <= 1`
 * * `initialLoad < 0`
 * * `initialLoad > initialCapacity`
 * * `maxPoolSize < 0`
 * * `maxPoolSize < initialCapacity`
 */
internal class SourcePositionPool(
  initialCapacity: Int = 4,
  poolScaleFactor: Float = 2F,
  initialLoad: Int = 1,
  maxPoolSize: Int = 20,
) {
  private val pool: Stack<JSONSourcePositionImpl>

  init {
    if (initialCapacity < 0)
      throw IllegalArgumentException("attempted to construct a SourcePositionPool instance with an initial capacity value ($initialCapacity) that is less than 0")
    if (poolScaleFactor <= 1)
      throw IllegalArgumentException("attempted to construct a SourcePositionPool instance with a pool scale factor value ($poolScaleFactor) that is less than or equal to 1")
    if (initialLoad < 0)
      throw IllegalArgumentException("attempted to construct a SourcePositionPool instance with an initial load value ($initialLoad) that is less than 0")
    if (initialLoad > initialCapacity)
      throw IllegalArgumentException("attempted to construct a SourcePositionPool instance with an initial load value ($initialLoad) that is greater than the configured initial capacity ($initialCapacity)")
    if (maxPoolSize < 0)
      throw IllegalArgumentException("attempted to construct a SourcePositionPool instance with a max pool size ($maxPoolSize) value that is less than 0")
    if (maxPoolSize < initialCapacity)
      throw IllegalArgumentException("attempted to construct a SourcePositionPool instance with a max pool size ($maxPoolSize) that is greater than the configured initial capacity ($initialCapacity)")

    pool = Stack(initialCapacity, poolScaleFactor, maxPoolSize)

    var i = 0
    while (i++ < initialLoad)
      pool.push(JSONSourcePositionImpl(this, 0u, 0u, 0u))
  }

  fun borrow(index: UInt, line: UInt, column: UInt): JSONSourcePosition =
    if (pool.isEmpty)
      JSONSourcePositionImpl(this, index, line, column)
    else
      pool.pop()

  fun reclaim(position: JSONSourcePosition) {
    if (position is JSONSourcePositionImpl && pool.size < pool.maxSize) {
      position.clear()
      pool.push(position)
    }
  }
}