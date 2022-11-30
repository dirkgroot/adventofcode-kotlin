package nl.dirkgroot.adventofcode.year2021

import nl.dirkgroot.adventofcode.util.Puzzle
import kotlin.math.pow

class Day24 : Puzzle() {
    private val functions = listOf(
        1L, 13L, 8L, 1L, 12L, 13L, 1L, 12L, 8L, 1L, 10L, 10L, 26L, -11L, 12L, 26L, -13L, 1L, 1L, 15L, 13L,
        1L, 10L, 5L, 26L, -2L, 10L, 26L, -6L, 3L, 1L, 14L, 2L, 26L, 0L, 2L, 26L, -15L, 12L, 26L, -4L, 7L,
    ).windowed(3, 3).map { (p1, p2, p3) -> function(p1, p2, p3) }
    private val functions1 = functions.take(6)
    private val functions2 = functions.drop(6).take(4)
    private val functions3 = functions.drop(10)

    private fun function(p1: Long, p2: Long, p3: Long) =
        { input: Long, z: Long ->
            if ((z % 26) + p2 == input) { // 0L else 1L
                z / p1
            } else {
                z / p1 * 26 + input + p3
            }
        }

    override fun part1() = findFirstValid(false)

    override fun part2() = findFirstValid(true)

    private fun findFirstValid(upTo: Boolean): Long {
        var minZ = 99999999999999L
        return findZs(functions1, 0L, upTo)
            .flatMap { (z1, c1) ->
                findZs(functions2, z1, upTo).flatMap { (z2, c2) ->
                    findZs(functions3, z2, upTo).map { (z3, c3) ->
                        z3 to (c1 * 100000000L) + (c2 * 10000) + c3
                    }
                }
            }
            .onEach { (z, c) ->
                if (z < minZ) {
                    minZ = z
                    println("New min: [c: $c, z: $z]")
                }
            }
            .filter { (z, _) -> z == 0L }
            .map { (_, c) -> c }
            .first()
    }

    private fun findZs(fs: List<(Long, Long) -> Long>, initialZ: Long, upTo: Boolean): Sequence<Pair<Long, Long>> {
        val range = if (upTo) createUpToRange(fs.size) else createDownToRange(fs.size)
        return range.asSequence().filter { noZeroes(it) }
            .map {
                val input = toLongList(it, fs.size)
                it to fs.foldIndexed(initialZ) { index, acc, f -> f(input[index], acc) }
            }
            .groupBy { (_, z) -> z }
            .map { (z, cs) -> z to if (upTo) cs.minOf { (c, _) -> c } else cs.maxOf { (c, _) -> c } }
            .asSequence()
            .let {
                if (upTo) it.sortedBy { (_, c) -> c }
                else it.sortedByDescending { (_, c) -> c }
            }
    }

    private fun createUpToRange(digits: Int): LongProgression {
        val start = (1..digits).fold(0L) { acc, _ -> acc * 10 + 1L }
        val end = (1..digits).fold(0L) { acc, _ -> acc * 10 + 9L }
        return start..end
    }

    private fun createDownToRange(digits: Int): LongProgression {
        val start = (1..digits).fold(0L) { acc, _ -> acc * 10 + 9L }
        val end = (1..digits).fold(0L) { acc, _ -> acc * 10 + 1L }
        return start downTo end
    }

    private fun noZeroes(number: Long) = generateSequence(number) { it / 10 }
        .takeWhile { it > 0 }
        .filter { it % 10L == 0L }
        .none()

    private fun toLongList(n: Long, digitCount: Int): List<Long> =
        generateSequence(10.0.pow(digitCount - 1).toLong()) { it / 10 }
            .map { if (it == 0L) n % 10 else n / it % 10 }
            .take(digitCount)
            .toList()
}
