package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.Input
import nl.dricus.adventofcode.util.Puzzle

class Day23(val input: Input) : Puzzle() {
    private val order by lazy { input.string().map { it - '0' - 1 } }

    override fun part1(): String {
        val index = createIndex(order, 9)
        play(index, order[0], 100)
        return createPart1Result(index, index[0])
    }

    private tailrec fun createPart1Result(index: IntArray, head: Int, acc: String = ""): String =
        if (head == 0) acc
        else createPart1Result(index, index[head], acc + (head + 1))

    override fun part2(): Long {
        val index = createIndex(order, 1_000_000)
        play(index, order[0], 10_000_000)
        return (index[0].toLong() + 1) * (index[index[0]].toLong() + 1)
    }

    private fun createIndex(initialCups: List<Int>, size: Int): IntArray {
        val allCups = IntArray(size) { index -> if (index < initialCups.size) initialCups[index] else index }
        val result = IntArray(size)
        allCups.forEachIndexed { index, label ->
            result[label] = allCups[(index + 1) % allCups.size]
        }
        return result
    }

    private fun play(index: IntArray, first: Int, turns: Int) {
        fun previousLabel(label: Int) = (label + index.size - 1) % index.size

        var current = first
        repeat(turns) {
            val pick1 = index[current]
            val pick2 = index[pick1]
            val pick3 = index[pick2]

            var destinationLabel = previousLabel(current)
            while (pick1 == destinationLabel || pick2 == destinationLabel || pick3 == destinationLabel) {
                destinationLabel = previousLabel(destinationLabel)
            }

            val old = index[destinationLabel]
            index[destinationLabel] = pick1
            index[current] = index[pick3]
            index[pick3] = old

            current = index[current]
        }
    }
}