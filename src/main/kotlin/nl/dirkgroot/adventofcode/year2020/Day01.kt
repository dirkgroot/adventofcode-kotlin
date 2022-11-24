package nl.dirkgroot.adventofcode.year2020

import nl.dirkgroot.adventofcode.util.Input
import nl.dirkgroot.adventofcode.util.Puzzle

class Day01(input: Input) : Puzzle() {
    private val input by lazy { input.lines().map { it.toInt() } }

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
