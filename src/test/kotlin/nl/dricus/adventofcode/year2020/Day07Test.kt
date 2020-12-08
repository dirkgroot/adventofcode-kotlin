package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.StringInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day07Test {
    private val input = StringInput(
        """
            light red bags contain 1 bright white bag, 2 muted yellow bags.
            dark orange bags contain 3 bright white bags, 4 muted yellow bags.
            bright white bags contain 1 shiny gold bag.
            muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
            shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
            dark olive bags contain 3 faded blue bags, 4 dotted black bags.
            vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
            faded blue bags contain no other bags.
            dotted black bags contain no other bags.
        """.trimIndent()
    )

    @Test
    fun part1() {
        assertEquals(4, Day07(input).part1())
    }

    @Test
    fun part2() {
        assertEquals(32, Day07(input).part2())
    }
}