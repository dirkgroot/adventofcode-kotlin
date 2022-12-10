package nl.dirkgroot.adventofcode.year2022

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import nl.dirkgroot.adventofcode.util.input
import nl.dirkgroot.adventofcode.util.invokedWith

private fun solution1(input: String) = run(input)
    .mapIndexed { index, x -> (index + 1) * x }
    .filterIndexed { index, _ -> (index + 1) in 20..220 step 40 }.sum()

private fun solution2(input: String) = run(input)
    .take(240).chunked(40)
    .map { row -> row.mapIndexed { index, x -> if (index in x - 1..x + 1) '#' else '.' } }
    .joinToString("\n") { it.joinToString("") }

private fun run(code: String) = code.lineSequence()
    .map { it.split(" ") }
    .flatMap { cycles(it) }
    .runningFold(1L) { x, add -> x + add }

private fun cycles(instruction: List<String>) =
    when (instruction[0]) {
        "noop" -> listOf(0L)
        "addx" -> listOf(0L, instruction[1].toLong())
        else -> throw IllegalStateException()
    }

//===============================================================================================\\

private const val YEAR = 2022
private const val DAY = 10

class Day10Test : StringSpec({
    "example part 1" { ::solution1 invokedWith exampleInput shouldBe 13140L }
    "part 1 solution" { ::solution1 invokedWith input(YEAR, DAY) shouldBe 13680L }
    "example part 2" {
        ::solution2 invokedWith exampleInput shouldBe """
            ##..##..##..##..##..##..##..##..##..##..
            ###...###...###...###...###...###...###.
            ####....####....####....####....####....
            #####.....#####.....#####.....#####.....
            ######......######......######......####
            #######.......#######.......#######.....
        """.trimIndent()
    }
    "part 2 solution" {
        ::solution2 invokedWith input(YEAR, DAY) shouldBe """
            ###..####..##..###..#..#.###..####.###..
            #..#....#.#..#.#..#.#.#..#..#.#....#..#.
            #..#...#..#....#..#.##...#..#.###..###..
            ###...#...#.##.###..#.#..###..#....#..#.
            #....#....#..#.#....#.#..#....#....#..#.
            #....####..###.#....#..#.#....####.###..
        """.trimIndent()
    }
})

private val exampleInput =
    """
        addx 15
        addx -11
        addx 6
        addx -3
        addx 5
        addx -1
        addx -8
        addx 13
        addx 4
        noop
        addx -1
        addx 5
        addx -1
        addx 5
        addx -1
        addx 5
        addx -1
        addx 5
        addx -1
        addx -35
        addx 1
        addx 24
        addx -19
        addx 1
        addx 16
        addx -11
        noop
        noop
        addx 21
        addx -15
        noop
        noop
        addx -3
        addx 9
        addx 1
        addx -3
        addx 8
        addx 1
        addx 5
        noop
        noop
        noop
        noop
        noop
        addx -36
        noop
        addx 1
        addx 7
        noop
        noop
        noop
        addx 2
        addx 6
        noop
        noop
        noop
        noop
        noop
        addx 1
        noop
        noop
        addx 7
        addx 1
        noop
        addx -13
        addx 13
        addx 7
        noop
        addx 1
        addx -33
        noop
        noop
        noop
        addx 2
        noop
        noop
        noop
        addx 8
        noop
        addx -1
        addx 2
        addx 1
        noop
        addx 17
        addx -9
        addx 1
        addx 1
        addx -3
        addx 11
        noop
        noop
        addx 1
        noop
        addx 1
        noop
        noop
        addx -13
        addx -19
        addx 1
        addx 3
        addx 26
        addx -30
        addx 12
        addx -1
        addx 3
        addx 1
        noop
        noop
        noop
        addx -9
        addx 18
        addx 1
        addx 2
        noop
        noop
        addx 9
        noop
        noop
        noop
        addx -1
        addx 2
        addx -37
        addx 1
        addx 3
        noop
        addx 15
        addx -21
        addx 22
        addx -6
        addx 1
        noop
        addx 2
        addx 1
        noop
        addx -10
        noop
        noop
        addx 20
        addx 1
        addx 2
        addx 2
        addx -6
        addx -11
        noop
        noop
        noop
    """.trimIndent()
