package nl.dirkgroot.adventofcode.util

import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

typealias Solution<R> = (String) -> R

@OptIn(ExperimentalTime::class)
infix fun <R> Solution<R>.invokedWith(input: String): R =
    measureTimedValue { invoke(input) }.let {
        println("Solution: ${it.value}")
        println("Time:     ${it.duration}")
        it.value
    }
