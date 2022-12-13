package nl.dirkgroot.adventofcode.year2022

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import nl.dirkgroot.adventofcode.util.input
import nl.dirkgroot.adventofcode.util.invokedWith

private fun solution1(input: String) = parse(input)
    .mapIndexed { index, packets -> index to packets }
    .filter { (_, packets) -> packets.first < packets.second }
    .sumOf { (index, _) -> index + 1 }

private val div1 = parseEntry("[[2]]")
private val div2 = parseEntry("[[6]]")
private fun solution2(input: String) = parse(input).flatMap { it.toList() }
    .plus(listOf(div1, div2))
    .sorted().let { (it.indexOf(div1) + 1) * (it.indexOf(div2) + 1) }

private fun parse(input: String) = input.split("\n\n")
    .map { it.split("\n") }
    .map { (l, r) -> parseEntry(l) to parseEntry(r) }

private fun parseEntry(input: String) = "\\[|]|\\d+".toRegex().findAll(input)
    .map { match -> match.value }
    .let { tokens -> parse(tokens.iterator()) ?: throw IllegalStateException() }

private fun parse(tokens: Iterator<String>): Entry? =
    when (val token = tokens.next()) {
        "]" -> null
        "[" -> ListEntry(generateSequence { if (tokens.hasNext()) parse(tokens) else null }.toList())
        else -> IntEntry(token.toInt())
    }

private sealed interface Entry : Comparable<Entry>

private data class ListEntry(val values: List<Entry>) : Entry {
    override fun compareTo(other: Entry): Int = when (other) {
        is IntEntry -> compareTo(ListEntry(listOf(other)))
        is ListEntry -> values.zip(other.values)
            .map { (a, b) -> a.compareTo(b) }
            .firstOrNull { it != 0 } ?: values.size.compareTo(other.values.size)
    }
}

private data class IntEntry(val value: Int) : Entry {
    override fun compareTo(other: Entry) = when (other) {
        is IntEntry -> value.compareTo(other.value)
        is ListEntry -> ListEntry(listOf(this)).compareTo(other)
    }
}

//===============================================================================================\\

private const val YEAR = 2022
private const val DAY = 13

class Day13Test : StringSpec({
    "example part 1" { ::solution1 invokedWith exampleInput shouldBe 13 }
    "part 1 solution" { ::solution1 invokedWith input(YEAR, DAY) shouldBe 6369 }
    "example part 2" { ::solution2 invokedWith exampleInput shouldBe 140 }
    "part 2 solution" { ::solution2 invokedWith input(YEAR, DAY) shouldBe 25800 }
})

private val exampleInput =
    """
        [1,1,3,1,1]
        [1,1,5,1,1]

        [[1],[2,3,4]]
        [[1],4]

        [9]
        [[8,7,6]]

        [[4,4],4,4]
        [[4,4],4,4,4]

        [7,7,7,7]
        [7,7,7]

        []
        [3]

        [[[]]]
        [[]]

        [1,[2,[3,[4,[5,6,7]]]],8,9]
        [1,[2,[3,[4,[5,6,0]]]],8,9]
    """.trimIndent()
