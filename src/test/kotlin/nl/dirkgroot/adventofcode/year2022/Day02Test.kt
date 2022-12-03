package nl.dirkgroot.adventofcode.year2022

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import nl.dirkgroot.adventofcode.util.input
import nl.dirkgroot.adventofcode.util.invokedWith

private const val YEAR = 2022
private const val DAY = 2

private fun solution1(input: String) = input.lineSequence().totalScore(0)
private fun solution2(input: String) = input.lineSequence().totalScore(1)

private fun Sequence<String>.totalScore(part: Int) = fold(0L) { acc, line -> acc + scores.getValue(line)[part] }

private val scores = mapOf(
    "A X" to arrayOf(4L, 3L), "A Y" to arrayOf(8L, 4L), "A Z" to arrayOf(3L, 8L),
    "B X" to arrayOf(1L, 1L), "B Y" to arrayOf(5L, 5L), "B Z" to arrayOf(9L, 9L),
    "C X" to arrayOf(7L, 2L), "C Y" to arrayOf(2L, 6L), "C Z" to arrayOf(6L, 7L),
)

class Day02Test : StringSpec({
    "example part 1" { ::solution1 invokedWith exampleInput shouldBe 15L }
    "part 1 solution" { ::solution1 invokedWith input(YEAR, DAY) shouldBe 13484L }
    "example part 2" { ::solution2 invokedWith exampleInput shouldBe 12L }
    "part 2 solution" { ::solution2 invokedWith input(YEAR, DAY) shouldBe 13433L }
})

private val exampleInput =
    """
        A Y
        B X
        C Z
    """.trimIndent()
