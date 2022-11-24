package nl.dirkgroot.adventofcode.year2020

import nl.dirkgroot.adventofcode.util.StringInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day09Test {
    private val input = StringInput(
        """
            35
            20
            15
            25
            47
            40
            62
            55
            65
            95
            102
            117
            150
            182
            127
            219
            299
            277
            309
            576
        """.trimIndent()
    )

    @Test
    fun part1() {
        assertEquals(127L, Day09(input, 5, 127L).part1())
    }

    @Test
    fun part2() {
        assertEquals(62L, Day09(input, 5, 127L).part2())
    }

    @Test
    fun bogoPart2() {
        assertEquals(62L, Day09(input, 5, 127L).part2())
        assertEquals(4830226L, Day09(nl.dirkgroot.adventofcode.util.ClasspathResourceInput(2020, 9), 25, 36845998L).bogoPart2())
    }
}