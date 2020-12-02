package nl.dricus.adventofcode2020.util

fun readInputLines(file: String): List<String> {
    object {}.javaClass.getResourceAsStream(file).use {
        return it.bufferedReader().readLines()
    }
}