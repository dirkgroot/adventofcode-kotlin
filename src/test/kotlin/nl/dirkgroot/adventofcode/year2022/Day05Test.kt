package nl.dirkgroot.adventofcode.year2022

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import nl.dirkgroot.adventofcode.util.input
import nl.dirkgroot.adventofcode.util.invokedWith

private fun solution1(input: String) = parseInput(input).let { (stacks, procedure) ->
    procedure.forEach { (number, from, to) ->
        repeat(number) {
            val crate = stacks[from].removeFirst()
            stacks[to].add(0, crate)
        }
    }
    stacks.joinToString("") { it.first().toString() }
}

private fun solution2(input: String) = parseInput(input).let { (stacks, procedure) ->
    procedure.forEach { (number, from, to) ->
        val crates = stacks[from].take(number)
        repeat(number) { stacks[from].removeFirst() }
        stacks[to].addAll(0, crates)
    }
    stacks.joinToString("") { it.first().toString() }
}

private fun parseInput(input: String) = input.split("(\\s+\\d+\\s*)+\\n\\n".toRegex()).let { (crates, procedure) ->
    val stackLines = crates.lines().map {
        it.chunked(4).map { c -> c.replace("\\[|]|\\s+".toRegex(), "") }
    }
    val stacks = stackLines.fold(stackLines.last().indices.map { mutableListOf<Char>() }) { acc, line ->
        line.mapIndexed { index, c -> index to c }.filter { (_, c) -> c.isNotBlank() }
            .forEach { (index, c) -> acc.getOrNull(index)?.add(c[0]) }
        acc
    }
    stacks to procedure.lines().map {
        "move (\\d+) from (\\d+) to (\\d+)".toRegex().matchEntire(it)?.let { match ->
            arrayOf(match.groupValues[1].toInt(), match.groupValues[2].toInt() - 1, match.groupValues[3].toInt() - 1)
        } ?: throw IllegalStateException()
    }
}

//===============================================================================================\\

private const val YEAR = 2022
private const val DAY = 5

class Day05Test : StringSpec({
    "parse input" {
        val (stacks, procedure) = parseInput(exampleInput)
        stacks shouldContainExactly listOf(
            listOf('N', 'Z'),
            listOf('D', 'C', 'M'),
            listOf('P'),
        )
        procedure shouldContainExactly listOf(
            arrayOf(1, 1, 0),
            arrayOf(3, 0, 2),
            arrayOf(2, 1, 0),
            arrayOf(1, 0, 1),
        )
    }
    "example part 1" { ::solution1 invokedWith exampleInput shouldBe "CMZ" }
    "part 1 solution" { ::solution1 invokedWith input(YEAR, DAY) shouldBe "FJSRQCFTN" }
    "example part 2" { ::solution2 invokedWith exampleInput shouldBe "MCD" }
    "part 2 solution" { ::solution2 invokedWith input(YEAR, DAY) shouldBe "CJVLJQPHS" }
})

private val exampleInput =
    """
            [D]    
        [N] [C]    
        [Z] [M] [P]
         1   2   3 
        
        move 1 from 2 to 1
        move 3 from 1 to 3
        move 2 from 2 to 1
        move 1 from 1 to 2
    """.trimIndent()
