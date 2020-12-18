package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.StringInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day18Test {
    private val example =
        """
            1 + 2 * 3 + 4 * 5 + 6
            2 * 3 + (4 * 5)
            5 + (8 * 3 + 9 + 3 * 4 * 3)
            5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))
            ((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2
        """.trimIndent()

    @Test
    fun part1() {
        assertEquals(26L, Day18(StringInput("2 * 3 + (4 * 5)")).part1())
        assertEquals(437L, Day18(StringInput("5 + (8 * 3 + 9 + 3 * 4 * 3)")).part1())
        assertEquals(12240L, Day18(StringInput("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))")).part1())
        assertEquals(13632L, Day18(StringInput("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2")).part1())
        assertEquals(26406L, Day18(StringInput(example)).part1())
    }

    @Test
    fun part2() {
        assertEquals(231L, Day18(StringInput("1 + 2 * 3 + 4 * 5 + 6")).part2())
    }
}