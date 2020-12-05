package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.Input
import nl.dricus.adventofcode.util.Puzzle

object Day05 : Puzzle() {
    private val allPossibleIds = 0..0b1111111111

    private val input by lazy {
        Input.lines(2020, 5)
            .map {
                it.foldIndexed(0) { index, acc, character ->
                    if (character in "BR")
                        acc or (1 shl (9 - index))
                    else
                        acc
                }
            }
    }

    override fun part1() = input.maxOrNull()!!

    override fun part2() =
        (allPossibleIds subtract input)
            .first { input.contains(it - 1) && input.contains(it + 1) }
}