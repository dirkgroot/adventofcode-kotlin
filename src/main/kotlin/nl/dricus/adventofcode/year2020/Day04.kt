package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.Input
import nl.dricus.adventofcode.util.Puzzle

class Day04(input: Input) : Puzzle() {
    private val passports: List<Map<String, String>> by lazy {
        input.string()
            .replace("\n\n", ";")
            .replace('\n', ' ')
            .split(';')
            .map { passport ->
                passport
                    .split(' ')
                    .map { Pair(it.substring(0..2), it.substring(4)) }
                    .toMap()
            }
    }

    override fun part1() = passports.count { requiredFieldsPresent(it) }

    override fun part2() = passports.count { requiredFieldsPresent(it) && requiredFieldsValid(it) }

    private fun requiredFieldsPresent(passport: Map<String, String>) =
        passport.keys.containsAll(listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"))

    private fun requiredFieldsValid(passport: Map<String, String>) =
        passport.entries.all { (key, value) ->
            when (key) {
                "byr" -> value.toInt() in 1920..2002
                "iyr" -> value.toInt() in 2010..2020
                "eyr" -> value.toInt() in 2020..2030
                "hgt" -> when {
                    value.endsWith("cm") -> value.takeWhile { c -> c.isDigit() }.toInt() in 150..193
                    value.endsWith("in") -> value.takeWhile { c -> c.isDigit() }.toInt() in 59..76
                    else -> false
                }
                "hcl" -> value.matches("#[0-9a-f]{6}".toRegex())
                "ecl" -> value in setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
                "pid" -> value.matches("\\d{9}".toRegex())
                "cid" -> true
                else -> false
            }
        }
}