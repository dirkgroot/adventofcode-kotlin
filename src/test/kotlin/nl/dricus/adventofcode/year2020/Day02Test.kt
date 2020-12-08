package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.StringInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day02Test {
    private val input = StringInput(
        """
            1-3 a: abcde
            1-3 b: cdefg
            2-9 c: ccccccccc
        """.trimIndent()
    )

    @Test
    fun part1() {
        assertEquals(2, Day02(input).part1())
    }

    @Test
    fun part2() {
        assertEquals(1, Day02(input).part2())
    }
}