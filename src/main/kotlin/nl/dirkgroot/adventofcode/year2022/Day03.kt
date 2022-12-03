package nl.dirkgroot.adventofcode.year2022

import nl.dirkgroot.adventofcode.util.Input
import nl.dirkgroot.adventofcode.util.Puzzle

class Day03(input: Input) : Puzzle() {
    private val rucksacks = input.lines()

    override fun part1() = rucksacks
        .map { rucksack -> rucksack.chunked(rucksack.length / 2).map { it.toSet() } }
        .let { overlappingItems -> sumOfPriorities(overlappingItems) }

    override fun part2() = rucksacks.chunked(3)
        .map { group -> group.map { rucksack -> rucksack.toSet() } }
        .let { overlappingItems -> sumOfPriorities(overlappingItems) }

    private fun sumOfPriorities(overlappingItems: List<List<Set<Char>>>) = overlappingItems
        .map { it.reduce { acc, rucksack -> acc.intersect(rucksack) }.single() }
        .sumOf { if (it >= 'a') it - 'a' + 1 else it - 'A' + 27 }
}