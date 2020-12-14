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

    private fun parseMask(mask: String) = mask.drop(7).let { maskValue ->
        val xMask = maskValue.replace('1', '0').replace('X', '1')
        val nMask = maskValue.replace('X', '0')

        SetMask(xMask.toLong(2), nMask.toLong(2))
    }

    private fun parseMem(mem: String) = "mem\\[(\\d+)] = (\\d+)".toRegex().matchEntire(mem)!!.let { match ->
        SetMem(match.groupValues[1].toLong(), match.groupValues[2].toLong())
    }

    override fun part1() = sumOfMemory(::setMemWithMaskedValue)

    private fun setMemWithMaskedValue(setMask: SetMask, setMem: SetMem) =
        sequenceOf(SetMem(setMem.address, (setMem.value and setMask.xMask) or setMask.nMask))

    override fun part2() = sumOfMemory(::setMemPermutations)

    private fun setMemPermutations(mask: SetMask, instruction: SetMem) =
        (instruction.address and mask.xMask.inv())
            .let { fixedBits -> addressPermutations(mask).map { address -> fixedBits or mask.nMask or address } }
            .map { SetMem(it, instruction.value) }

    private fun addressPermutations(mask: SetMask) =
        generateSequence(mask.xMask) { prev -> if (prev > 0) (prev - 1) and mask.xMask else null }

    private fun sumOfMemory(setMemInstructions: (SetMask, SetMem) -> Sequence<SetMem>) = program
        .runningFold(SetMask(0, 0) to sequenceOf<SetMem>()) { (mask, previous), next ->
            when (next) {
                is SetMask -> next to previous
                is SetMem -> mask to setMemInstructions(mask, next)
                else -> throw IllegalStateException()
            }
        }
        .flatMap { (_, instructions) -> instructions }
        .groupBy { it.address }
        .entries.map { (_, instructions) -> instructions.last().value }
        .sum()
}