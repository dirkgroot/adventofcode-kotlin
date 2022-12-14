package nl.dirkgroot.adventofcode.year2022

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import nl.dirkgroot.adventofcode.util.input
import nl.dirkgroot.adventofcode.util.invokedWith
import java.lang.Integer.max
import kotlin.math.min

private fun solution1(input: String) = createCave(input, withFloor = false).solve()
private fun solution2(input: String) = createCave(input, withFloor = true).solve()

private fun createCave(input: String, withFloor: Boolean) =
    Cave(input.lineSequence().map {
        "(\\d+),(\\d+)".toRegex().findAll(it).map { match -> match.groupValues.drop(1) }
            .map { (first, second) -> first.toInt() to second.toInt() }
    }, withFloor)

private class Cave(lines: Sequence<Sequence<Pair<Int, Int>>>, withFloor: Boolean = false) {
    private val rocks = fillRocks(lines)
    private val maxY = rocks.maxOf { (_, y) -> y }.let { if (withFloor) it + 2 else it }
    private val stop = Int.MAX_VALUE to Int.MAX_VALUE

    init {
        if (withFloor) (0..1000).forEach { x -> rocks.add(x to maxY) }
    }

    private fun fillRocks(lines: Sequence<Sequence<Pair<Int, Int>>>) = lines.flatMap { it.windowed(2) }
        .flatMap { (a, b) ->
            sequence {
                for (x in min(a.first, b.first)..max(a.first, b.first))
                    for (y in min(a.second, b.second)..max(a.second, b.second))
                        yield(x to y)
            }
        }.toMutableSet()

    fun solve() = generateSequence { dropSand() }.takeWhile { it }.count()

    private fun dropSand(): Boolean {
        val (x, y) = generateSequence(500 to 0) { (x, y) ->
            val nextY = y + 1
            when {
                !rocks.contains(x to nextY) -> x to nextY
                !rocks.contains(x - 1 to nextY) -> x - 1 to nextY
                !rocks.contains(x + 1 to nextY) -> x + 1 to nextY
                else -> stop
            }
        }.takeWhile { it != stop && it.second <= maxY }.last()

        return if (y < maxY) rocks.add(x to y) else false
    }
}

//===============================================================================================\\

private const val YEAR = 2022
private const val DAY = 14

class Day14Test : StringSpec({
    "example part 1" { ::solution1 invokedWith exampleInput shouldBe 24 }
    "part 1 solution" { ::solution1 invokedWith input(YEAR, DAY) shouldBe 763 }
    "example part 2" { ::solution2 invokedWith exampleInput shouldBe 93 }
    "part 2 solution" { ::solution2 invokedWith input(YEAR, DAY) shouldBe 23921 }
})

private val exampleInput =
    """
        498,4 -> 498,6 -> 496,6
        503,4 -> 502,4 -> 502,9 -> 494,9
    """.trimIndent()
