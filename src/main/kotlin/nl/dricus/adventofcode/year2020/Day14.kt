package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.Input
import nl.dricus.adventofcode.util.Puzzle

class Day14(input: Input) : Puzzle() {
    private interface Instruction
    private class SetMask(val xMask: Long, val nMask: Long) : Instruction
    private class SetMem(val address: Long, val value: Long) : Instruction

    private val program by lazy {
        input.lines().map {
            when {
                it.startsWith("mask") -> parseMask(it)
                else -> parseMem(it)
            }
        }
    }

    private fun parseMask(mask: String): SetMask {
        val maskValue = mask.drop(7)
        val xMask = maskValue.replace('1', '0').replace('X', '1')
        val nMask = maskValue.replace('X', '0')

        return SetMask(xMask.toLong(2), nMask.toLong(2))
    }

    private fun parseMem(mem: String): Instruction {
        val match = "mem\\[(\\d+)] = (\\d+)".toRegex().matchEntire(mem)!!

        return SetMem(match.groupValues[1].toLong(), match.groupValues[2].toLong())
    }

    override fun part1(): Long {
        var mask = SetMask(0, 0)
        val memory = mutableMapOf<Long, Long>()

        program.forEach { instruction ->
            when (instruction) {
                is SetMask -> mask = instruction
                is SetMem -> memory[instruction.address] = applyValueMask(mask.xMask, mask.nMask, instruction.value)
            }
        }

        return memory.values.sum()
    }

    private fun applyValueMask(xMask: Long, nMask: Long, value: Long) = (value and xMask) or nMask

    override fun part2(): Long {
        var mask = SetMask(0, 0)
        val memory = mutableMapOf<Long, Long>()

        program.forEach { instruction ->
            when (instruction) {
                is SetMask -> mask = instruction
                is SetMem -> getAddressPermutations(mask.xMask, mask.nMask, instruction.address).forEach {
                    memory[it] = instruction.value
                }
            }
        }

        return memory.values.sum()
    }

    private fun getAddressPermutations(xMask: Long, nMask: Long, address: Long): List<Long> {
        val fixedBits = address and xMask.inv()
        val result = mutableListOf<Long>()
        var orPermutation = xMask

        do {
            result.add(fixedBits or nMask or orPermutation)
            orPermutation = (orPermutation - 1) and xMask
        } while (orPermutation != xMask)

        return result
    }
}