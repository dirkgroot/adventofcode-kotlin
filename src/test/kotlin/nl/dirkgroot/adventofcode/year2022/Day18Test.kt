package nl.dirkgroot.adventofcode.year2022

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import nl.dirkgroot.adventofcode.util.input
import nl.dirkgroot.adventofcode.util.invokedWith
import java.util.ArrayDeque

private fun solution1(input: String) = parseCubes(input).allSurfaceSides().size
private fun solution2(input: String) = parseCubes(input).surfaceSidesTouchedBySteam().size

private fun parseCubes(input: String) = input.lineSequence()
    .map { it.split(",").let { (x, y, z) -> Cube(x.toInt(), y.toInt(), z.toInt()) } }
    .toSet()

private fun Set<Cube>.surfaceSidesTouchedBySteam(): List<Side> {
    val steam = mutableSetOf<Cube>()
    val queue = ArrayDeque<Cube>()
    val xRange = minOf { it.x1 } - 1 until maxOf { it.x2 } + 1
    val yRange = minOf { it.y1 } - 1 until maxOf { it.y2 } + 1
    val zRange = minOf { it.z1 } - 1 until maxOf { it.z2 } + 1

    queue.add(Cube(xRange.first, yRange.first, zRange.first))
    while (queue.isNotEmpty()) {
        val candidate = queue.removeFirst()
        if (!contains(candidate) && !steam.contains(candidate)) {
            steam.add(candidate)
            queue.addAll(candidate.neighbors().filter { it.x1 in xRange && it.y1 in yRange && it.z1 in zRange })
        }
    }

    return steam.allSurfaceSides().filter {
        it.x1 - 1 in xRange && it.x2 in xRange && it.y1 - 1 in yRange && it.y2 in yRange && it.z1 - 1 in zRange && it.z2 in zRange
    }
}

private fun Set<Cube>.allSurfaceSides() = allSides().groupBy { it }
    .filter { (_, sides) -> sides.size == 1 }.map { (side, _) -> side }

private fun Set<Cube>.allSides() = asSequence().flatMap { it.sides() }

private data class Side(val x1: Int, val y1: Int, val z1: Int, val x2: Int, val y2: Int, val z2: Int)

private data class Cube(val x1: Int, val y1: Int, val z1: Int) {
    val x2 = x1 + 1
    val y2 = y1 + 1
    val z2 = z1 + 1

    fun sides() = sequenceOf(
        Side(x1, y1, z1, x1, y2, z2), Side(x1, y1, z1, x2, y1, z2), Side(x1, y1, z1, x2, y2, z1),
        Side(x1, y1, z2, x2, y2, z2), Side(x1, y2, z1, x2, y2, z2), Side(x2, y1, z1, x2, y2, z2),
    )

    fun neighbors() = sequenceOf(
        Cube(x1 - 1, y1, z1), Cube(x1 + 1, y1, z1),
        Cube(x1, y1 - 1, z1), Cube(x1, y1 + 1, z1),
        Cube(x1, y1, z1 - 1), Cube(x1, y1, z1 + 1),
    )
}

//===============================================================================================\\

private const val YEAR = 2022
private const val DAY = 18

class Day18Test : StringSpec({
    "example part 1" { ::solution1 invokedWith exampleInput shouldBe 64 }
    "part 1 solution" { ::solution1 invokedWith input(YEAR, DAY) shouldBe 4500 }
    "example part 2" { ::solution2 invokedWith exampleInput shouldBe 58 }
    "part 2 solution" { ::solution2 invokedWith input(YEAR, DAY) shouldBe 2558 }
})

private val exampleInput =
    """
        2,2,2
        1,2,2
        3,2,2
        2,1,2
        2,3,2
        2,2,1
        2,2,3
        2,2,4
        2,2,6
        1,2,5
        3,2,5
        2,1,5
        2,3,5
    """.trimIndent()
