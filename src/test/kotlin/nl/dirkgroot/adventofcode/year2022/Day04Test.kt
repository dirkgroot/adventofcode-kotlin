package nl.dirkgroot.adventofcode.year2022

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import nl.dirkgroot.adventofcode.util.input
import nl.dirkgroot.adventofcode.util.invokedWith

private fun solution1(input: String) = input.parseRanges().countCompleteOverlaps()

private fun Sequence<Pair<LongRange, LongRange>>.countCompleteOverlaps() =
    count { (a, b) -> (a.first <= b.first && a.last >= b.last) || (b.first <= a.first && b.last >= a.last) }

private fun solution2(input: String) = input.parseRanges().countPartialOverlaps()

private fun Sequence<Pair<LongRange, LongRange>>.countPartialOverlaps() =
    count { (a, b) -> (a.first <= b.first && a.last >= b.first) || (b.first <= a.first && b.last >= a.first) }

private fun String.parseRanges() = lineSequence()
    .map { it.split(",") }
    .map { (a, b) -> a.toRange() to b.toRange() }

private fun String.toRange() = split("-")
    .map { it.toLong() }
    .let { (a, b) -> a..b }

//===============================================================================================\\

private const val YEAR = 2022
private const val DAY = 4

class Day04Test : StringSpec({
    "example part 1" { ::solution1 invokedWith exampleInput shouldBe 2 }
    "part 1 solution" { ::solution1 invokedWith input(YEAR, DAY) shouldBe 532 }
    "example part 2" { ::solution2 invokedWith exampleInput shouldBe 4 }
    "part 2 solution" { ::solution2 invokedWith input(YEAR, DAY) shouldBe 854 }
})

private val exampleInput =
    """
        2-4,6-8
        2-3,4-5
        5-7,7-9
        2-8,3-7
        6-6,4-6
        2-6,4-8
    """.trimIndent()
