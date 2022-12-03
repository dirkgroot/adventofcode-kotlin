package nl.dirkgroot.adventofcode.year2022

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import nl.dirkgroot.adventofcode.util.*

private const val YEAR = 2022
private const val DAY = 3

private fun solution1(input: String) = input.lineSequence()
    .splitCompartments()
    .totalPriorityOfOverlappingItems()

private fun Sequence<String>.splitCompartments() = this
    .map { it.chunked(it.length / 2) }
    .map { it.map { compartment -> compartment.toSet() } }

private fun solution2(input: String) = input.lineSequence()
    .splitGroups()
    .totalPriorityOfOverlappingItems()

private fun Sequence<String>.splitGroups() = this
    .chunked(3)
    .map { it.map { rucksack -> rucksack.toSet() } }

private fun Sequence<List<Set<Char>>>.totalPriorityOfOverlappingItems() = this
    .map { it.reduce { acc, rucksack -> acc.intersect(rucksack) } }
    .map { it.single() }
    .sumOf { if (it >= 'a') it - 'a' + 1 else it - 'A' + 27 }

class Day03Test : StringSpec({
    "example part 1" { ::solution1 invokedWith exampleInput shouldBe 157 }
    "part 1 solution" { ::solution1 invokedWith input(YEAR, DAY) shouldBe 7903 }
    "example part 2" { ::solution2 invokedWith exampleInput shouldBe 70 }
    "part 2 solution" { ::solution2 invokedWith input(YEAR, DAY) shouldBe 2548 }
})

private val exampleInput =
    """
        vJrwpWtwJgWrhcsFMMfFFhFp
        jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
        PmmdzqPrVvPwwTWBwg
        wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
        ttgJtRGJQctTZtZT
        CrZsJsPPZsGzwwsLwLmpwMDw
    """.trimIndent()
