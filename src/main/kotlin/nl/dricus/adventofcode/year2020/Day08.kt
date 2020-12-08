package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.Input
import nl.dricus.adventofcode.util.Puzzle
import java.lang.IllegalStateException

object Day08 : Puzzle() {
    private val program by lazy { parseProgram(Input.lines(2020, 8)) }

    private fun parseProgram(code: List<String>) = code.map {
        val tokens = it.split(" ")
        tokens[0] to tokens[1].toInt()
    }

    override fun part1() = run().second

    override fun part2() = program.asSequence().withIndex()
        .filter { it.value.first in setOf("nop", "jmp") }
        .map { run(it.index) }
        .filter { (isLoop, _) -> !isLoop }
        .map { (_, acc) -> acc }
        .first()

    private tailrec fun run(
        flip: Int = -1, ip: Int = 0, acc: Int = 0, visited: Set<Int> = emptySet()
    ): Pair<Boolean, Int> =
        when {
            ip >= program.size -> false to acc
            visited.contains(ip) -> true to acc
            else -> {
                val (instruction, argument) = program[ip]
                when (instruction to (ip == flip)) {
                    "acc" to false -> run(flip, ip + 1, acc + argument, visited + ip)
                    "jmp" to false -> run(flip, ip + argument, acc, visited + ip)
                    "nop" to true -> run(flip, ip + argument, acc, visited + ip)
                    "nop" to false -> run(flip, ip + 1, acc, visited + ip)
                    "jmp" to true -> run(flip, ip + 1, acc, visited + ip)
                    else -> throw IllegalStateException()
                }
            }
        }
}