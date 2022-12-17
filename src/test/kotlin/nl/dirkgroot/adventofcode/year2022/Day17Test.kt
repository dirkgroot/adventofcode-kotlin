package nl.dirkgroot.adventofcode.year2022

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import nl.dirkgroot.adventofcode.util.input
import nl.dirkgroot.adventofcode.util.invokedWith
import java.lang.Integer.max

private fun solution1(input: String) = Tower(input.toJetPattern()).simulate(2022)
private fun solution2(input: String) = Tower(input.toJetPattern()).simulate(1000000000000)

private fun String.toJetPattern() = map { if (it == '<') -1 else 1 }

private class Tower(private val jetPattern: List<Int>) {
    private val tiles = mutableListOf<Int>()
    private var jetIndex = 0
    private var height = 0
    private val heightIncreases = mutableListOf<Int>()
    private val hasRepeatingPatternOnTop get() = repeatingPatternHeight != 0
    private var repeatingPatternHeight = 0
    private var repeatingPatternLength = 0

    fun simulate(iterationCount: Long): Long {
        var currentShape = 0

        fun iterate() {
            drop(shapes[currentShape])
            currentShape = (currentShape + 1) % shapes.size
        }

        val iterations = generateSequence { if (hasRepeatingPatternOnTop) null else iterate() }.count()
        val nonRepeatingBottomIterations = iterations % repeatingPatternLength
        val nonRepeatingTopIterations = (iterationCount - nonRepeatingBottomIterations) % repeatingPatternLength

        repeat(nonRepeatingTopIterations.toInt()) { iterate() }

        val nonRepeatingBottomHeight = heightIncreases.take(nonRepeatingBottomIterations).sum()
        val nonRepeatingTopHeight = heightIncreases.takeLast(nonRepeatingTopIterations.toInt()).sum()
        val repeatingPatternIterations = iterationCount - nonRepeatingBottomIterations - nonRepeatingTopIterations
        val repetitionHeight = repeatingPatternIterations / repeatingPatternLength * repeatingPatternHeight

        return nonRepeatingBottomHeight + repetitionHeight + nonRepeatingTopHeight
    }

    fun drop(shape: IntArray) {
        repeat(height + 3 + shape.size) { tiles.add(0b0000000) }
        val rock = shape.copyOf()

        val rockHeight = generateSequence(height + 3) { currentHeight ->
            val direction = nextJet()
            val hasMoved = rock.tryMove(direction)
            if (hasMoved && overlapsWithTiles(rock, currentHeight))
                rock.tryMove(-direction)

            if (currentHeight == 0 || overlapsWithTiles(rock, currentHeight - 1)) null
            else currentHeight - 1
        }.last()

        rock.forEachIndexed { index, i -> tiles[rockHeight + index] = tiles[rockHeight + index] or i }

        val newHeight = max(height, rockHeight + shape.size)
        if (newHeight > height) heightIncreases.add(newHeight - height) else heightIncreases.add(0)
        height = newHeight

        if (!hasRepeatingPatternOnTop) {
            val hi = heightIncreases.joinToString("")
            for (i in hi.length / 2 downTo 10) {
                val s1 = hi.substring(hi.length - i)
                if (hi.endsWith(s1 + s1)) {
                    repeatingPatternLength = s1.length
                    repeatingPatternHeight = heightIncreases.takeLast(s1.length).sum()
                    break
                }
            }
        }
    }

    private fun nextJet() = jetPattern[jetIndex].also { jetIndex = (jetIndex + 1) % jetPattern.size }

    private fun overlapsWithTiles(shape: IntArray, y: Int) = shape.foldIndexed(false) { index, acc, i ->
        acc || tiles[y + index] or i != tiles[y + index] xor i
    }

    override fun toString() = (height - 1 downTo 0).joinToString("\n") { y ->
        ("|" + tiles[y].toString(2).padStart(7, '0') + "|").replace('0', '.').replace('1', '#')
    } + "\n+-------+"
}

private fun IntArray.tryMove(dir: Int): Boolean {
    if (dir > 0) {
        if (any { it and 0b0000001 == 0b0000001 }) return false
        indices.forEach { this[it] = this[it] shr 1 }
    } else {
        if (any { it and 0b1000000 == 0b1000000 }) return false
        indices.forEach { this[it] = this[it] shl 1 }
    }
    return true
}

private val shapes = arrayOf(
    intArrayOf(0b0011110),
    intArrayOf(0b0001000, 0b0011100, 0b0001000),
    intArrayOf(0b0011100, 0b0000100, 0b0000100),
    intArrayOf(0b0010000, 0b0010000, 0b0010000, 0b0010000),
    intArrayOf(0b0011000, 0b0011000)
)

//===============================================================================================\\

private const val YEAR = 2022
private const val DAY = 17

class Day17Test : StringSpec({
    "test shape 0" {
        val tower = Tower(exampleInput.toJetPattern())
        generateSequence(0) { (it + 1) % shapes.size }
            .take(10)
            .forEach { tower.drop(shapes[it]) }
        println(tower)
        tower.toString() shouldBe """
            |....#..|
            |....#..|
            |....##.|
            |##..##.|
            |######.|
            |.###...|
            |..#....|
            |.####..|
            |....##.|
            |....##.|
            |....#..|
            |..#.#..|
            |..#.#..|
            |#####..|
            |..###..|
            |...#...|
            |..####.|
            +-------+
        """.trimIndent()
    }
    "example part 1" { ::solution1 invokedWith exampleInput shouldBe 3068 }
    "part 1 solution" { ::solution1 invokedWith input(YEAR, DAY) shouldBe 3117 }
    "example part 2" { ::solution2 invokedWith exampleInput shouldBe 1514285714288 }
    "part 2 solution" { ::solution2 invokedWith input(YEAR, DAY) shouldBe 1553314121019 }
})

private val exampleInput =
    """
        >>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>
    """.trimIndent()
