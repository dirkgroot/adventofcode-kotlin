package nl.dricus.adventofcode.year2019

import nl.dricus.adventofcode.util.StringInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day02Test {
    private val input = StringInput(
        "1,9,10,3,2,3,11,0,99,30,40,50"
    )

    @Test
    fun part1() {
        assertEquals(3500, Day02(input, false).part1())
    }

    @Test
    fun part2() {
//        assertEquals(1, Day02(input).part2())
    }
}
