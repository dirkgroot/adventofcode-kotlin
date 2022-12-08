package nl.dirkgroot.adventofcode.year2022

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import nl.dirkgroot.adventofcode.util.input
import nl.dirkgroot.adventofcode.util.invokedWith

private fun solution1(input: String): Int {
    val grid = createGrid(input)
    val width = grid[0].size
    val height = grid.size
    val visibility = List(height) { BooleanArray(width) { false } }

    for (y in grid.indices) {
        var maxHeight = -1
        for (x in grid[y].indices) {
            if (grid[y][x] > maxHeight) {
                maxHeight = grid[y][x]
                visibility[y][x] = true
            }
        }
    }
    for (y in grid.indices) {
        var maxHeight = -1
        for (x in grid[y].indices.reversed()) {
            if (grid[y][x] > maxHeight) {
                maxHeight = grid[y][x]
                visibility[y][x] = true
            }
        }
    }
    for (x in grid[0].indices) {
        var maxHeight = -1
        for (y in grid.indices) {
            if (grid[y][x] > maxHeight) {
                maxHeight = grid[y][x]
                visibility[y][x] = true
            }
        }
    }
    extracted(grid, visibility)

    return visibility.asSequence().flatMap { it.asSequence() }.count { it }
}

private fun extracted(grid: List<IntArray>, visibility: List<BooleanArray>) {
    for (x in grid[0].indices) {
        var maxHeight = -1
        for (y in grid.indices.reversed()) {
            if (grid[y][x] > maxHeight) {
                maxHeight = grid[y][x]
                visibility[y][x] = true
            }
        }
    }
}

private fun solution2(input: String): Int {
    val grid = createGrid(input)

    return grid.flatMapIndexed { y, row -> row.indices.map { x -> score(grid, y, x) } }
        .max()
}

private fun createGrid(input: String) = input.lineSequence().map { it.map { c -> c - '0' }.toIntArray() }.toList()

private fun score(grid: List<IntArray>, y: Int, x: Int): Int {
    var n = 0
    var e = 0
    var s = 0
    var w = 0

    for (i in (x - 1) downTo 0 step 1) {
        w++
        if (grid[y][i] >= grid[y][x])
            break
    }
    for (i in (x + 1) until grid[0].size) {
        e++
        if (grid[y][i] >= grid[y][x])
            break
    }
    for (i in (y - 1) downTo 0) {
        n++
        if (grid[i][x] >= grid[y][x])
            break
    }
    for (i in (y + 1) until grid.size) {
        s++
        if (grid[i][x] >= grid[y][x])
            break
    }
    return n * e * s * w
}

//===============================================================================================\\

private const val YEAR = 2022
private const val DAY = 8

class Day08Test : StringSpec({
    "example part 1" { ::solution1 invokedWith exampleInput shouldBe 21 }
    "part 1 solution" { ::solution1 invokedWith input(YEAR, DAY) shouldBe 1849 }
    "example part 2" { ::solution2 invokedWith exampleInput shouldBe 8 }
    "part 2 solution" { ::solution2 invokedWith input(YEAR, DAY) shouldBe 201600 }
})

private val exampleInput =
    """
        30373
        25512
        65332
        33549
        35390
    """.trimIndent()
