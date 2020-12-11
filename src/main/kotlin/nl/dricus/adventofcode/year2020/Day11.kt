package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.Input
import nl.dricus.adventofcode.util.Puzzle

class Day11(input: Input) : Puzzle() {
    private val empty = 'L'
    private val occupied = '#'
    private val floor = '.'

    private val area by lazy {
        val area = input.lines().map { listOf(floor) + it.toList() + listOf(floor) }
        val floorRow = listOf(List(area[0].size) { floor })
        floorRow + area + floorRow
    }

    private val rowCount by lazy { area.size }
    private val colCount by lazy { area[0].size }
    private val directions = listOf(-1 to -1, -1 to 0, -1 to 1, 1 to -1, 1 to 0, 1 to 1, 0 to -1, 0 to 1)

    override fun part1() = createRounds(4, ::adjacentNeighbors)
        .last()
        .countOccupiedSeats()

    private fun adjacentNeighbors(rowIndex: Int, colIndex: Int) =
        directions.map { (rowDiff, colDiff) -> rowIndex + rowDiff to colIndex + colDiff }

    override fun part2() = createRounds(5, ::visibleNeighbors)
        .last()
        .countOccupiedSeats()

    private fun visibleNeighbors(rowIndex: Int, colIndex: Int) =
        directions.map { (rowDiff, colDiff) -> visibleNeighbor(rowIndex, colIndex, rowDiff, colDiff) }

    private tailrec fun visibleNeighbor(rowIndex: Int, colIndex: Int, rowDiff: Int, colDiff: Int): Pair<Int, Int> {
        val row = rowIndex + rowDiff
        val col = colIndex + colDiff
        return when {
            row == 0 || col == 0 -> row to col
            row == rowCount - 1 || col == colCount - 1 -> row to col
            area[row][col] != floor -> row to col
            else -> visibleNeighbor(row, col, rowDiff, colDiff)
        }
    }

    private fun createRounds(maxOccupied: Int, neighbors: (Int, Int) -> List<Pair<Int, Int>>) =
        generateSequence(area) { state ->
            state
                .mapIndexed { rowIndex, row ->
                    row.mapIndexed { colIndex, col ->
                        when (col) {
                            empty ->
                                if (allEmpty(state, neighbors(rowIndex, colIndex)))
                                    occupied
                                else
                                    empty
                            occupied ->
                                if (tooManyOccupied(state, maxOccupied, neighbors(rowIndex, colIndex)))
                                    empty
                                else
                                    occupied
                            else -> col
                        }
                    }
                }
                .let { if (it == state) null else it }
        }

    private fun allEmpty(state: List<List<Char>>, neighbors: List<Pair<Int, Int>>) =
        neighbors.all { (row, column) -> state[row][column] != occupied }

    private fun tooManyOccupied(state: List<List<Char>>, max: Int, neighbors: List<Pair<Int, Int>>) =
        neighbors.count { (r, c) -> state[r][c] == occupied } >= max

    private fun List<List<Char>>.countOccupiedSeats() =
        sumBy { row -> row.count { it == occupied } }
}