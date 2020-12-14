package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.StringInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day14Test {
    private val example1 =
        """
            mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
            mem[8] = 11
            mem[7] = 101
            mem[8] = 0
        """.trimIndent()

    @Test
    fun part1() {
        assertEquals(165L, Day14(StringInput(example1)).part1())
    }

    private val example2 =
        """
            mask = 000000000000000000000000000000X1001X
            mem[42] = 100
            mask = 00000000000000000000000000000000X0XX
            mem[26] = 1
        """.trimIndent()

    @Test
    fun part2() {
        assertEquals(208L, Day14(StringInput(example2)).part2())
    }
}