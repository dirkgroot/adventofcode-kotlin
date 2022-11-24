package nl.dirkgroot.adventofcode.year2020

import nl.dirkgroot.adventofcode.util.StringInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day08Test {
    private val input = StringInput(
        """
            nop +0
            acc +1
            jmp +4
            acc +3
            jmp -3
            acc -99
            acc +1
            jmp -4
            acc +6
        """.trimIndent()
    )

    @Test
    fun part1() {
        assertEquals(5, Day08(input).part1())
    }

    @Test
    fun part2() {
        assertEquals(8, Day08(input).part2())
    }
}