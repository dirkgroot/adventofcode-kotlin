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
                .flatMap {
                    listOf(
                        Tile(it.x - 1, it.z + 1), Tile(it.x + 1, it.z - 1),
                        Tile(it.x - 1, it.z), Tile(it.x + 1, it.z),
                        Tile(it.x, it.z - 1), Tile(it.x, it.z + 1)
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

    private fun adjacentBlackTiles(blackTiles: MutableSet<Tile>, tile: Tile) =
        blackTiles.count {
            (it.x - tile.x == -1 && it.z - tile.z == 1) ||
                    (it.x - tile.x == 1 && it.z - tile.z == -1) ||
                    (it.z - tile.z == 0 && abs(it.x - tile.x) == 1) ||
                    (abs(it.z - tile.z) == 1 && it.x - tile.x == 0)
        }

    private fun blackTiles() = flip.asSequence()
        .map { getCoordinate(it) }
        .groupBy { it }
        .map { (coord, occurrences) -> coord to occurrences.size }
        .filter { (_, count) -> count % 2 > 0 }
        .map { (coord, _) -> coord }

    private data class Tile(val x: Int, val z: Int)

    private tailrec fun getCoordinate(directions: List<String>, x: Int = 0, z: Int = 0): Tile {
        return if (directions.isEmpty()) Tile(x, z)
        else when (directions[0]) {
            "e" -> getCoordinate(directions.drop(1), x + 1, z)
            "se" -> getCoordinate(directions.drop(1), x, z + 1)
            "sw" -> getCoordinate(directions.drop(1), x - 1, z + 1)
            "w" -> getCoordinate(directions.drop(1), x - 1, z)
            "nw" -> getCoordinate(directions.drop(1), x, z - 1)
            "ne" -> getCoordinate(directions.drop(1), x + 1, z - 1)
            else -> throw IllegalStateException()
        }
    }
}