package com.confiz.lezzgo.data.network

sealed class NetworkException private constructor(
    override val message: String
): Exception(message) {
    object Default: NetworkException("Unable to connect to the internet")
}
