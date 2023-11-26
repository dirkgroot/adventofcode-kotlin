package nl.dirkgroot.adventofcode.year2022

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import nl.dirkgroot.adventofcode.util.input
import nl.dirkgroot.adventofcode.util.invokedWith
import kotlin.math.abs

private fun solution1(input: String) = parse(input, 1L).let { file ->
    file.mix()
    file.sum()
}

private fun solution2(input: String) = parse(input, 811589153L).let { file ->
    repeat(10) { file.mix() }
    file.sum()
}

private fun parse(input: String, key: Long) = Numbers(input.lineSequence().map { it.toLong() }, key)

private class Numbers(longs: Sequence<Long>, key: Long) {
    private class Entry(val value: Long, var next: Entry? = null, var previous: Entry? = null) {
        override fun toString() = "value: $value"
    }

    private val entries = longs.map { Entry(it * key) }.toList().also { list ->
        list.forEachIndexed { index, entry ->
            entry.previous = list[prev(index, list.size)]
            entry.next = list[next(index, list.size)]
        }
    }

    private fun prev(index: Int, size: Int) = (index + size - 1) % size
    private fun next(index: Int, size: Int) = (index + 1) % size

    fun sum(): Long {
        val zeroElement = entries.first { it.value == 0L }
        return generateSequence(zeroElement.next) { it.next }
            .windowed(1000, 1000)
            .map { it.last().value }
            .take(3)
            .sum()
    }

    fun mix() {
        entries.forEach { entry ->
            if (entry.value == 0L) return@forEach
            if (entry.value % (entries.size - 1) == 0L) return@forEach

            val after = if (entry.value > 0)
                generateSequence(entry) { it.next }
                    .drop((entry.value % (entries.size - 1)).toInt())
                    .first()
            else
                generateSequence(entry.previous) { it.previous }
                    .drop(abs(entry.value % (entries.size - 1)).toInt())
                    .first()
            val before = after.next!!

            entry.previous?.next = entry.next
            entry.next?.previous = entry.previous

            after.next = entry
            before.previous = entry
            entry.next = before
            entry.previous = after
        }
    }

    override fun toString(): String = generateSequence(entries.first { it.value == 0L }) { it.next }
        .take(entries.size)
        .map { it.value }
        .joinToString(",")
}

//===============================================================================================\\

private const val YEAR = 2022
private const val DAY = 20

class Day20Test : StringSpec({
    "modulo" { -10 % 7 shouldBe -3 }
    "example part 1" { ::solution1 invokedWith exampleInput shouldBe 3L }
    "part 1 solution" { ::solution1 invokedWith input(YEAR, DAY) shouldBe 13289L }
    "example part 2" { ::solution2 invokedWith exampleInput shouldBe 1623178306L }
    "part 2 solution" { ::solution2 invokedWith input(YEAR, DAY) shouldBe 2865721299243L }
})

private val exampleInput =
    """
        1
        2
        -3
        3
        -2
        0
        4
    """.trimIndent()
