package nl.dirkgroot.adventofcode.year2020

import nl.dirkgroot.adventofcode.util.Input
import nl.dirkgroot.adventofcode.util.Puzzle

class Day19(input: Input) : Puzzle() {
    interface Rule
    data class Terminal(val value: Char) : Rule
    data class Sequence(val rules: List<Int>) : Rule
    data class EitherOr(val either: Rule, val or: Rule) : Rule

    private val parts by lazy {
        val parts = input.string().split("\n\n")
        parts[0] to parts[1]
    }
    private val rules1 by lazy { parseRules(parts.first, false) }
    private val rules2 by lazy { parseRules(parts.first, true) }
    private val messages by lazy { parts.second.split("\n") }

    private fun parseRules(rules: String, part2: Boolean) =
        rules.split("\n").map { rule ->
            rule.split(": ").let { (id, text) ->
                val ruleId = id.toInt()

                if (part2 && ruleId == 8) ruleId to parseRule("42 | 42 8")
                else if (part2 && ruleId == 11) ruleId to parseRule("42 31 | 42 11 31")
                else ruleId to parseRule(text)
            }
        }.toMap()

    private val terminalRegex = "\".\"".toRegex()
    private val sequenceRegex = "(\\d+ ?)+".toRegex()
    private val eitherOrRegex = "(\\d+ ?)+ \\| (\\d+ ?)+".toRegex()

    private fun parseRule(rule: String): Rule =
        when {
            rule.matches(terminalRegex) -> Terminal(rule[1])
            rule.matches(sequenceRegex) -> {
                val rules = rule.split(" ").map { it.toInt() }
                Sequence(rules)
            }
            rule.matches(eitherOrRegex) -> {
                val (either, or) = rule.split(" | ")
                EitherOr(parseRule(either), parseRule(or))
            }
            else -> throw IllegalStateException("Cannot parse rule $rule")
        }

    override fun part1() = messages.count { message -> isMatch(message, listOf(rules1[0]!!), rules1) }
    override fun part2() = messages.count { message -> isMatch(message, listOf(rules2[0]!!), rules2) }

    private fun isMatch(message: CharSequence, shouldMatch: List<Rule>, rules: Map<Int, Rule>): Boolean {
        when {
            message.isEmpty() -> return shouldMatch.isEmpty()
            shouldMatch.isEmpty() -> return false
        }

        return when (val rule = shouldMatch[0]) {
            is Terminal ->
                if (message[0] == rule.value) isMatch(message.drop(1), shouldMatch.drop(1), rules)
                else false
            is Sequence ->
                rule.rules.map { rules[it]!! }
                    .let { isMatch(message, it + shouldMatch.drop(1), rules) }
            is EitherOr ->
                isMatch(message, listOf(rule.either) + shouldMatch.drop(1), rules) ||
                        isMatch(message, listOf(rule.or) + shouldMatch.drop(1), rules)
            else -> throw IllegalStateException("Unknown rule")
        }
    }
}