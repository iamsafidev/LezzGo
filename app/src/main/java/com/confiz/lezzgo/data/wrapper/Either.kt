package com.figfolders.data.wrapper

import arrow.core.left
import arrow.core.right

fun <T> T.wrap() =
    runCatching { this }.fold({ it.right() }, { Exception(it.localizedMessage).left() })