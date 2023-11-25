package nl.dirkgroot.adventofcode.year2022

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import nl.dirkgroot.adventofcode.util.input
import nl.dirkgroot.adventofcode.util.invokedWith
import kotlin.math.ceil
import kotlin.math.max

private fun solution1(input: String) = parseBlueprints(input)
    .sumOf { it.id * maxGeodes(it, 24) }

private fun solution2(input: String) = parseBlueprints(input).take(3)
    .map { maxGeodes(it, 32) }
    .reduce(Int::times)

private fun maxGeodes(blueprint: Blueprint, minutes: Int): Int {
    val queue = ArrayDeque<State>().apply { add(State(blueprint, minutes)) }
    var maxGeodes = 0

    while (queue.isNotEmpty()) {
        val first = queue.removeFirst()
        val minutesRemaining = minutes - first.minutesElapsed
        if (first.maxPotentialGeodes(minutesRemaining) > maxGeodes)
            queue.addAll(first.subStates)
        maxGeodes = max(maxGeodes, first.geodesAt(minutes))
    }
    return maxGeodes
}

private fun parseBlueprints(input: String) = input.lineSequence()
    .map {
        "\\d+".toRegex().findAll(it).map { c -> c.value.toInt() }.toList().let { tokens ->
            Blueprint(
                tokens[0],
                Resources(ore = tokens[1]),
                Resources(ore = tokens[2]),
                Resources(ore = tokens[3], clay = tokens[4]),
                Resources(ore = tokens[5], obsidian = tokens[6])
            )
        }
    }.toList()

private data class State(
    val blueprint: Blueprint,
    val maxElapsed: Int,
    val resources: Resources = Resources(),
    val minutesElapsed: Int = 0,
    val oreRobots: Int = 1,
    val clayRobots: Int = 0,
    val obsidianRobots: Int = 0,
    val geodeRobots: Int = 0,
    val geodesCracked: Int = 0
) {
    private val maxOreCost = maxOf(
        blueprint.oreRobot.ore, blueprint.clayRobot.ore, blueprint.obsidianRobot.ore, blueprint.geodeRobot.ore
    )
    private val maxClayCost = blueprint.obsidianRobot.clay
    private val maxObsCost = blueprint.geodeRobot.obsidian

    val subStates: List<State>
        get() = sequence {
            if (oreRobots < maxOreCost) yield(buyOreRobot())
            if (clayRobots < maxClayCost) yield(buyClayRobot())
            if (clayRobots > 0 && obsidianRobots < maxObsCost) yield(buyObsidianRobot())
            if (obsidianRobots > 0) yield(buyGeodeRobot())
        }.filter { it.minutesElapsed <= maxElapsed }.toList()

    private fun buyOreRobot() = (minutesUntil(blueprint.oreRobot) + 1).let { minutes ->
        copy(
            minutesElapsed = minutesElapsed + minutes,
            resources = resourcesAfter(minutes) - blueprint.oreRobot,
            oreRobots = oreRobots + 1,
            geodesCracked = geodesAfter(minutes),
        )
    }

    private fun buyClayRobot() = (minutesUntil(blueprint.clayRobot) + 1).let { minutes ->
        copy(
            minutesElapsed = minutesElapsed + minutes,
            resources = resourcesAfter(minutes) - blueprint.clayRobot,
            clayRobots = clayRobots + 1,
            geodesCracked = geodesAfter(minutes),
        )
    }

    private fun buyObsidianRobot() = (minutesUntil(blueprint.obsidianRobot) + 1).let { minutes ->
        copy(
            minutesElapsed = minutesElapsed + minutes,
            resources = resourcesAfter(minutes) - blueprint.obsidianRobot,
            obsidianRobots = obsidianRobots + 1,
            geodesCracked = geodesAfter(minutes),
        )
    }

    private fun buyGeodeRobot() = (minutesUntil(blueprint.geodeRobot) + 1).let { minutes ->
        copy(
            minutesElapsed = minutesElapsed + minutes,
            resources = resourcesAfter(minutes) - blueprint.geodeRobot,
            geodeRobots = geodeRobots + 1,
            geodesCracked = geodesAfter(minutes),
        )
    }

    private fun minutesUntil(goal: Resources) =
        if (resources.isEnoughFor(goal)) 0
        else maxOf(
            ceil((goal.ore - resources.ore).toDouble() / oreRobots),
            if (clayRobots > 0) ceil((goal.clay - resources.clay).toDouble() / clayRobots) else 0.0,
            if (obsidianRobots > 0) ceil((goal.obsidian - resources.obsidian).toDouble() / obsidianRobots) else 0.0,
        ).toInt()

    private fun resourcesAfter(minutes: Int) = resources.copy(
        ore = resources.ore + oreRobots * minutes,
        clay = resources.clay + clayRobots * minutes,
        obsidian = resources.obsidian + obsidianRobots * minutes,
    )

    fun geodesAt(minutes: Int) = geodesAfter(minutes - minutesElapsed)

    private fun geodesAfter(minutes: Int) = geodesCracked + geodeRobots * minutes

    fun maxPotentialGeodes(minutes: Int) = geodesCracked + minutes * geodeRobots + (1 until minutes).sum()
}

private data class Blueprint(
    val id: Int,
    val oreRobot: Resources,
    val clayRobot: Resources,
    val obsidianRobot: Resources,
    val geodeRobot: Resources
)

private data class Resources(val ore: Int = 0, val clay: Int = 0, val obsidian: Int = 0) {
    operator fun minus(other: Resources) =
        copy(ore = ore - other.ore, clay = clay - other.clay, obsidian = obsidian - other.obsidian)

    fun isEnoughFor(cost: Resources) = ore >= cost.ore && clay >= cost.clay && obsidian >= cost.obsidian
}

//===============================================================================================\\

private const val YEAR = 2022
private const val DAY = 19

class Day19Test : StringSpec({
    "example part 1" { ::solution1 invokedWith exampleInput shouldBe 33 }
    "part 1 solution" { ::solution1 invokedWith input(YEAR, DAY) shouldBe 1356 }
    "example part 2" { ::solution2 invokedWith exampleInput shouldBe 3472 }
    "part 2 solution" { ::solution2 invokedWith input(YEAR, DAY) shouldBe 27720 }
})

private val exampleInput =
    """
        Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.
        Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian.
    """.trimIndent()
