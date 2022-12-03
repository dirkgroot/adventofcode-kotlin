package nl.dirkgroot.adventofcode.util

import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

@OptIn(ExperimentalTime::class)
fun input(year: Int, day: Int) = measureTimedValue { readFromClassPath(year, day).readText() }
    .let {
        println("Year $year, day $day")
        println("I/O:      ${it.duration}")
        it.value
    }

private fun readFromClassPath(year: Int, day: Int) = object {}.javaClass
    .getResourceAsStream("/inputs/${year}/${java.lang.String.format("%02d", day)}.txt")
    ?.bufferedReader()
    ?: throw IllegalStateException("Input for year $year, day $day not available!")
