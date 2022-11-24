package nl.dirkgroot.adventofcode.year2019

import nl.dirkgroot.adventofcode.util.Input
import nl.dirkgroot.adventofcode.util.Puzzle

class Day01(input: Input) : Puzzle() {
    private val moduleMasses = input.lines()
        .map(String::toInt)

    override fun part1() = moduleMasses.sumOf { fuelRequired(it) }

    override fun part2(): Any {
        return moduleMasses.sumOf {
            generateSequence(fuelRequired(it)) { previous ->
                val next = fuelRequired(previous)
                if (next <= 0) null
                else next
            }.sum()
        }
    }

    private fun fuelRequired(mass: Int) = mass / 3 - 2
}
