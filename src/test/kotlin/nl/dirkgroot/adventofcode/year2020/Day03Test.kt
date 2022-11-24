package nl.dirkgroot.adventofcode.year2020

import nl.dirkgroot.adventofcode.util.StringInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day03Test {
    private val input = StringInput(
        """
            ..##.......
            #...#...#..
            .#....#..#.
            ..#.#...#.#
            .#...##..#.
            ..#.##.....
            .#.#.#....#
            .#........#
            #.##...#...
            #...##....#
            .#..#...#.#
        """.trimIndent()
    )

    @Test
    fun part1() {
        assertEquals(7L, Day03(input).part1())
    }

    @Test
    fun part2() {
        assertEquals(336L, Day03(input).part2())
    }
}