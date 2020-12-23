package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.StringInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day23Test {
    private val example = "389125467"

    @Test
    fun part1() {
        assertEquals("67384529", Day23(StringInput(example)).part1())
    }

    @Test
    fun part2() {
        assertEquals(149245887792L, Day23(StringInput(example)).part2())
    }
}