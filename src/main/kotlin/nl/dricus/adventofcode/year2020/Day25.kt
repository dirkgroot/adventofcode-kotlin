package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.Input
import nl.dricus.adventofcode.util.Puzzle

class Day25(input: Input) : Puzzle() {
    private val subjectNumber = 7L
    private val cardPublicKey = input.lines()[0].toLong()
    private val doorPublicKey = input.lines()[1].toLong()

    override fun part1() = solve()

    private tailrec fun solve(i: Long = 1, k: Long = 1): Long =
        if (i == cardPublicKey) k
        else solve((i * subjectNumber) % 20201227, (k * doorPublicKey) % 20201227)
}