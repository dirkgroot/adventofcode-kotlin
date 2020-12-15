package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.Input
import nl.dricus.adventofcode.util.Puzzle

class Day15(input: Input) : Puzzle() {
    private val startingNumbers by lazy { input.string().split(",").map { it.toInt() } }

    override fun part1() = solve(2019)

    override fun part2() = solve(29999999)

    private fun solve(maxIndex: Int): Int {
        val numbers = startingNumbers.toMutableList()
        val seen = mutableMapOf<Int, MutableList<Int>>()

        fun add(i: Int, index: Int) {
            val list = seen[i] ?: (mutableListOf<Int>().apply { seen[i] = this })
            list.add(index)
            if (list.size > 2) list.removeAt(0)
        }

        numbers.forEachIndexed { index, i ->
            add(i, index)
        }

        var last = numbers.last()
        for (i in (numbers.lastIndex + 1)..maxIndex) {
            val indices = seen[last]!!

            last = if (indices.size == 2) indices[1] - indices[0] else 0
            add(last, i)
        }

        return last
    }
}