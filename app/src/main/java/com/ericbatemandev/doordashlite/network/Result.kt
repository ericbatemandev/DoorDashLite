package com.ericbatemandev.doordashlite.network

sealed class Result<out T> {
    data class Success<T>(val body: T) : Result<T>()
    data class Failure(val errorMessage: String) : Result<Nothing>()
}
