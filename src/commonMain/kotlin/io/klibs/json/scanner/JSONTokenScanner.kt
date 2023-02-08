package io.klibs.json.scanner

import io.klibs.json.token.JSONToken

interface JSONTokenScanner : Iterable<JSONToken>, Iterator<JSONToken>
