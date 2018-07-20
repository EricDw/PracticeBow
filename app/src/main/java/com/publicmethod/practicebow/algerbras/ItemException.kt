package com.publicmethod.practicebow.algerbras

sealed class ItemException : Exception() {
    object NoItemFoundException : ItemException()
}