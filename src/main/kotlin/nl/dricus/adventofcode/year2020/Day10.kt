package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.Input
import nl.dricus.adventofcode.util.Puzzle

class Day10(input: Input) : Puzzle() {
    private val adapters by lazy { input.lines().map { it.toLong() } }
    private val deviceJoltage by lazy { adapters.maxOrNull()!! + 3 }

    override fun part1() = adapters.plus(0).sorted()
        .windowed(2)
        .groupBy { (a, b) -> b - a }
        .let { diffs -> diffs[1L]!!.size * (diffs[3L]!!.size + 1L) }

    override fun part2() = adapters.plus(deviceJoltage).sorted()
        .fold(mapOf(0L to 1L)) { combinations, joltage ->
            combinations + (joltage to (-3L..-1L).sumOf { combinations.getOrDefault(joltage + it, 0L) })
        }[deviceJoltage]!!
}