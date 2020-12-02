package nl.dricus.adventofcode2020

import nl.dricus.adventofcode2020.util.readInputLines
import java.lang.IllegalStateException

fun main() {
    val entries = readInputLines("/inputs/day2/input.txt")
    val solution = entries.count { isValid(it) }

    println("Solution: $solution")
}

private fun isValid(entry: String): Boolean {
    val match = Regex("(\\d+)-(\\d+) ([a-z]): (.*)").matchEntire(entry) ?: throw IllegalStateException(entry)

    val first = Integer.parseInt(match.groupValues[1]) - 1
    val second = Integer.parseInt(match.groupValues[2]) - 1
    val character = match.groupValues[3][0]
    val password = match.groupValues[4]

    return (password[first] == character) xor (password[second] == character)
}
