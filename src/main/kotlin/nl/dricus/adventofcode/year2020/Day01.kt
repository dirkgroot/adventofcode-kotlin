package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.Input
import nl.dricus.adventofcode.util.Puzzle

object Day01 : Puzzle {
    private val input by lazy { Input.lines(2020, 1).map { it.toInt() } }

    override fun part1() =
        input
            .filter { n1 -> input.any { n2 -> n1 + n2 == 2020 } }
            .fold(1, { n1, n2 -> n1 * n2 })

    override fun part2() =
        input
            .filter { n1 ->
                input.any { n2 ->
                    input.any { n3 -> n1 + n2 + n3 == 2020 }
                }
            }
            .fold(1, { n1, n2 -> n1 * n2 })
}
