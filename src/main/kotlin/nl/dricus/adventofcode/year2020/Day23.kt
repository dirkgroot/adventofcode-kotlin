package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.Input
import nl.dricus.adventofcode.util.Puzzle
import kotlin.math.max

class Day23(input: Input) : Puzzle() {
    private val firstCup by lazy { createCups(input.string()) }

    private class Cup(val label: Int, next: Cup? = null) {
        var next: Cup = next ?: this
        override fun toString() = "label=$label"
    }

    private fun createCups(labels: String): Cup {
        val prep = labels.map { Cup(it - '0') }
        return prep.onEachIndexed { index, cup -> cup.next = prep[(index + 1) % prep.size] }[0]
    }

    override fun part1(): String {
        play(firstCup, createIndex(), 9, 100)
        return createPart1Result(firstCup)
    }

    private fun createPart1Result(head: Cup): String {
        tailrec fun append(cup: Cup, acc: String): String =
            if (cup.label == 1) acc
            else append(cup.next, acc + cup.label)

        tailrec fun find(cup: Cup, label: Int): Cup =
            if (cup.label == label) cup else find(cup.next, 1)

        return append(find(head, 1).next, "")
    }

    override fun part2(): Long {
        createMany(firstCup, firstCup, 1_000_000)

        play(firstCup, createIndex(), 1_000_000, 10_000_000)

        return createPart2Result(firstCup)
    }

    private tailrec fun createPart2Result(head: Cup): Long =
        if (head.label == 1) head.next.label.toLong() * head.next.next.label.toLong()
        else createPart2Result(head.next)

    private tailrec fun createMany(head: Cup, current: Cup, count: Int, nextLabel: Int = 0): Cup =
        when {
            count == 1 -> head
            current.next != head -> createMany(head, current.next, count - 1, max(nextLabel, current.label + 1))
            else -> {
                current.next = Cup(nextLabel, head)
                createMany(head, current.next, count - 1, nextLabel + 1)
            }
        }

    private fun createIndex() =
        generateSequence(firstCup.label to firstCup) { (_, prevCup) ->
            if (prevCup.next == firstCup) null else prevCup.next.label to prevCup.next
        }.toMap()

    private fun play(head: Cup, index: Map<Int, Cup>, cupCount: Int, turns: Int) {
        fun previousLabel(label: Int) = if (label == 1) cupCount else label - 1

        var current = head
        repeat(turns) {
            val pick1 = current.next
            val pick2 = pick1.next
            val pick3 = pick2.next

            current.next = current.next.next.next.next

            var destinationLabel = previousLabel(current.label)
            while (pick1.label == destinationLabel || pick2.label == destinationLabel || pick3.label == destinationLabel) {
                destinationLabel = previousLabel(destinationLabel)
            }
            val destination = index[destinationLabel]!!

            val old = destination.next
            destination.next = pick1
            pick3.next = old
            current = current.next
        }
    }
}