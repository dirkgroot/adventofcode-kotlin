package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.Input
import nl.dricus.adventofcode.util.Puzzle

class Day15(input: Input) : Puzzle() {
    private val startingNumbers by lazy { input.string().split(",").map { it.toInt() } }

    override fun part1() = solve(2_020)

    override fun part2() = solve(30_000_000)

    private fun solve(maxIndex: Int): Int {
        val spoken = IntArray(maxIndex) { -1 }

        tailrec fun turns(turn: Int, lastSpoken: Int, next: Int): Int {
            if (turn == maxIndex) return lastSpoken

            val nextToSpeek = spoken[next].let { found -> if (found == -1) 0 else turn - found }
            spoken[next] = turn
            return turns(turn + 1, next, nextToSpeek)
        }

        startingNumbers.forEachIndexed { index, n -> spoken[n] = index }

        return turns(startingNumbers.size, startingNumbers.last(), 0)
    }
}