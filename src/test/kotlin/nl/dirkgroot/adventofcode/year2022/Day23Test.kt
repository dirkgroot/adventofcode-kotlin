package nl.dirkgroot.adventofcode.year2022

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import nl.dirkgroot.adventofcode.util.input
import nl.dirkgroot.adventofcode.util.invokedWith

private fun solution1(input: String) = parse(input).let { map ->
    repeat(10) { map.round() }
    val x1 = map.elves.minOf { it.x }
    val x2 = map.elves.maxOf { it.x }
    val y1 = map.elves.minOf { it.y }
    val y2 = map.elves.maxOf { it.y }

    (y1..y2).flatMap { y -> (x1..x2).map { x -> y to x } }
        .count { pos -> !map.positions.contains(pos) }
}

private fun solution2(input: String) = parse(input).let { map ->
    generateSequence { map.round() }
        .takeWhile { elvesMoved -> elvesMoved }
        .count() + 1
}

private fun parse(input: String) =
    Map(input.lineSequence().flatMapIndexed { y, row ->
        row.asSequence().mapIndexedNotNull { x, char -> if (char == '#') Elf(y, x) else null }
    }.toList())

private class Map(val elves: List<Elf>) {
    val positions = elves.associateBy { it.y to it.x }.toMutableMap()
    private val proposalOrder = mutableListOf(Direction.N, Direction.S, Direction.W, Direction.E)

    fun round(): Boolean {
        val proposals = elves.mapNotNull { elf ->
            if (elf.neighbors().none { positions.contains(it) })
                null
            else
                proposalOrder.firstOrNull { dir -> elf.neighbors(dir).none { pos -> positions.contains(pos) } }
                    ?.let {
                        when (it) {
                            Direction.N -> (elf.y - 1 to elf.x) to elf
                            Direction.S -> (elf.y + 1 to elf.x) to elf
                            Direction.W -> (elf.y to elf.x - 1) to elf
                            Direction.E -> (elf.y to elf.x + 1) to elf
                        }
                    }
        }.groupBy({ (pos, _) -> pos }, { (_, elf) -> elf })
            .filter { (_, l) -> l.size == 1 }
            .map { (pos, l) -> pos to l.first() }

        proposals.forEach { (pos, elf) ->
            positions.remove(elf.y to elf.x)
            positions[pos] = elf
            elf.y = pos.first
            elf.x = pos.second
        }

        rotateProposalOrder()

        return proposals.isNotEmpty()
    }

    private fun rotateProposalOrder() {
        val dir = proposalOrder.removeFirst()
        proposalOrder.add(dir)
    }
}

private class Elf(var y: Int, var x: Int) {
    fun neighbors() = listOf(
        y - 1 to x - 1, y to x - 1, y + 1 to x - 1,
        y - 1 to x, y + 1 to x,
        y - 1 to x + 1, y to x + 1, y + 1 to x + 1,
    )

    fun neighbors(dir: Direction) = when (dir) {
        Direction.N -> listOf(y - 1 to x - 1, y - 1 to x, y - 1 to x + 1)
        Direction.S -> listOf(y + 1 to x - 1, y + 1 to x, y + 1 to x + 1)
        Direction.W -> listOf(y - 1 to x - 1, y to x - 1, y + 1 to x - 1)
        Direction.E -> listOf(y - 1 to x + 1, y to x + 1, y + 1 to x + 1)
    }
}

private enum class Direction { N, S, W, E }

//===============================================================================================\\

private const val YEAR = 2022
private const val DAY = 23

class Day23Test : StringSpec({
    "example part 1" { ::solution1 invokedWith exampleInput shouldBe 110 }
    "part 1 solution" { ::solution1 invokedWith input(YEAR, DAY) shouldBe 4070 }
    "example part 2" { ::solution2 invokedWith exampleInput shouldBe 20 }
    "part 2 solution" { ::solution2 invokedWith input(YEAR, DAY) shouldBe 881 }
})

private val exampleInput =
    """
        ....#..
        ..###.#
        #...#.#
        .#...##
        #.###..
        ##.#.##
        .#..#..
    """.trimIndent()
