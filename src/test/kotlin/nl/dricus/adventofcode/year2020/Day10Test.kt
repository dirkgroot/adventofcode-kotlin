package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.StringInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day10Test {
    private val exampleInput1 =
        """
            1
            4
            5
            6
            7
            10
            11
            12
            15
            16
            19
        """.trimIndent()

    @Test
    fun part1Example1() {
        assertEquals(35, Day10(StringInput(exampleInput1)).part1())
    }

    @Test
    fun part2Example1() {
        assertEquals(8, Day10(StringInput(exampleInput1)).part2())
    }

    private val exampleInput2 =
        """
            1
            2
            3
            4
            7
            8
            9
            10
            11
            14
            17
            18
            19
            20
            23
            24
            25
            28
            31
            32
            33
            34
            35
            38
            39
            42
            45
            46
            47
            48
            49
        """.trimIndent()

    @Test
    fun part1Example2() {
        assertEquals(220, Day10(StringInput(exampleInput2)).part1())
    }

    @Test
    fun part2Example2() {
        assertEquals(19208, Day10(StringInput(exampleInput2)).part2())
    }
}