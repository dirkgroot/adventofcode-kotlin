package nl.dricus.adventofcode.year2019

import nl.dricus.adventofcode.util.Input
import nl.dricus.adventofcode.util.Puzzle

class Day02(input: Input, replaceInput: Boolean = true) : Puzzle() {
    data class Line(val instruction: (Int, Int) -> Int, val input1: Int, val input2: Int)

    private val program = if (replaceInput)
        readProgram(input)
            .mapIndexed { index, s ->
                when (index) {
                    1 -> 12
                    2 -> 2
                    else -> s
                }
            }
    else
        readProgram(input)

    private fun readProgram(input: Input) = input.string()
        .split(",")
        .map(String::toInt)

    private val lines = program
        .chunked(4)
        .takeWhile { it[0] != 99 }
        .associate { it[3] to Line(if (it[0] == 1) Int::plus else Int::times, it[1], it[2]) }

    override fun part1() = valueAt(0)

    private fun valueAt(index: Int): Int =
        lines[index]
            ?.let { it.instruction(valueAt(it.input1), valueAt(it.input2)) }
            ?: program[index]
}
