package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.Input
import nl.dricus.adventofcode.util.Puzzle
import kotlin.math.abs

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
        val blackTiles = blackTiles().toMutableSet()

        repeat(100) {
            val flipToWhite = blackTiles
                .filter { tile ->
                    val adjacentBlack = adjacentBlackTiles(blackTiles, tile)
                    adjacentBlack == 0 || adjacentBlack > 2
                }
            val flipToBlack = blackTiles
                .flatMap { (x, y) ->
                    listOf(
                        x - 1 to y + 1, x + 1 to y - 1,
                        x - 1 to y, x + 1 to y,
                        x to y - 1, x to y + 1
                    )
                }
                .distinct()
                .filter { tile ->
                    if (tile in blackTiles) false else {
                        val adjacentBlack = adjacentBlackTiles(blackTiles, tile)
                        adjacentBlack == 2
                    }
                }

            blackTiles.addAll(flipToBlack)
            blackTiles.removeAll(flipToWhite)
        }

        return blackTiles.count()
    }

    private fun adjacentBlackTiles(blackTiles: MutableSet<Pair<Int, Int>>, tile: Pair<Int, Int>) =
        tile.let { (tileX, tileY) ->
            blackTiles.count { (x, y) ->
                (x - tileX == -1 && y - tileY == 1) ||
                        (x - tileX == 1 && y - tileY == -1) ||
                        (y - tileY == 0 && abs(x - tileX) == 1) ||
                        (abs(y - tileY) == 1 && x - tileX == 0)
            }
        }

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