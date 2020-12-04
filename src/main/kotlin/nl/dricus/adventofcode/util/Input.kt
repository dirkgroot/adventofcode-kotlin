package nl.dricus.adventofcode.util

import java.io.InputStream
import java.lang.String.format

object Input {
    fun lines(year: Int, day: Int): List<String> {
        inputAsStream(year, day).use {
            return it.bufferedReader().readLines()
        }
    }

    fun string(year: Int, day: Int): String {
        inputAsStream(year, day).use {
            return it.bufferedReader().readText()
        }
    }

    private fun inputAsStream(year: Int, day: Int): InputStream {
        val file = "/inputs/${year}/${format("%02d", day)}.txt"

        return object {}.javaClass.getResourceAsStream(file)
    }
}