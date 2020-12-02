package nl.dricus.adventofcode

import nl.dricus.adventofcode.util.Puzzle

fun main(args: Array<String>) {
    val year = args[0].toInt()
    val day = args[1]

    if (day == "all")
        puzzles[year]?.forEachIndexed { index, puzzle ->
            printSolution(year, index + 1, puzzle)
        }
    else
        solve(year, day.toInt())
}

private fun solve(year: Int, day: Int) {
    val puzzle = puzzles[year]?.get(day - 1)

    if (puzzle == null)
        println("Could't find a puzzle for year $year, day $day.")
    else
        printSolution(year, day, puzzle)
}

private fun printSolution(year: Int, day: Int, puzzle: Puzzle) {
    println("Solutions for puzzle $year-$day:")
    println("- Part 1: ${puzzle.part1()}")
    println("- Part 2: ${puzzle.part2()}")
}
