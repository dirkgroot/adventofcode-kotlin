package nl.dirkgroot.adventofcode.year2020

import nl.dirkgroot.adventofcode.util.StringInput
import kotlin.test.Test
import kotlin.test.assertEquals

class Day12Test {
    private val exampleInput =
        """
            F10
            N3
            F7
            R90
            F11
        """.trimIndent()

    @Test
    fun part1() {
        assertEquals(25, Day12(StringInput(exampleInput)).part1())
    }

    @Test
    fun part2() {
        assertEquals(286, Day12(StringInput(exampleInput)).part2())
    }
}