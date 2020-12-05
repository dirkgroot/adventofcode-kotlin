package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.Input
import nl.dricus.adventofcode.util.Puzzle

object Day05 : Puzzle() {
    private val allPossibleSeatIds = 0..0b1111111111

    private val occupiedSeatIds by lazy {
        Input.lines(2020, 5)
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