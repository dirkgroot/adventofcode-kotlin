package nl.dirkgroot.adventofcode.year2022

import nl.dirkgroot.adventofcode.util.Input
import nl.dirkgroot.adventofcode.util.Puzzle

class Day03(input: Input) : Puzzle() {
    private val rucksacks = input.lines()

    override fun part1() = rucksacks.map { rucksack ->
        rucksack.windowed(rucksack.length / 2, rucksack.length / 2)
            .map { it.toSet() }
            .reduce { acc, half -> acc.intersect(half) }
    }.sumOf { priority(it.single()) }

    override fun part2() = rucksacks.windowed(3, 3).map { group ->
        group.map { rucksack -> rucksack.toSet() }
            .reduce { acc, rucksack -> acc.intersect(rucksack) }
    }.sumOf { priority(it.single()) }

    private fun priority(item: Char) = if (item >= 'a') item - 'a' + 1 else item - 'A' + 27
}