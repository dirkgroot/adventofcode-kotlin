package nl.dirkgroot.adventofcode.year2022

import assertk.assertThat
import assertk.assertions.isEqualTo
import nl.dirkgroot.adventofcode.util.ClasspathResourceInput
import nl.dirkgroot.adventofcode.util.StringInput
import org.junit.jupiter.api.Test

class Day03Test {
    private val example =
        """
            vJrwpWtwJgWrhcsFMMfFFhFp
            jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
            PmmdzqPrVvPwwTWBwg
            wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
            ttgJtRGJQctTZtZT
            CrZsJsPPZsGzwwsLwLmpwMDw
        """.trimIndent()

    @Test
    fun `example part1`() {
        assertThat(Day03(StringInput(example)).part1()).isEqualTo(157)
    }

    @Test
    fun `example part2`() {
        assertThat(Day03(StringInput(example)).part2()).isEqualTo(70)
    }

    @Test
    fun part1() {
        val result = Day03(ClasspathResourceInput(2022, 3)).part1()
        println(result)
        assertThat(result).isEqualTo(7903)
    }

    @Test
    fun part2() {
        val result = Day03(ClasspathResourceInput(2022, 3)).part2()
        println(result)
        assertThat(result).isEqualTo(2548)
    }
}