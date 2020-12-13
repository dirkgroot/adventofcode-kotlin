package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.StringInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day13Test {
    private val exampleInput =
        """
            939
            7,13,x,x,59,x,31,19
        """.trimIndent()

    @Test
    fun part1() {
        assertEquals(295, Day13(StringInput(exampleInput)).part1())
    }

    @Test
    fun part2() {
        assertEquals(7L, Day13(StringInput("0\n7")).part2())
        assertEquals(77L, Day13(StringInput("0\n7,13")).part2())
        assertEquals(350L, Day13(StringInput("0\n7,13,8")).part2())
        assertEquals(1806L, Day13(StringInput("0\n7,13,8,3")).part2())
        assertEquals(1068781L, Day13(StringInput(exampleInput)).part2())
        assertEquals(102L, Day13(StringInput("0\n17,x,13")).part2())
        assertEquals(3417L, Day13(StringInput("0\n17,x,13,19")).part2())
        assertEquals(754018L, Day13(StringInput("0\n67,7,59,61")).part2())
    }
}