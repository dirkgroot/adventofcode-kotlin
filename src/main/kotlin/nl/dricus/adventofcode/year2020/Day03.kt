package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.Input
import nl.dricus.adventofcode.util.Puzzle

object Day03 : Puzzle() {
    private val input by lazy { Input.lines(2020, 3) }
    private val width by lazy { input[0].length }

    override fun part1(): Any = countTrees(3, 1)

    override fun part2(): Any =
        countTrees(1, 1) *
                countTrees(3, 1) *
                countTrees(5, 1) *
                countTrees(7, 1) *
                countTrees(1, 2)

    private fun countTrees(slopeX: Int, slopeY: Int): Long =
        input
            .filterIndexed { index, line ->
                (index % slopeY == 0) && line[(index / slopeY * slopeX) % width] == '#'
            }
            .count().toLong()
}