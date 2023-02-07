package io.klibs.json.util.pool

interface JSONPoolableResource {
  fun returnToPool()
}