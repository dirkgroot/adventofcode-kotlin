package nl.dirkgroot.adventofcode.year2022

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import nl.dirkgroot.adventofcode.util.input
import nl.dirkgroot.adventofcode.util.invokedWith

private fun solution1(input: String) = parse(input).let { (map, path) ->
    val width = map[0].size
    val height = map.size
    var row = 0
    var col = map[0].indexOfFirst { it != ' ' }
    var facing = Facing.RIGHT

    path.forEach { instr ->
        when (instr) {
            is Instr.Move -> {
                val (dr, dc) = when (facing) {
                    Facing.RIGHT -> 0 to 1
                    Facing.DOWN -> 1 to 0
                    Facing.LEFT -> 0 to -1
                    Facing.UP -> -1 to 0
                }

                val (newR, newC) = generateSequence(row to col) { (r, c) -> (r + dr + height) % height to (c + dc + width) % width }
                    .filter { (r, c) -> map[r][c] != ' ' }
                    .take(instr.steps + 1)
                    .takeWhile { (r, c) -> map[r][c] != '#' }
                    .onEach { (r, c) -> map[r][c] = facing.char }
                    .last()
                row = newR
                col = newC
            }

            is Instr.Turn -> {
                facing = when (instr.direction) {
                    "R" -> facing.rotateRight()
                    "L" -> facing.rotateLeft()
                    else -> throw IllegalStateException()
                }
            }
        }
    }

    (row + 1) * 1000 + (col + 1) * 4 + facing.value
}

private fun solution2(input: String): Long = 0L

private fun parse(input: String) = input.split("\n\n").let { (map, path) ->
    val mapLines = map.lines()
    val mapWidth = mapLines.maxOf { it.length }

    val parsedMap = mapLines
        .map { line -> Array(mapWidth) { line.getOrElse(it) { ' ' } } }
        .toTypedArray<Array<Char>>()

    val parsedPath = "\\d+|[RL]".toRegex().findAll(path)
        .map { it.value }
        .map { if (it[0].isDigit()) Instr.Move(it.toInt()) else Instr.Turn(it) }

    parsedMap to parsedPath
}

private sealed interface Instr {
    class Move(val steps: Int) : Instr
    class Turn(val direction: String) : Instr
}

private enum class Facing(val char: Char, val value: Int) {
    RIGHT('>', 0) {
        override fun rotateLeft() = UP
        override fun rotateRight() = DOWN
    },
    DOWN('v', 1) {
        override fun rotateLeft() = RIGHT
        override fun rotateRight() = LEFT
    },
    LEFT('<', 2) {
        override fun rotateLeft() = DOWN
        override fun rotateRight() = UP
    },
    UP('^', 3) {
        override fun rotateLeft() = LEFT
        override fun rotateRight() = RIGHT
    };

    abstract fun rotateLeft(): Facing
    abstract fun rotateRight(): Facing
}

private fun printMap(map: Array<Array<Char>>) {
    map.forEach { row ->
        row.forEach { print(it) }
        println()
    }
}

//===============================================================================================\\

private const val YEAR = 2022
private const val DAY = 22

class Day22Test : StringSpec({
    "example part 1" { ::solution1 invokedWith exampleInput shouldBe 6032 }
    "part 1 solution" { ::solution1 invokedWith input(YEAR, DAY) shouldBe 88226 }
    "example part 2" { ::solution2 invokedWith exampleInput shouldBe 5031 }
    "part 2 solution" { ::solution2 invokedWith input(YEAR, DAY) shouldBe 0L }
})

private val exampleInput =
    """
                ...#
                .#..
                #...
                ....
        ...#.......#
        ........#...
        ..#....#....
        ..........#.
                ...#....
                .....#..
                .#......
                ......#.
        
        10R5L5R10L4R5L5
    """.trimIndent()
