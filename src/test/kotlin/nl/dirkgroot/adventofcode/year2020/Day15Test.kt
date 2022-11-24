package nl.dirkgroot.adventofcode.year2020

import nl.dirkgroot.adventofcode.util.StringInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day15Test {
    @Test
    fun part1() {
        assertEquals(436, Day15(StringInput("0,3,6")).part1())
    }

    @Test
    fun part2() {
        assertEquals(175594, Day15(StringInput("0,3,6")).part2())
    }
}