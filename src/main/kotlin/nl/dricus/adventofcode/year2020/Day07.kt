package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.Input
import nl.dricus.adventofcode.util.Puzzle

object Day07 : Puzzle() {
    private val rules by lazy {
        Input.lines(2020, 7)
            .map { rule ->
                val normalized = rule.replace("[,.\\s]+".toRegex(), "")
                    .split("bagscontain")
                val bagColor = normalized[0]
                val bagRules = normalized[1].split("bags?".toRegex())
                    .filter { it.isNotBlank() && it != "noother" }
                    .map { it.takeLastWhile { c -> !c.isDigit() } to it.takeWhile { c -> c.isDigit() }.toInt() }
                    .toMap()

                bagColor to bagRules
            }
            .toMap()
    }

    override fun part1() = rules
        .count { canContainColor(it.key, "shinygold") }

    private fun canContainColor(containerColor: String, bagColor: String): Boolean = rules[containerColor]!!
        .keys.any { color ->
            color == bagColor || canContainColor(color, bagColor)
        }

    override fun part2() = bagsNeeded("shinygold")

    private fun bagsNeeded(containerColor: String): Int = rules[containerColor]!!
        .map { (innerBagColor, count) ->
            count + bagsNeeded(innerBagColor) * count
        }
        .sum()
}