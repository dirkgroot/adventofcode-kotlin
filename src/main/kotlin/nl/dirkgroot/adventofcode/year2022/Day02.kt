package nl.dirkgroot.adventofcode.year2022

import nl.dirkgroot.adventofcode.util.Input
import nl.dirkgroot.adventofcode.util.Puzzle

class Day02(input: Input) : Puzzle() {
    private val strategy = input.lines()
    private val scores = mapOf(
        "A X" to arrayOf(4L, 3L), "A Y" to arrayOf(8L, 4L), "A Z" to arrayOf(3L, 8L),
        "B X" to arrayOf(1L, 1L), "B Y" to arrayOf(5L, 5L), "B Z" to arrayOf(9L, 9L),
        "C X" to arrayOf(7L, 2L), "C Y" to arrayOf(2L, 6L), "C Z" to arrayOf(6L, 7L),
    )

    override fun part1() = totalScore(0)
    override fun part2() = totalScore(1)
    private fun totalScore(part: Int) = strategy.fold(0L) { acc, line -> acc + scores.getValue(line)[part] }
}