package nl.dricus.adventofcode.util

import java.io.InputStream
import java.lang.String.format

class ClasspathResourceInput(private val year: Int, private val day: Int) : Input {
    override fun lines() = inputAsStream().use { stream ->
        stream.bufferedReader().use { reader -> reader.readLines() }
    }

    override fun string() = inputAsStream().use { stream ->
        stream.bufferedReader().use { reader -> reader.readText() }
    }

    private fun inputAsStream(): InputStream {
        val file = "/inputs/${year}/${format("%02d", day)}.txt"

        return object {}.javaClass.getResourceAsStream(file)
    }
}