package nl.dirkgroot.adventofcode.year2022

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import nl.dirkgroot.adventofcode.util.input
import nl.dirkgroot.adventofcode.util.invokedWith

private const val YEAR = 2022
private const val DAY = 1

private fun solution1(input: String) = calories(input).max()
private fun solution2(input: String) = calories(input).sortedDescending().take(3).sum()

private fun calories(input: String) = input.split("\n\n")
    .map { elf -> elf.split("\n").map { calories -> calories.toLong() } }
    .map { elf -> elf.sum() }

class Day01Test : StringSpec({
    "example part 1" { ::solution1 invokedWith exampleInput shouldBe 24000L }
    "part 1 solution" { ::solution1 invokedWith input(YEAR, DAY) shouldBe 69836L }
    "example part 2" { ::solution2 invokedWith exampleInput shouldBe 45000L }
    "part 2 solution" { ::solution2 invokedWith input(YEAR, DAY) shouldBe 207968L }
})

private val exampleInput =
    """
        1000
        2000
        3000
        
        4000
        
        5000
        6000
        
        7000
        8000
        9000
        
        10000
    """.trimIndent()
