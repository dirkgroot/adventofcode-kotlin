package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.Input
import nl.dricus.adventofcode.util.Puzzle

class Day03(input: Input) : Puzzle() {
    private val map by lazy { input.lines() }
    private val width by lazy { map[0].length }

    override fun part1(): Any = countTrees(3, 1)

    override fun part2(): Any =
        countTrees(1, 1) *
                countTrees(3, 1) *
                countTrees(5, 1) *
                countTrees(7, 1) *
                countTrees(1, 2)

    private fun countTrees(slopeX: Int, slopeY: Int) =
        map.filterIndexed { index, line ->
            (index % slopeY == 0) && line[(index / slopeY * slopeX) % width] == '#'
        }.size.toLong()
}