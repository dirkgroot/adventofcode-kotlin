package nl.dirkgroot.adventofcode.year2021

import nl.dirkgroot.adventofcode.util.Input
import nl.dirkgroot.adventofcode.util.Puzzle

class Day25(input: Input) : Puzzle() {
    private val lines = input.lines()
    val width = lines[0].length
    val map = lines.joinToString("").toCharArray()

    override fun part1() = generateSequence { move() }.takeWhile { it != 0 }.count() + 1

    fun move() = doMoves(eastMoves()) + doMoves(southMoves())

    private fun doMoves(moves: List<Pair<Int, Int>>) = moves.onEach { (from, to) ->
        map[to] = map[from]
        map[from] = '.'
    }.size

    private fun eastMoves() = map.indices
        .map {
            val to = it + 1
            it to if (to % width == 0) to - width else to
        }
        .filter { (from, to) -> map[from] == '>' && map[to] == '.' }

    private fun southMoves() = map.indices
        .map {
            val to = it + width
            it to if (to >= map.size) to - map.size else to
        }
        .filter { (from, to) -> map[from] == 'v' && map[to] == '.' }
}