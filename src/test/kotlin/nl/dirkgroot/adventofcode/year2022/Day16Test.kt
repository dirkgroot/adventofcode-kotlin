package nl.dirkgroot.adventofcode.year2022

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import nl.dirkgroot.adventofcode.util.input
import nl.dirkgroot.adventofcode.util.invokedWith
import java.util.*

private fun solution1(input: String) = parse(input).maxPressureRelease()
private fun solution2(input: String) = parse(input).maxPressureRelease2()

private fun Map<String, Valve>.maxPressureRelease(): Int {
    val cache = mutableMapOf<String, Int>()

    fun analyze(remaining: Set<String>, current: String, minutesPassed: Int, pressure: Int): Int {
        return if (remaining.isEmpty()) pressure
        else remaining.maxOf { next ->
            val minutesToOpen = cache.getOrPut(current + next) { walkingTime(this, current, next) + 1 }

            if (minutesToOpen + minutesPassed >= 30)
                pressure
            else {
                val nextReleases = (30 - minutesPassed - minutesToOpen) * getValue(next).flowRate
                analyze(remaining - next, next, minutesPassed + minutesToOpen, pressure + nextReleases)
            }
        }
    }

    val withFlowRate = filter { (_, valve) -> valve.flowRate > 0 }
    return analyze(withFlowRate.keys, "AA", 0, 0)
}

private fun Map<String, Valve>.maxPressureRelease2(): Int {
    val cache = mutableMapOf<String, Int>()
    val cache2 = mutableMapOf<String, Int>()
    val withFlowRate = filter { (_, valve) -> valve.flowRate > 0 }

    fun analyze(remaining: Set<String>, current: String, minutesPassed: Int, resetAt: Int): Int {
        return if (remaining.isEmpty()) 0
        else remaining.maxOf { next ->
            val minutesToOpen = cache.getOrPut(current + next) { walkingTime(this, current, next) + 1 }

            if (minutesToOpen + minutesPassed >= 26)
                0
            else {
                val nextReleases = (26 - minutesPassed - minutesToOpen) * getValue(next).flowRate
                nextReleases + if (remaining.size - 1 == resetAt) {
                    val key = (remaining - next).sorted().joinToString("") + "AA" + "0"
                    cache2.getOrPut(key) {
                        analyze(remaining - next, "AA", 0, resetAt)
                    }
                } else {
                    val key = (remaining - next).sorted().joinToString("") + next + (minutesPassed + minutesToOpen)
                    cache2.getOrPut(key) {
                        analyze(remaining - next, next, minutesPassed + minutesToOpen, resetAt)
                    }
                }
            }
        }
    }

    return (1 until withFlowRate.size).maxOf { resetAt ->
        cache2.clear()
        analyze(withFlowRate.keys, "AA", 0, resetAt)
    }
}

private const val INFINITY = 100000000

private fun walkingTime(valves: Map<String, Valve>, start: String, end: String): Int {
    val dist = mutableMapOf<String, Int>()
    val prev = mutableMapOf<String, String>()
    val q = PriorityQueue<String> { o1, o2 -> dist.getValue(o1).compareTo(dist.getValue(o2)) }

    valves.keys.forEach { dist[it] = INFINITY }
    dist[start] = 0
    q.add(start)

    while (q.isNotEmpty()) {
        val u = q.poll()
        if (u == end || dist[u] == INFINITY) break

        valves.getValue(u).tunnelsTo.forEach { v ->
            val alt = dist.getValue(u) + 1
            if (alt < dist.getValue(v)) {
                dist[v] = alt
                prev[v] = u
                q.add(v)
            }
        }
    }
    return dist.getValue(end)
}

private fun parse(input: String) = input.lineSequence().map { line ->
    val match = "Valve ([A-Z]{2}) has flow rate=(\\d+); tunnels? leads? to valves? (.*)".toRegex().matchEntire(line)
        ?: throw IllegalStateException()
    Valve(match.groupValues[1], match.groupValues[2].toInt(), match.groupValues[3].split(", "))
}.map { it.name to it }.toMap()

private data class Valve(val name: String, val flowRate: Int, val tunnelsTo: List<String>)

//===============================================================================================\\

private const val YEAR = 2022
private const val DAY = 16

class Day16Test : StringSpec({
    "example part 1" { ::solution1 invokedWith exampleInput shouldBe 1651 }
    "part 1 solution" { ::solution1 invokedWith input(YEAR, DAY) shouldBe 1724 }
    "example part 2" { ::solution2 invokedWith exampleInput shouldBe 1707 }
    "part 2 solution" { ::solution2 invokedWith input(YEAR, DAY) shouldBe 2283 }
})

private val exampleInput =
    """
        Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
        Valve BB has flow rate=13; tunnels lead to valves CC, AA
        Valve CC has flow rate=2; tunnels lead to valves DD, BB
        Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE
        Valve EE has flow rate=3; tunnels lead to valves FF, DD
        Valve FF has flow rate=0; tunnels lead to valves EE, GG
        Valve GG has flow rate=0; tunnels lead to valves FF, HH
        Valve HH has flow rate=22; tunnel leads to valve GG
        Valve II has flow rate=0; tunnels lead to valves AA, JJ
        Valve JJ has flow rate=21; tunnel leads to valve II
    """.trimIndent()
