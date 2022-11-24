package nl.dirkgroot.adventofcode.year2019

import nl.dirkgroot.adventofcode.util.StringInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day01Test {
    @Test
    fun part1() {
        assertEquals(2, Day01(StringInput("12")).part1())
        assertEquals(2, Day01(StringInput("14")).part1())
        assertEquals(33583, Day01(StringInput("100756")).part1())
    }

    @Test
    fun part2() {
        assertEquals(2, Day01(StringInput("12")).part2())
        assertEquals(966, Day01(StringInput("1969")).part2())
        assertEquals(50346, Day01(StringInput("100756")).part2())
    }
}
