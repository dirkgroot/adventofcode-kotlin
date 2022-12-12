package nl.dirkgroot.adventofcode.year2022

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import nl.dirkgroot.adventofcode.util.input
import nl.dirkgroot.adventofcode.util.invokedWith
import java.io.File
import java.util.*

private fun solution1(input: String) = Grid(input).let { it.shortest(sequenceOf(it.start)) }
private fun solution2(input: String) = Grid(input).let { it.shortest(it.vertices { c -> c == 'a' }) }

private class Grid(input: String) {
    private val infinity = 100000000
    private val cells = input.filter { it != '\n' }.toCharArray()
    private val height = input.lineSequence().count()
    private val width = cells.size / height
    val start = cells.indexOf('S')
    private val end = cells.indexOf('E')

    init {
        cells[start] = 'a'
        cells[end] = 'z'
    }

    fun vertices(predicate: (Char) -> Boolean) = cells.indices.asSequence().filter { predicate(cells[it]) }

    fun shortest(starts: Sequence<Int>): Int {
        val dist = mutableMapOf<Int, Int>()
        val prev = mutableMapOf<Int, Int>()
        val q = PriorityQueue<Int> { o1, o2 -> dist.getValue(o1).compareTo(dist.getValue(o2)) }

        cells.indices.forEach { dist[it] = infinity }
        starts.forEach { v -> dist[v] = 0 }
        q.addAll(starts)

        while (q.isNotEmpty()) {
            val u = q.poll()
            if (u == end || dist[u] == infinity) break

            neighbors(u).forEach { v ->
                val alt = dist.getValue(u) + 1
                if (alt < dist.getValue(v)) {
                    dist[v] = alt
                    prev[v] = u
                    q.add(v)
                }
            }
        }

        return dist.getValue(end)
    }

    private fun neighbors(index: Int) = sequence {
        if (index % width > 0) yield(index - 1)
        if (index % width < width - 1) yield(index + 1)
        if (index + width < cells.size) yield(index + width)
        if (index >= width) yield(index - width)
    }.filter { cells[it] - cells[index] <= 1 }
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
