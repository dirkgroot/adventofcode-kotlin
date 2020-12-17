package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.StringInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day17Test {
    private val example =
        """
            .#.
            ..#
            ###
        """.trimIndent()

    @Test
    fun part1() {
        assertEquals(112, Day17(StringInput(example)).part1())
    }

    @Test
    fun part2() {
        assertEquals(848, Day17(StringInput(example)).part2())
    }
}