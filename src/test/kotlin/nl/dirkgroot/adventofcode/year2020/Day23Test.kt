package nl.dirkgroot.adventofcode.year2020

import nl.dirkgroot.adventofcode.util.StringInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.time.ExperimentalTime

class Day23Test {
    private val example = "389125467"

    @Test
    fun part1() {
        assertEquals("67384529", Day23(StringInput(example)).part1())
    }

    @ExperimentalTime
    @Test
    fun part2() {
        assertEquals(149245887792L, Day23(StringInput(example)).part2())
    }
}