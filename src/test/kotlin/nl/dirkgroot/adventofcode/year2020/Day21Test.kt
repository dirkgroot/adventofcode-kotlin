package nl.dirkgroot.adventofcode.year2020

import nl.dirkgroot.adventofcode.util.StringInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day21Test {
    private val example =
        """
            mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
            trh fvjkl sbzzf mxmxvkd (contains dairy)
            sqjhc fvjkl (contains soy)
            sqjhc mxmxvkd sbzzf (contains fish)
        """.trimIndent()

    @Test
    fun part1() {
        assertEquals(5, Day21(StringInput(example)).part1())
    }

    @Test
    fun part2() {
        assertEquals("mxmxvkd,sqjhc,fvjkl", Day21(StringInput(example)).part2())
    }
}