package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.Input
import nl.dricus.adventofcode.util.Puzzle

class Day18(input: Input) : Puzzle() {
    private val tokenRegex = "\\s*(\\d+|\\+|\\*|\\(|\\))".toRegex()

    abstract class Token {
        override fun toString() = this::class.simpleName ?: ""
    }

    object Plus : Token()
    object Star : Token()
    object Open : Token()
    object Close : Token()
    data class Number(val value: Long) : Token()

    interface Term {
        fun evaluate(): Long
    }

    abstract class Operation(val term: Term) {
        abstract fun evaluate(input: Long): Long
    }

    class Add(term: Term) : Operation(term) {
        override fun evaluate(input: Long) = input + term.evaluate()
    }

    class Multiply(term: Term) : Operation(term) {
        override fun evaluate(input: Long) = input * term.evaluate()
    }

    data class Constant(val value: Long) : Term {
        override fun evaluate() = value
    }

    data class Expression(val term: Term, val ops: List<Operation>) : Term {
        override fun evaluate() = ops
            .fold(term.evaluate()) { acc, operation -> operation.evaluate(acc) }
    }

    private val expressions by lazy { input.lines() }

    override fun part1() = solve(Part.One)

    override fun part2() = solve(Part.Two)

    private fun solve(part: Part) = expressions
        .map { tokenize(it) }
        .map { Parser(part).parse(it) }
        .sumOf { it.evaluate() }

    private fun tokenize(expression: String) = tokenRegex.findAll(expression).map {
        when (val token = it.groupValues[1]) {
            "+" -> Plus
            "*" -> Star
            "(" -> Open
            ")" -> Close
            else -> Number(token.toLong())
        }
    }

    enum class Part { One, Two }
    private class Parser(val part: Part) {
        fun parse(tokens: Sequence<Token>) = parseExpression(tokens).first

        private fun parseExpression(tokens: Sequence<Token>): Pair<Expression, Sequence<Token>> =
            parseTerm(tokens).let { (term, operations) ->
                val (ops, remaining) = parseOperations(operations)

                Expression(term, ops) to remaining
            }

        private fun parseTerm(tokens: Sequence<Token>) =
            tokens.first().let { first ->
                when (first) {
                    is Open -> {
                        val (expression, remaining) = parseExpression(tokens.drop(1))
                        if (remaining.first() != Close) throw IllegalStateException("Expected closing bracket")
                        expression to remaining.drop(1)
                    }
                    is Number -> Constant(first.value) to tokens.drop(1)
                    else -> throw IllegalStateException("Unexpected token $first")
                }
            }

        private tailrec fun parseOperations(
            remaining: Sequence<Token>, operations: MutableList<Operation> = mutableListOf()
        ): Pair<List<Operation>, Sequence<Token>> =
            when {
                remaining.none() -> operations to remaining
                remaining.first() is Close -> operations to remaining
                else -> {
                    val (operation, rest) = when (part) {
                        Part.One -> parseOperation1(remaining)
                        Part.Two -> parseOperation2(remaining)
                    }
                    operations.add(operation)
                    parseOperations(rest, operations)
                }
            }

        private fun parseOperation1(tokens: Sequence<Token>): Pair<Operation, Sequence<Token>> {
            val operation = tokens.first()
            val (term, remaining) = parseTerm(tokens.drop(1))

            return when (operation) {
                is Plus -> Add(term) to remaining
                is Star -> Multiply(term) to remaining
                else -> throw IllegalStateException("Unexpected token: $operation")
            }
        }

        private fun parseOperation2(tokens: Sequence<Token>) =
            when (val operation = tokens.first()) {
                is Plus -> {
                    val (term, remaining) = parseTerm(tokens.drop(1))
                    Add(term) to remaining
                }
                is Star -> {
                    val (expr, remaining) = parseExpression(tokens.drop(1))
                    Multiply(expr) to remaining
                }
                else -> throw IllegalStateException("Unexpected token: $operation")
            }
    }
}