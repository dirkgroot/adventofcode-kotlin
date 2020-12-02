package nl.dricus.adventofcode.util

import java.lang.String.format

object Input {
    fun lines(year: Int, day: Int): List<String> {
        val file = "/inputs/${year}/${format("%02d", day)}.txt"

        object {}.javaClass.getResourceAsStream(file).use {
            return it.bufferedReader().readLines()
        }
    }
}