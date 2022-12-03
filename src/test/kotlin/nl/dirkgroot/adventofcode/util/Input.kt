package nl.dirkgroot.adventofcode.util

fun input(year: Int, day: Int) = readFromClassPath(year, day).readText()

private fun readFromClassPath(year: Int, day: Int) = object {}.javaClass
    .getResourceAsStream("/inputs/${year}/${java.lang.String.format("%02d", day)}.txt")
    ?.bufferedReader()
    ?: throw IllegalStateException("Input for year $year, day $day not available!")
