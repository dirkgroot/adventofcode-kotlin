package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.Input
import nl.dricus.adventofcode.util.Puzzle

class Day23(val input: Input) : Puzzle() {
    private val order by lazy { input.string().map { it - '0' - 1 } }

    private class Cup(val label: Int, next: Cup? = null) {
        var next: Cup = next ?: this
        override fun toString() = "label=$label"
    }

    override fun part1(): String {
        val index = createIndex(order, 9)
        play(index, order[0], 100)
        return createPart1Result(index[0])
    }

    private tailrec fun createPart1Result(head: Cup, acc: String = ""): String =
        if (head.label == 0) acc
        else createPart1Result(head.next, acc + (head.label + 1))

    override fun part2(): Long {
        val index = createIndex(order, 1_000_000)
        play(index, order[0], 10_000_000)
        return (index[0].next.label.toLong() + 1) * (index[0].next.next.label.toLong() + 1)
    }

    private fun createIndex(order: List<Int>, size: Int): Array<Cup> {
        val result = Array(size) { index -> Cup(index) }
        result.forEachIndexed { index, cup ->
            val orderIndex = order.indexOf(cup.label)
            if (orderIndex in 0..order.lastIndex)
                cup.next = result[order[(orderIndex + 1) % order.size]]
            else
                cup.next = result[(index + 1) % size]
        }
        if (size > order.size) {
            result[order.last()].next = result[order.size]
            result.last().next = result[order[0]]
        }
        return result
    }

    private fun play(index: Array<Cup>, first: Int, turns: Int) {
        fun previousLabel(label: Int) = (label + index.size - 1) % index.size

        var current = index[first]
        repeat(turns) {
            val pick1 = current.next
            val pick2 = pick1.next
            val pick3 = pick2.next

            var destinationLabel = previousLabel(current.label)
            while (pick1.label == destinationLabel || pick2.label == destinationLabel || pick3.label == destinationLabel) {
                destinationLabel = previousLabel(destinationLabel)
            }
            val destination = index[destinationLabel]

            current.next = current.next.next.next.next

            val old = destination.next
            destination.next = pick1
            pick3.next = old
            current = current.next
        }
    }
}