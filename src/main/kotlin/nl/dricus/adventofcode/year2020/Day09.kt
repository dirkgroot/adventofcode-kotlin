package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.Input
import nl.dricus.adventofcode.util.Puzzle
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

class Day09(input: Input, private val preambleLenght: Int, private val part1Solution: Long) : Puzzle() {
    private val numbers by lazy { input.lines().map { it.toLong() } }

    override fun part1() =
        numbers.drop(preambleLenght).withIndex()
            .first { (index, number) ->
                val preamble = numbers.drop(index).take(preambleLenght)
                !preamble.any { n1 -> preamble.any { n2 -> n1 + n2 == number } }
            }
            .value

    private class State(val sum: Long = 0, val min: Long = Long.MAX_VALUE, val max: Long = 0)

    override fun part2() = numbers.indices.asSequence()
        .map { index ->
            numbers.asSequence().drop(index)
                .runningFold(State()) { state, number ->
                    State(state.sum + number, min(state.min, number), max(state.max, number))
                }
                .first { it.sum >= part1Solution }
        }
        .first { state -> state.sum == part1Solution }
        .let { state -> state.min + state.max }

    /**
     * Solution inspired by https://en.wikipedia.org/wiki/Bogosort.
     */
    fun bogoPart2() = generateSequence { Random.nextInt(0, numbers.lastIndex - 1) }
        .map { start -> start to Random.nextInt(start + 1, numbers.lastIndex + 1) }
        .map { (start, end) -> numbers.subList(start, end + 1) }
        .first { it.sum() == part1Solution }
        .let { it.minOrNull()!! + it.maxOrNull()!! }
}