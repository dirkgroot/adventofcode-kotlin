package nl.dirkgroot.adventofcode.year2022

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import nl.dirkgroot.adventofcode.util.input
import nl.dirkgroot.adventofcode.util.invokedWith

private fun solution1(input: String) = solve(input, 20, 3L)
private fun solution2(input: String) = solve(input, 10000, 1L)

private fun solve(input: String, rounds: Int, divideBy: Long) = parseMonkeys(input).let { monkeys ->
    val modulo = monkeys.map { it.divisibleBy }.reduce(Long::times)
    repeat(rounds) { round(monkeys, divideBy, modulo) }
    monkeys.monkeyBusiness()
}

private fun parseMonkeys(input: String) = input.split("\n\n").map { monkey -> Monkey.parse(monkey) }

private fun round(monkeys: List<Monkey>, divideBy: Long, modulo: Long) {
    monkeys.forEach { monkey ->
        repeat(monkey.items.size) {
            val (item, destination) = monkey.inspect(divideBy, modulo)
            monkeys[destination].items.add(item)
        }
    }
}

private fun List<Monkey>.monkeyBusiness() = map { it.inspectCount }.sortedDescending().take(2).reduce(Long::times)

private data class Monkey(
    val items: MutableList<Long>,
    val operation: (Long) -> Long,
    val divisibleBy: Long,
    val ifTrue: Int,
    val ifFalse: Int,
    var inspectCount: Long = 0L
) {
    fun inspect(divideBy: Long, modulo: Long): Pair<Long, Int> {
        inspectCount++

        val item = operation(items.removeAt(0)) / divideBy % modulo

        return if (item % divisibleBy == 0L) item to ifTrue
        else item to ifFalse
    }

    companion object {
        fun parse(input: String): Monkey {
            val lines = input.lines()
            val items = lines[1].drop(18).split(", ").map { it.toLong() }.toMutableList()
            val operation = lines[2].drop(23).split(" ").let {
                if (it[1] == "old") {
                    if (it[0] == "*") { old: Long -> old * old }
                    else { old: Long -> old + old }
                } else {
                    val num = it[1].toLong()
                    if (it[0] == "*") { old: Long -> old * num }
                    else { old: Long -> old + num }
                }
            }
            val divisibleBy = lines[3].drop(21).toLong()
            val ifTrue = lines[4].drop(29).toInt()
            val ifFalse = lines[5].drop(30).toInt()

            return Monkey(items, operation, divisibleBy, ifTrue, ifFalse)
        }
    }
}

//===============================================================================================\\

private const val YEAR = 2022
private const val DAY = 11

class Day11Test : StringSpec({
    "example part 1" { ::solution1 invokedWith exampleInput shouldBe 10605L }
    "part 1 solution" { ::solution1 invokedWith input(YEAR, DAY) shouldBe 54054L }
    "example part 2" { ::solution2 invokedWith exampleInput shouldBe 2713310158L }
    "part 2 solution" { ::solution2 invokedWith input(YEAR, DAY) shouldBe 14314925001L }
})

private val exampleInput =
    """
        Monkey 0:
          Starting items: 79, 98
          Operation: new = old * 19
          Test: divisible by 23
            If true: throw to monkey 2
            If false: throw to monkey 3
        
        Monkey 1:
          Starting items: 54, 65, 75, 74
          Operation: new = old + 6
          Test: divisible by 19
            If true: throw to monkey 2
            If false: throw to monkey 0
        
        Monkey 2:
          Starting items: 79, 60, 97
          Operation: new = old * old
          Test: divisible by 13
            If true: throw to monkey 1
            If false: throw to monkey 3
        
        Monkey 3:
          Starting items: 74
          Operation: new = old + 3
          Test: divisible by 17
            If true: throw to monkey 0
            If false: throw to monkey 1
    """.trimIndent()
