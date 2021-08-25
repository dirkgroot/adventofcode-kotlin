package nl.dricus.adventofcode.year2019

import nl.dricus.adventofcode.util.Input
import nl.dricus.adventofcode.util.Puzzle

class Day01(input: Input) : Puzzle() {
    private val moduleMasses = input.lines()
        .map(String::toInt)

    override fun part1() = moduleMasses.sumBy { fuelRequired(it) }

    override fun part2(): Any {
        return moduleMasses.sumBy {
            generateSequence(fuelRequired(it)) { previous ->
                val next = fuelRequired(previous)
                if (next <= 0) null
                else next
            }.sum()
        }
    }

    private fun fuelRequired(mass: Int) = mass / 3 - 2
}
