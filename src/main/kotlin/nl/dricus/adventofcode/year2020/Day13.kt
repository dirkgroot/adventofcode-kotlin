package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.Input
import nl.dricus.adventofcode.util.Puzzle

class Day13(input: Input) : Puzzle() {
    private val inputLines by lazy { input.lines() }
    private val earliestTime by lazy { inputLines[0].toInt() }
    private val schedule1 by lazy { inputLines[1].split(",").filter { it != "x" }.map { it.toInt() }.toSet() }
    private val schedule2 by lazy { inputLines[1].split(",").map { if (it == "x") 1 else it.toLong() } }

    override fun part1() = generateSequence(earliestTime) { it + 1 }
        .map { time -> time to schedule1.firstOrNull { busId -> time % busId == 0 } }
        .filter { (_, busId) -> busId != null }
        .first()
        .let { (time, busId) -> busId!! * (time - earliestTime) }

    override fun part2() = schedule2
        .foldIndexed(1L to 1L) { index, (i, inc), it ->
            findNext(i + index, inc, it) - index to inc * it
        }
        .let { (i, _) -> i }

    private tailrec fun findNext(i: Long, inc: Long, divisor: Long): Long =
        if (i % divisor == 0L) i
        else findNext(i + inc, inc, divisor)
}