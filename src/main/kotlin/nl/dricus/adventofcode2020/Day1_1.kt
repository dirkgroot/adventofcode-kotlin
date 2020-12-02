package nl.dricus.adventofcode2020

import nl.dricus.adventofcode2020.util.readInputLines

fun main() {
    val numbers = readInputLines("/inputs/day1/input.txt")
        .map { Integer.parseInt(it) }
    val solution = numbers
        .filter { n1 -> numbers.any { n2 -> n1 + n2 == 2020 } }
        .fold(1, { n1, n2 -> n1 * n2 })

    println("Solution: $solution")
}
