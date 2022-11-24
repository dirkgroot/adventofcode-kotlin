package nl.dirkgroot.adventofcode.year2020

import nl.dirkgroot.adventofcode.util.Input
import nl.dirkgroot.adventofcode.util.Puzzle

class Day16(input: Input) : Puzzle() {
    class Notes(val rules: Map<String, (Int) -> Boolean>, val myTicket: List<Int>, val nearbyTickets: List<List<Int>>)

    private val notes by lazy {
        input.string().split("\n\n")
            .let { (rules, myTicket, nearbyTickets) ->
                Notes(parseRules(rules), parseTicket(myTicket), parseNearbyTickets(nearbyTickets))
            }
    }

    private val ruleRegex = "(.+): (\\d+)-(\\d+) or (\\d+)-(\\d+)".toRegex()
    private fun parseRules(rules: String) = rules.split("\n")
        .map {
            val match = ruleRegex.matchEntire(it)!!
            val range1 = match.groups[2]!!.value.toInt()..match.groups[3]!!.value.toInt()
            val range2 = match.groups[4]!!.value.toInt()..match.groups[5]!!.value.toInt()
            match.groups[1]!!.value to { value: Int ->
                value in range1 || value in range2
            }
        }
        .toMap()

    private fun parseNearbyTickets(nearbyTickets: String) = nearbyTickets.split("\n")
        .map { ticket -> parseTicket(ticket) }

    private fun parseTicket(ticket: String) = ticket.split(",").map { it.toInt() }

    override fun part1() = notes.nearbyTickets
        .sumOf { ticket -> ticket.filter { fieldValue -> notes.rules.values.none { it(fieldValue) } }.sum() }

    override fun part2(): Any {
        val validTickets = notes.nearbyTickets.filter { ticket ->
            ticket.all { fieldValue -> notes.rules.values.any { it(fieldValue) } }
        }
        val fieldRules = (0..notes.myTicket.lastIndex)
            .map { fieldIndex ->
                notes.rules.entries
                    .filter { (_, rule) ->
                        validTickets.all { ticket -> rule(ticket[fieldIndex]) }
                    }
                    .map { it.key }
                    .toMutableList()
            }

        while (!fieldRules.all { it.size == 1 }) {
            fieldRules.forEach { ruleNames ->
                if (ruleNames.size == 1) {
                    fieldRules.forEach { if (it.size > 1) it.remove(ruleNames[0]) }
                }
            }
        }

        return fieldRules.withIndex()
            .filter { (_, names) -> names[0].startsWith("departure") }
            .fold(1L) { acc, (index, _) -> acc * notes.myTicket[index] }
    }
}