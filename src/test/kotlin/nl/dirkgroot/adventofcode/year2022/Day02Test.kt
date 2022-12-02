package nl.dirkgroot.adventofcode.year2022

import assertk.assertThat
import assertk.assertions.isEqualTo
import nl.dirkgroot.adventofcode.util.ClasspathResourceInput
import nl.dirkgroot.adventofcode.util.StringInput
import org.junit.jupiter.api.Test

class Day02Test {
    @Test
    fun example1() {
        val score = Day02(
            StringInput(
                """
                    A Y
                    B X
                    C Z
                """.trimIndent()
            )
        ).part1()

        assertThat(score).isEqualTo(15)
    }

    @Test
    fun example2() {
        val score = Day02(
            StringInput(
                """
                    A Y
                    B X
                    C Z
                """.trimIndent()
            )
        ).part2()

        assertThat(score).isEqualTo(12)
    }

    @Test
    fun part1() {
        val score = Day02(ClasspathResourceInput(2022, 2)).part1()
        println(score)
        assertThat(score).isEqualTo(13484L)
    }

    @Test
    fun part2() {
        val score = Day02(ClasspathResourceInput(2022, 2)).part2()
        println(score)
        assertThat(score).isEqualTo(13433L)
    }
}