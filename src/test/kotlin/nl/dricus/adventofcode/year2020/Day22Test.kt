package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.StringInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day22Test {
    private val example =
        """
            Player 1:
            9
            2
            6
            3
            1

            Player 2:
            5
            8
            4
            7
            10
        """.trimIndent()

    @Test
    fun part1() {
        assertEquals(306, Day22(StringInput(example)).part1())
    }

    @Test
    fun part2() {
        assertEquals(291, Day22(StringInput(example)).part2())
    }
}