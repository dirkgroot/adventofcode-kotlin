package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.StringInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day11Test {
    private val example =
        """
            L.LL.LL.LL
            LLLLLLL.LL
            L.L.L..L..
            LLLL.LL.LL
            L.LL.LL.LL
            L.LLLLL.LL
            ..L.L.....
            LLLLLLLLLL
            L.LLLLLL.L
            L.LLLLL.LL
        """.trimIndent()

    @Test
    fun part1() {
        assertEquals(37, Day11(StringInput(example)).part1())
    }

    @Test
    fun part2() {
        assertEquals(26, Day11(StringInput(example)).part2())
    }
}