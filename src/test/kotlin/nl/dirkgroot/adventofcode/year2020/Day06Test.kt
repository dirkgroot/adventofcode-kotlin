package nl.dirkgroot.adventofcode.year2020

import nl.dirkgroot.adventofcode.util.StringInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day06Test {
    private val input = StringInput(
        """
            abc

            a
            b
            c

            ab
            ac

            a
            a
            a
            a

            b
        """.trimIndent()
    )

    @Test
    fun part1() {
        assertEquals(11, Day06(input).part1())
    }

    @Test
    fun part2() {
        assertEquals(6, Day06(input).part2())
    }
}