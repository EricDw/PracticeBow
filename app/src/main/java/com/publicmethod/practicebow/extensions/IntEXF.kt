package com.publicmethod.practicebow.extensions

fun Int.isEven(): Boolean = this divBy 2
fun Int.isDivisibleBy(divisor: Int): Boolean = this % divisor == 0
infix fun Int.divBy(divisor: Int) = isDivisibleBy(divisor)