package nl.dricus.adventofcode

fun main(args: Array<String>) {
    val year = args[0].toInt()
    val day = args[1]

    if (day == "all")
        puzzles[year]?.forEachIndexed { index, puzzle ->
            puzzle.apply {
                println("Solutions for puzzle $year-${index + 1}:")
                println("- Part 1: ${part1()}")
                println("- Part 2: ${part2()}")
            }
        }
    else
        solve(year, day.toInt())
}

private fun solve(year: Int, day: Int) {
    val puzzle = puzzles[year]?.get(day - 1)

    if (puzzle == null)
        println("Could't find a puzzle for year $year, day $day.")
    else
        puzzle.apply {
            println("Solutions for puzzle $year-$day:")
            println("- Part 1: ${part1()}")
            println("- Part 2: ${part2()}")
        }
}
