package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.Input
import nl.dricus.adventofcode.util.Puzzle

class Day10(input: Input) : Puzzle() {
    private val adapters by lazy { input.lines().map { it.toLong() } }

    override fun part1() = adapters.plus(0).sorted()
        .windowed(2)
        .fold(0L to 0L) { (diff1, diff3), (a, b) ->
            (if (b - a == 1L) diff1 + 1 else diff1) to
                    (if (b - a == 3L) diff3 + 1 else diff3)
        }
        .let { (diff1, diff3) -> diff1 * (diff3 + 1) }

    override fun part2() = adapters.plus(0L).sortedDescending()
        .fold(mapOf((adapters.maxOrNull()!! + 3L) to 1L)) { acc, current ->
            acc.plus(
                current to (1L..3L).fold(0L) { cur, diff ->
                    cur + acc.getOrDefault(current + diff, 0L)
                }
            )
        }[0L]!!
}