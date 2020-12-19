package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.Input
import nl.dricus.adventofcode.util.Puzzle
import kotlin.math.max

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

    private fun parseRules(rules: String, part2: Boolean): Map<Int, Rule> {
        return rules.split("\n")
            .map { rule ->
                rule.split(": ").let { (id, text) ->
                    val ruleId = id.toInt()

                    if (part2 && ruleId == 8)
                        ruleId to parseRule("42 | 42 8")
                    else if (part2 && ruleId == 11)
                        ruleId to parseRule("42 31 | 42 11 31")
                    else
                        ruleId to parseRule(text)
                }
            }
            .toMap()
    }

    private val terminalRegex = "\".\"".toRegex()
    private val sequenceRegex = "(\\d+ ?)+".toRegex()
    private val eitherOrRegex = "(\\d+ ?)+ \\| (\\d+ ?)+".toRegex()

    private fun parseRule(rule: String): Rule =
        when {
            rule.matches(terminalRegex) -> Terminal(rule[1])
            rule.matches(sequenceRegex) -> {
                val rules = rule.split(" ")
                    .map { it.toInt() }
                Sequence(rules)
            }
            rule.matches(eitherOrRegex) -> {
                val (either, or) = rule.split(" | ")
                EitherOr(parseRule(either), parseRule(or))
            }
            else -> throw IllegalStateException("Cannot parse rule $rule")
        }

    override fun part1() = messages.count { message ->
        evaluate(message, rules1[0]!!, rules1)
    }

    override fun part2() = messages.count { message ->
        evaluate(message, rules2[0]!!, rules2)
    }

    private fun evaluate(message: String, rule: Rule, rules: Map<Int, Rule>) =
        match(message, rule, rules) == message.length

    private fun match(message: String, rule: Rule, rules: Map<Int, Rule>) =
        when (rule) {
            is Terminal -> terminalMatch(message, rule)
            is Sequence -> sequenceMatch(message, rule, rules)
            is EitherOr -> eitherOrMatch(message, rule, rules)
            else -> throw IllegalStateException("Unknown rule: $rule")
        }

    private fun sequenceMatch(message: String, rule: Sequence, rules: Map<Int, Rule>): Int =
        if (isTailRecursive(rules[rule.rules[0]]!!, rules)) {
            (1 until message.length).map { i ->
                val first = match(message.substring(0 until i), rules[rule.rules[0]]!!, rules)
                val second = match(message.substring(i), rules[rule.rules[1]]!!, rules)
                first + second
            }.maxOrNull() ?: 0
        } else {
            rule.rules.fold(0) { acc, id ->
                val matchCount = match(message.substring(acc), rules[id]!!, rules)
                if (matchCount == 0) return 0
                acc + matchCount
            }
        }

    private fun eitherOrMatch(message: String, rule: EitherOr, rules: Map<Int, Rule>): Int {
        val either = match(message, rule.either, rules)
        val or = match(message, rule.or, rules)

        return max(either, or)
    }

    private fun terminalMatch(message: String, rule: Terminal) =
        if (message.isEmpty()) 0 else if (message[0] == rule.value) 1 else 0

    private fun isTailRecursive(rule: Rule, rules: Map<Int, Rule>) =
        when (rule) {
            is EitherOr -> rule.or is Sequence && rules[rule.or.rules.last()] == rule
            else -> false
        }
}