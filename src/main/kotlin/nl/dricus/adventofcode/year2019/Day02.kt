package nl.dricus.adventofcode.year2019

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import nl.dricus.adventofcode.util.Input
import nl.dricus.adventofcode.util.Puzzle

class Day02(input: Input, private val replaceInput: Boolean = true) : Puzzle() {
    private val program = input.string()
        .split(",")
        .map(String::toInt)

    override fun part1() = if (replaceInput)
        exec(program, 12, 2)
    else
        exec(program, program[1], program[2])

    override fun part2() =
        allNounVerbCombos()
            .first { (noun, verb) -> exec(program, noun, verb) == 19690720 }
            .let { (noun, verb) -> 100 * noun + verb }

    private fun exec(program: List<Int>, noun: Int, verb: Int): Int {
        tailrec fun exec(memory: PersistentList<Int>, ip: Int = 0): Int =
            when (memory[ip]) {
                1 -> exec(memory.set(memory[ip + 3], memory[memory[ip + 1]] + memory[memory[ip + 2]]), ip + 4)
                2 -> exec(memory.set(memory[ip + 3], memory[memory[ip + 1]] * memory[memory[ip + 2]]), ip + 4)
                else -> memory[0]
            }

        return exec(program.toPersistentList().set(1, noun).set(2, verb))
    }

    private fun allNounVerbCombos() = generateSequence(0 to 0) { (noun, verb) ->
        when {
            noun == 100 -> null
            verb == 99 -> noun + 1 to 0
            else -> noun to verb + 1
        }
    }
}
