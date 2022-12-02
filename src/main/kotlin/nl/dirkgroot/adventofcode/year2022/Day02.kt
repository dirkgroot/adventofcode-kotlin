package nl.dirkgroot.adventofcode.year2022

import nl.dirkgroot.adventofcode.util.Input
import nl.dirkgroot.adventofcode.util.Puzzle

class Day02(input: Input) : Puzzle() {
    private val scores = mapOf(
        'A' to mapOf('A' to arrayOf(4L, 3L), 'B' to arrayOf(8L, 4L), 'C' to arrayOf(3L, 8L)),
        'B' to mapOf('A' to arrayOf(1L, 1L), 'B' to arrayOf(5L, 5L), 'C' to arrayOf(9L, 9L)),
        'C' to mapOf('A' to arrayOf(7L, 2L), 'B' to arrayOf(2L, 6L), 'C' to arrayOf(6L, 7L)),
    )
    private val strategy = input.lines()
        .map { round -> round.split(" ").map { it[0] } }
        .map { it[0] to (it[1] - 23) }

    override fun part1() = totalScore(0)
    override fun part2() = totalScore(1)

    private fun totalScore(part: Int) =
        strategy.fold(0L) { acc, (opponent, me) ->
            acc + scores.getOrDefault(opponent, mapOf()).getOrDefault(me, arrayOf())[part]
        }
}