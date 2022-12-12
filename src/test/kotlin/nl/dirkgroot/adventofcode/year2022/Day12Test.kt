package nl.dirkgroot.adventofcode.year2022

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import nl.dirkgroot.adventofcode.util.input
import nl.dirkgroot.adventofcode.util.invokedWith
import java.util.PriorityQueue

private fun solution1(input: String) = Grid(input).let { it.shortest(sequenceOf(it.startX to it.startY)) }
private fun solution2(input: String) = Grid(input).let { it.shortest(it.vertices { c -> c == 'a' }) }

private class Grid(input: String) {
    private val infinity = 100000000
    private val cells = input.lineSequence().map { it.toCharArray() }.toList()
    private val endX = input.filter { it != '\n' }.indexOf('E') % cells[0].size
    private val endY = input.filter { it != '\n' }.indexOf('E') / cells[0].size

    val startX = input.filter { it != '\n' }.indexOf('S') % cells[0].size
    val startY = input.filter { it != '\n' }.indexOf('S') / cells[0].size

    init {
        cells[startY][startX] = 'a'
        cells[endY][endX] = 'z'
    }

    fun vertices(predicate: (Char) -> Boolean) = sequence {
        for (y in cells.indices) for (x in cells[y].indices)
            if (predicate(cells[y][x])) yield(x to y)
    }

    fun shortest(starts: Sequence<Pair<Int, Int>>): Int {
        val dist = mutableMapOf<Pair<Int, Int>, Int>()
        val prev = mutableMapOf<Pair<Int, Int>, Pair<Int, Int>>()
        val q = PriorityQueue<Pair<Int, Int>> { o1, o2 -> dist.getValue(o1).compareTo(dist.getValue(o2)) }

        for (y in cells.indices) for (x in cells[y].indices) dist[x to y] = infinity
        starts.forEach { v -> dist[v] = 0 }
        q.addAll(starts)

        while (q.isNotEmpty()) {
            val u = q.poll()
            if (u == endX to endY || dist[u] == infinity) break

            neighbors(u.first, u.second).forEach { v ->
                val alt = dist.getValue(u) + 1
                if (alt < dist.getValue(v)) {
                    dist[v] = alt
                    prev[v] = u
                    q.add(v)
                }
            }
        }

        return dist.getValue(endX to endY)
    }

    private fun neighbors(x: Int, y: Int) =
        sequenceOf(x - 1 to y, x + 1 to y, x to y - 1, x to y + 1)
            .filter { (x, y) -> x in cells[0].indices && y in cells.indices }
            .filter { (nx, ny) -> cells[ny][nx] - cells[y][x] <= 1 }
}

//===============================================================================================\\

private const val YEAR = 2022
private const val DAY = 12

class Day12Test : StringSpec({
    "example part 1" { ::solution1 invokedWith exampleInput shouldBe 31 }
    "part 1 solution" { ::solution1 invokedWith input(YEAR, DAY) shouldBe 380 }
    "example part 2" { ::solution2 invokedWith exampleInput shouldBe 29 }
    "part 2 solution" { ::solution2 invokedWith input(YEAR, DAY) shouldBe 375 }
})

private val exampleInput =
    """
        Sabqponm
        abcryxxl
        accszExk
        acctuvwj
        abdefghi
    """.trimIndent()
