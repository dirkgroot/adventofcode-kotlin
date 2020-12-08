package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.Input
import nl.dricus.adventofcode.util.Puzzle

class Day05(input: Input) : Puzzle() {
    private val allPossibleSeatIds = 0..0b1111111111

    private val occupiedSeatIds by lazy {
        input.lines()
            .map {
                it.foldIndexed(0) { index, seatId, character ->
                    if (character in "BR")
                        seatId or (1 shl (9 - index))
                    else
                        seatId
                }
            }
    }

    override fun part1() = occupiedSeatIds.maxOrNull()!!

    override fun part2() =
        (allPossibleSeatIds subtract occupiedSeatIds)
            .first { occupiedSeatIds.contains(it - 1) && occupiedSeatIds.contains(it + 1) }
}