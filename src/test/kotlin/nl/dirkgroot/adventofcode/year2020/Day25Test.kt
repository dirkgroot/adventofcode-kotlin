package nl.dirkgroot.adventofcode.year2020

import nl.dirkgroot.adventofcode.util.StringInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day25Test {
    private val example =
        """
            5764801
            17807724
        """.trimIndent()

    @Test
    fun part1() {
        assertEquals(14897079L, Day25(StringInput(example)).part1())
    }
}