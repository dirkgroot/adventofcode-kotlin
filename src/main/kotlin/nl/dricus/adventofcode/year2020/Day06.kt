package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.Input
import nl.dricus.adventofcode.util.Puzzle

object Day06 : Puzzle() {
    private class Person(val yesAnswers: Set<Char>)
    private class Group(val persons: List<Person>)

    private val groups by lazy {
        Input.string(2020, 6)
            .split("\n\n")
            .map {
                Group(it.split("\n")
                    .map { personAnswers -> Person(personAnswers.toSet()) })
            }
    }

    override fun part1() =
        groups
            .map { it.persons.flatMap { person -> person.yesAnswers }.toSet() }
            .fold(0) { sum, groupAnswers -> sum + groupAnswers.size }

    override fun part2(): Int =
        groups
            .map {
                val allQuestions = ('a'..'z').toSet()
                it.persons.fold(allQuestions) { allPersonsYes, current ->
                    allPersonsYes intersect current.yesAnswers
                }
            }
            .fold(0) { sum, allPersonsYes -> sum + allPersonsYes.size }
}