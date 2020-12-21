package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.Input
import nl.dricus.adventofcode.util.Puzzle
import kotlin.math.sqrt

class Day20(input: Input) : Puzzle() {
    data class TileArrangement(val tileId: Long, val image: List<List<Char>>)

    private val tiles by lazy { input.string().split("\n\n").map { parseTile(it) }.toMap() }

    private fun parseTile(text: String): Pair<Long, List<List<Char>>> {
        val lines = text.split("\n")
        val id = lines[0].substring(5..8).toLong()

        return id to lines.subList(1, lines.size).map { it.map { c -> c } }
    }

    override fun part1() = arrange().let { grid ->
        grid[0][0].tileId * grid.last()[0].tileId * grid.last().last().tileId * grid[0].last().tileId
    }

    override fun part2() = permutations(imageFromGrid(arrange()))
        .map { it to findSeaMonsters(it) }
        .first { (_, monsters) -> monsters.isNotEmpty() }
        .let { (image, monsters) -> markSeaMonsters(image, monsters) }
        .sumOf { line -> line.count { c -> c == '#' } }

    private fun arrange(): List<List<TileArrangement>> {
        val gridSize = sqrt(tiles.keys.size.toDouble()).toInt() * 2 + 1
        val tileIds = tiles.map { (id, _) -> id }.toMutableList()
        val grid = List(gridSize) { MutableList<TileArrangement?>(gridSize) { null } }

        val x = gridSize / 2
        val y = x

        val tileId = tiles.keys.first()
        grid[y][x] = TileArrangement(tileId, tiles[tileId]!!)
        tileIds.remove(tileId)

        var fill = setOf(x to y)
        while (tileIds.isNotEmpty()) {
            fill = fill.filter { (x, y) -> grid[y][x] != null }
                .flatMap { (x, y) -> setOf(x - 1 to y, x + 1 to y, x to y - 1, x to y + 1) }
                .filter { (x, y) -> grid[y][x] == null }
                .toSet()
                .onEach { (x, y) ->
                    val tilePermutations = tileIds.asSequence().flatMap { tileId ->
                        permutations(tiles[tileId]!!).map { tile -> tileId to tile }
                    }

                    tilePermutations
                        .firstOrNull { (_, permutation) ->
                            (grid[y][x - 1]?.let { fitsHorizontally(it.image, permutation) } ?: false) ||
                                    (grid[y][x + 1]?.let { fitsHorizontally(permutation, it.image) } ?: false) ||
                                    (grid[y - 1][x]?.let { fitsVertically(it.image, permutation) } ?: false) ||
                                    (grid[y + 1][x]?.let { fitsVertically(permutation, it.image) } ?: false)
                        }
                        ?.let { (id, tile) ->
                            grid[y][x] = TileArrangement(id, tile)
                            tileIds.remove(id)
                        }
                }
        }

        return grid
            .filter { line -> line.any { tile -> tile != null } }
            .map { line -> line.filterNotNull() }
    }

    private fun permutations(image: List<List<Char>>) = sequence {
        var permutation = image
        for (i in 1..4) {
            permutation = rotateCW(permutation)
            yield(permutation)
            permutation = flipHorizontal(permutation)
            yield(permutation)
            permutation = flipVertical(permutation)
            yield(permutation)
            permutation = flipHorizontal(permutation)
            yield(permutation)
            permutation = flipVertical(permutation)
        }
    }

    private fun fitsHorizontally(tile1: List<List<Char>>, tile2: List<List<Char>>) =
        tile1.withIndex().all { (y, line) -> line.last() == tile2[y].first() }

    private fun fitsVertically(tile1: List<List<Char>>, tile2: List<List<Char>>) =
        tile1.last().withIndex().all { (x, c) -> tile2.first()[x] == c }

    private fun rotateCW(tile: List<List<Char>>) = transpose(tile, ::rotateXYCounterCW)
    private fun flipHorizontal(tile: List<List<Char>>) = transpose(tile, ::flipXYHorizontal)
    private fun flipVertical(tile: List<List<Char>>) = transpose(tile, ::flipXYVertical)

    private fun transpose(tile: List<List<Char>>, transformation: (Int, Int, Int) -> Pair<Int, Int>) =
        tile.indices.map { y ->
            tile[y].indices.map { x ->
                transformation(x, y, tile.size).let { (sx, sy) -> tile[sy][sx] }
            }
        }

    private fun rotateXYCounterCW(x: Int, y: Int, size: Int) = y to size - x - 1
    private fun flipXYHorizontal(x: Int, y: Int, size: Int) = size - x - 1 to y
    private fun flipXYVertical(x: Int, y: Int, size: Int) = x to size - y - 1

    private fun imageFromGrid(grid: List<List<TileArrangement>>) = grid
        .map { line ->
            line.map { arr ->
                TileArrangement(arr.tileId, arr.image
                    .slice(1 until arr.image.lastIndex)
                    .map { line -> line.slice(1 until line.lastIndex) })
            }
        }
        .flatMap { gridLine ->
            gridLine[0].image.indices.map { y ->
                gridLine.flatMap { tile -> tile.image[y] }
            }
        }

    private val seaMosterWidth = 20
    private val seaMosterHeight = 3
    private val seaMonster = listOf(
        "                  # ",
        "#    ##    ##    ###",
        " #  #  #  #  #  #   "
    )

    private fun findSeaMonsters(image: List<List<Char>>) = sequence {
        for (y in 0..image.size - seaMosterHeight) {
            for (x in 0..image[y].size - seaMosterWidth) {
                if (isSeaMonster(image, x, y)) yield(x to y)
            }
        }
    }.toList()

    private fun isSeaMonster(image: List<List<Char>>, x: Int, y: Int) =
        seaMonster.withIndex().all { (monsterY, line) ->
            line.withIndex().all { (monsterX, c) ->
                when (c) {
                    '#' -> image[y + monsterY][x + monsterX] == '#'
                    else -> true
                }
            }
        }

    private fun markSeaMonsters(image: List<List<Char>>, monsters: List<Pair<Int, Int>>) =
        image.map { it.toMutableList() }.also { result ->
            monsters.forEach { (monsterX, monsterY) ->
                for (y in 0 until seaMosterHeight) {
                    for (x in 0 until seaMosterWidth) {
                        if (seaMonster[y][x] == '#') result[monsterY + y][monsterX + x] = 'O'
                    }
                }
            }
        }
}