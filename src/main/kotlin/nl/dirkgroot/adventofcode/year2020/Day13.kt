package nl.dirkgroot.adventofcode.year2020

import nl.dirkgroot.adventofcode.util.Input
import nl.dirkgroot.adventofcode.util.Puzzle

class Day13(input: Input) : Puzzle() {
    private val inputLines by lazy { input.lines() }
    private val earliestTime by lazy { inputLines[0].toInt() }
    private val schedule1 by lazy { inputLines[1].split(",").filter { it != "x" }.map { it.toInt() }.toSet() }
    private val schedule2 by lazy { inputLines[1].split(",").map { if (it == "x") 1 else it.toLong() } }

    override fun part1() = generateSequence(earliestTime) { it + 1 }
        .mapNotNull { time -> schedule1.firstOrNull { busId -> time % busId == 0 }?.let { time to it } }
        .first()
        .let { (time, busId) -> busId * (time - earliestTime) }

    override fun part2() = schedule2
        .foldIndexed(1L to 1L) { index, (departure, incrementBy), busId ->
            val nextDeparture = findNext(departure + index, incrementBy, busId) - index
            nextDeparture to incrementBy * busId
        }
        .let { (departure, _) -> departure }

    private tailrec fun findNext(departure: Long, incrementBy: Long, busId: Long): Long =
        if (departure % busId == 0L) departure
        else findNext(departure + incrementBy, incrementBy, busId)
}