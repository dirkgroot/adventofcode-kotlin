package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.StringInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day01Test {
    private val input = StringInput(
        """
            1721
            979
            366
            299
            675
            1456
        """.trimIndent()
    )

    @Test
    fun part1() {
        assertEquals(514579, Day01(input).part1())
    }

    @Test
    fun part2() {
        assertEquals(241861950, Day01(input).part2())
    }
}