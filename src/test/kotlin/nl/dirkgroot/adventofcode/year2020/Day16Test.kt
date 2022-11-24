package nl.dirkgroot.adventofcode.year2020

import nl.dirkgroot.adventofcode.util.StringInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day16Test {
    private val example1 =
        """
            class: 1-3 or 5-7
            row: 6-11 or 33-44
            seat: 13-40 or 45-50

            7,1,14

            7,3,47
            40,4,50
            55,2,20
            38,6,12
        """.trimIndent()

    @Test
    fun part1() {
        assertEquals(71, Day16(StringInput(example1)).part1())
    }

    private val example2 =
        """
            departure class: 0-1 or 4-19
            departure row: 0-5 or 8-19
            seat: 0-13 or 16-19

            11,12,13

            3,9,18
            15,1,5
            5,14,9
        """.trimIndent()

    @Test
    fun part2() {
        assertEquals(132L, Day16(StringInput(example2)).part2())
    }
}