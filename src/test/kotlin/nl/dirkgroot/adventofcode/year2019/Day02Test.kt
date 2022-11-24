package nl.dirkgroot.adventofcode.year2019

import nl.dirkgroot.adventofcode.util.StringInput
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
}
