package nl.dirkgroot.adventofcode.year2022

import nl.dirkgroot.adventofcode.util.Input
import nl.dirkgroot.adventofcode.util.Puzzle

class Day01(input: Input) : Puzzle() {
    private val calories = input.string().split("\n\n")
        .map { elf -> elf.split("\n").map { calories -> calories.toLong() } }
        .map { elf -> elf.sum() }

    override fun part1() = calories.max()
    override fun part2() = calories.sortedDescending().take(3).sum()
}
