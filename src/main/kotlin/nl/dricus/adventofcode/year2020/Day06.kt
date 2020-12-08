package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.Input
import nl.dricus.adventofcode.util.Puzzle

class Day06(input: Input) : Puzzle() {
    private val groups by lazy { parseGroups(input.string()) }

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