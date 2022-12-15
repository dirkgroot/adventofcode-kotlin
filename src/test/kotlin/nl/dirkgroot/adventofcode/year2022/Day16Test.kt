package nl.dirkgroot.adventofcode.year2022

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import nl.dirkgroot.adventofcode.util.input
import nl.dirkgroot.adventofcode.util.invokedWith

private fun solution1(input: String): Long = 0L
private fun solution2(input: String): Long = 0L

//===============================================================================================\\

private const val YEAR = 2022
private const val DAY = 16

class Day16Test : StringSpec({
    "example part 1" { ::solution1 invokedWith exampleInput shouldBe 0L }
    "part 1 solution" { ::solution1 invokedWith input(YEAR, DAY) shouldBe 0L }
    "example part 2" { ::solution2 invokedWith exampleInput shouldBe 0L }
    "part 2 solution" { ::solution2 invokedWith input(YEAR, DAY) shouldBe 0L }
})

private val exampleInput =
    """
    """.trimIndent()
