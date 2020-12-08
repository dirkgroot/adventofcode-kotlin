package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.Input
import nl.dricus.adventofcode.util.Puzzle

object Day07 : Puzzle() {
    private val rules by lazy { parseBagRules(Input.lines(2020, 7)) }

    private fun parseBagRules(input: List<String>) = input.map(this::parseBagRule).toMap()

    private fun parseBagRule(rule: String): Pair<String, Map<String, Int>> {
        val (bagColor, bagContentRules) = sanitizeBagRule(rule).split("bagscontain")

        return bagColor to parseBagContentRules(bagContentRules)
    }

    private fun sanitizeBagRule(rule: String) = rule.replace("[,.\\s]+".toRegex(), "")

    private fun parseBagContentRules(bagContentRules: String) = bagContentRules
        .split("bags?".toRegex())
        .filter { it.isNotBlank() && it != "noother" }
        .map { it.takeLastWhile { c -> !c.isDigit() } to it.takeWhile { c -> c.isDigit() }.toInt() }
        .toMap()

    override fun part1() = rules.count { canContainColor(it.key, "shinygold") }

    private fun canContainColor(containerColor: String, bagColor: String): Boolean = rules[containerColor]!!
        .keys.any { color -> color == bagColor || canContainColor(color, bagColor) }

    override fun part2() = bagsNeeded("shinygold")

    private fun bagsNeeded(containerColor: String): Int = rules[containerColor]!!
        .map { (innerBagColor, count) -> count + bagsNeeded(innerBagColor) * count }
        .sum()
}