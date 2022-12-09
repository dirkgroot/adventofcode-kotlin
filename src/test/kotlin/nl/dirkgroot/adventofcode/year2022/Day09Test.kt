package nl.dirkgroot.adventofcode.year2022

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import nl.dirkgroot.adventofcode.util.input
import nl.dirkgroot.adventofcode.util.invokedWith
import kotlin.math.sign

private fun solution1(input: String) = doMotions(input, 2)
private fun solution2(input: String) = doMotions(input, 10)

private fun doMotions(input: String, knotCount: Int): Int {
    val knots = List(knotCount) { Knot(0, 0) }
    val last = knots.last()
    val tailVisited = mutableSetOf<Pair<Int, Int>>()

    input.lineSequence().map { it.split(" ") }
        .flatMap { (dir, count) -> generateSequence { dir }.take(count.toInt()) }
        .forEach { dir ->
            knots[0].move(dir)
            knots.windowed(2, 1).forEach { (k1, k2) -> k2.follow(k1) }

            tailVisited.add(last.x to last.y)
        }

    return tailVisited.size
}

private class Knot(var x: Int, var y: Int) {
    private val deltas = mapOf("R" to (1 to 0), "U" to (0 to 1), "L" to (-1 to 0), "D" to (0 to -1))

    fun move(direction: String) {
        val (dx, dy) = deltas.getValue(direction)
        x += dx
        y += dy
    }

    fun follow(other: Knot) {
        val dx = other.x - x
        val dy = other.y - y

        if (dx in -1..1 && dy in -1..1) return

        x += dx.sign
        y += dy.sign
    }
}

//===============================================================================================\\

private const val YEAR = 2022
private const val DAY = 9

class Day09Test : StringSpec({
    "example part 1" { ::solution1 invokedWith exampleInput shouldBe 13 }
    "part 1 solution" { ::solution1 invokedWith input(YEAR, DAY) shouldBe 5513 }
    "example part 2" { ::solution2 invokedWith exampleInput shouldBe 1 }
    "larger example part 2" { ::solution2 invokedWith largerExampleInput shouldBe 36 }
    "part 2 solution" { ::solution2 invokedWith input(YEAR, DAY) shouldBe 2427 }
})

private val exampleInput =
    """
        R 4
        U 4
        L 3
        D 1
        R 4
        D 1
        L 5
        R 2
    """.trimIndent()

private val largerExampleInput =
    """
        R 5
        U 8
        L 8
        D 3
        R 17
        D 10
        L 25
        U 20
    """.trimIndent()
