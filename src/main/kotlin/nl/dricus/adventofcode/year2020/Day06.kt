package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.Input
import nl.dricus.adventofcode.util.Puzzle

object Day06 : Puzzle() {
    private val groups by lazy { parseGroups(Input.string(2020, 6)) }

    private fun parseGroups(input: String) = input
        .split("\n\n")
        .map { parsePerson(it) }

    private fun parsePerson(input: String) = input
        .split("\n")
        .map { personAnswers -> personAnswers.toSet() }

    override fun part1() = groups
        .map(::allYesAnswers)
        .sumBy { it.size }

    private fun allYesAnswers(group: List<Set<Char>>) = group
        .reduce { allYesAnswers, personYesAnswers -> allYesAnswers union personYesAnswers }

    override fun part2() = groups
        .map(::yesAnswersByAllPersons)
        .sumBy { it.size }

    private fun yesAnswersByAllPersons(group: List<Set<Char>>) = group
        .reduce { allPersonsAnsweredYes, personAnswers -> allPersonsAnsweredYes intersect personAnswers }
}