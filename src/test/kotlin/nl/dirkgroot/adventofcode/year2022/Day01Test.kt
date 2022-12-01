package nl.dirkgroot.adventofcode.year2022

import assertk.assertThat
import assertk.assertions.isEqualTo
import nl.dirkgroot.adventofcode.util.StringInput
import org.junit.jupiter.api.Test

class Day01Test {
    @Test
    fun `example input`() {
        val day01 = Day01(
            StringInput(
                """
                    1000
                    2000
                    3000
    
                    4000
    
                    5000
                    6000
    
                    7000
                    8000
                    9000
    
                    10000
                """.trimIndent()
            )
        )

        assertThat(day01.part1()).isEqualTo(24000L)
        assertThat(day01.part2()).isEqualTo(45000L)
    }
}