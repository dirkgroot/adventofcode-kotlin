package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.Input
import nl.dricus.adventofcode.util.Puzzle

class Day24(input: Input) : Puzzle() {
    private val flip by lazy {
        input.lines().map { parseRoute(it) }
    }

    private val directionRegex = "(e|se|sw|w|nw|ne)".toRegex()
    private tailrec fun parseRoute(route: String, directions: List<String> = listOf()): List<String> {
        return if (route == "") directions
        else {
            val match = directionRegex.find(route)!!
            parseRoute(route.substring(match.groupValues[1].length), directions + match.groupValues[1])
        }
    }

    override fun part1() = blackTiles().size

    override fun part2(): Int {
        val blackTiles = blackTiles()
        val initialWidth = blackTiles.maxOf { it.first } - blackTiles.minOf { it.first }
        val initialHeight = blackTiles.maxOf { it.second } - blackTiles.minOf { it.second }
        val turns = 100
        val increase = turns * 2 + 4
        val originX = (initialWidth + increase) / 2
        val originY = (initialHeight + increase) / 2
        val grid = Array(initialHeight + increase) { y ->
            BooleanArray(initialWidth + increase) { x -> x - originX to y - originY in blackTiles }
        }

        (1..100).fold(initialWidth + 2 to initialHeight + 2) { (width, height), _ ->
            val flip = (originY - height / 2..originY + height / 2).asSequence()
                .flatMap { y -> (originX - width / 2..originX + width / 2).map { x -> x to y } }
                .filter { (x, y) ->
                    val adjacentBlackTileCount = adjacentBlackTiles(grid, x, y)
                    if (grid[y][x]) {
                        adjacentBlackTileCount == 0 || adjacentBlackTileCount > 2
                    } else {
                        adjacentBlackTileCount == 2
                    }
                }
                .toList()

            flip.forEach { (x, y) -> grid[y][x] = !grid[y][x] }

            width + 2 to height + 2
        }

        return grid.sumBy { it.count { isBlack -> isBlack } }
    }

    private fun adjacentBlackTiles(grid: Array<BooleanArray>, x: Int, y: Int) =
        listOf(x - 1 to y + 1, x + 1 to y - 1, x - 1 to y, x + 1 to y, x to y + 1, x to y - 1)
            .count { (tx, ty) -> grid[ty][tx] }

    private fun blackTiles() = flip.asSequence()
        .map { getCoordinate(it) }
        .groupBy { it }
        .map { (coord, occurrences) -> coord to occurrences.size }
        .filter { (_, count) -> count % 2 > 0 }
        .map { (coord, _) -> coord }

    private tailrec fun getCoordinate(directions: List<String>, x: Int = 0, y: Int = 0): Pair<Int, Int> =
        if (directions.isEmpty()) x to y
        else when (directions[0]) {
            "e" -> getCoordinate(directions.drop(1), x + 1, y)
            "se" -> getCoordinate(directions.drop(1), x, y + 1)
            "sw" -> getCoordinate(directions.drop(1), x - 1, y + 1)
            "w" -> getCoordinate(directions.drop(1), x - 1, y)
            "nw" -> getCoordinate(directions.drop(1), x, y - 1)
            "ne" -> getCoordinate(directions.drop(1), x + 1, y - 1)
            else -> throw IllegalStateException()
        }
}