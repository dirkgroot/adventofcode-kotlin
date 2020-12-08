package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.Input
import nl.dricus.adventofcode.util.Puzzle

class Day02(input: Input) : Puzzle() {
    private val input by lazy { input.lines().map { Entry(it) } }

    override fun part1() = input.count { isValidPart1(it) }

    private fun isValidPart1(entry: Entry): Boolean {
        val count = entry.password.count { it == entry.character }

        return count in entry.a..entry.b
    }

    override fun part2() = input.count { isValidPart2(it) }

    private fun isValidPart2(entry: Entry): Boolean {
        return (entry.password[entry.a - 1] == entry.character) xor (entry.password[entry.b - 1] == entry.character)
    }

    private class Entry(entry: String) {
        val a: Int
        val b: Int
        val character: Char
        val password: String

        init {
            val match = Regex("(\\d+)-(\\d+) ([a-z]): (.*)").matchEntire(entry) ?: throw IllegalStateException(entry)

            a = match.groupValues[1].toInt()
            b = match.groupValues[2].toInt()
            character = match.groupValues[3][0]
            password = match.groupValues[4]
        }
    }
}
