package nl.dirkgroot.adventofcode.year2022

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import nl.dirkgroot.adventofcode.util.input
import nl.dirkgroot.adventofcode.util.invokedWith
import kotlin.math.abs

private const val LEFT = 'L'
private const val RIGHT = 'R'

private fun solution1(y: Long) = { input: String ->
    val sensors = parseSensors(input)
    sensors.impossiblePositions(y) - sensors.beaconsAtY(y).count()
}

private fun solution2(maxXY: Long) = { input: String ->
    val (x, y) = parseSensors(input).findDistressBeacon(maxXY)
    x * 4000000L + y
}

private fun parseSensors(input: String) = "-?\\d+".toRegex().findAll(input)
    .map { it.value.toLong() }.windowed(4, step = 4)
    .map { Sensor(it[0], it[1], it[2], it[3]) }.toList()

private fun List<Sensor>.impossiblePositions(y: Long): Long {
    val positions = sortedEndpoints(y)

    var lr = 0
    var start = 0L
    var count = 0L
    positions.forEach { (side, x) ->
        if (lr == 0) start = x
        if (side == LEFT) lr++ else lr--
        if (lr == 0) count += x - start + 1
    }
    return count
}

private fun List<Sensor>.beaconsAtY(y: Long) =
    asSequence().filter { it.beaconY == y }.map { it.beaconX to it.beaconY }.distinct()

private fun List<Sensor>.findDistressBeacon(maxXY: Long) = (0..maxXY).asSequence()
    .map { y -> firstPossibleX(y, maxXY) to y }
    .first { (x, _) -> x < maxXY }

private fun List<Sensor>.firstPossibleX(y: Long, maxX: Long): Long {
    val positions = sortedEndpoints(y)
    var lr = 0
    var end = 0L
    positions.forEach { (side, x) ->
        if (lr == 0 && x > end) return end + 1
        if (side == LEFT) lr++ else lr--
        if (lr == 0) end = x
    }
    return maxX + 1
}

private fun List<Sensor>.sortedEndpoints(y: Long) = asSequence().map { it.xsAt(y) }
    .filter { it != LongRange.EMPTY }
    .flatMap { listOf(LEFT to it.first, RIGHT to it.last) }
    .sortedWith { a, b -> if (a.second != b.second) a.second.compareTo(b.second) else a.first.compareTo(b.first) }

private class Sensor(private val x: Long, private val y: Long, val beaconX: Long, val beaconY: Long) {
    private val distanceToBeacon = abs(x - beaconX) + abs(y - beaconY)

    fun xsAt(sy: Long): LongRange {
        val q = distanceToBeacon - abs(sy - y)
        return if (q >= 0) x - q..x + q else LongRange.EMPTY
    }
}

//===============================================================================================\\

private const val YEAR = 2022
private const val DAY = 15

class Day15Test : StringSpec({
    "example part 1" { solution1(10) invokedWith exampleInput shouldBe 26L }
    "part 1 solution" { solution1(2000000) invokedWith input(YEAR, DAY) shouldBe 4724228L }
    "example part 2" { solution2(20) invokedWith exampleInput shouldBe 56000011L }
    "part 2 solution" { solution2(4000000) invokedWith input(YEAR, DAY) shouldBe 13622251246513L }
})

private val exampleInput =
    """
        Sensor at x=2, y=18: closest beacon is at x=-2, y=15
        Sensor at x=9, y=16: closest beacon is at x=10, y=16
        Sensor at x=13, y=2: closest beacon is at x=15, y=3
        Sensor at x=12, y=14: closest beacon is at x=10, y=16
        Sensor at x=10, y=20: closest beacon is at x=10, y=16
        Sensor at x=14, y=17: closest beacon is at x=10, y=16
        Sensor at x=8, y=7: closest beacon is at x=2, y=10
        Sensor at x=2, y=0: closest beacon is at x=2, y=10
        Sensor at x=0, y=11: closest beacon is at x=2, y=10
        Sensor at x=20, y=14: closest beacon is at x=25, y=17
        Sensor at x=17, y=20: closest beacon is at x=21, y=22
        Sensor at x=16, y=7: closest beacon is at x=15, y=3
        Sensor at x=14, y=3: closest beacon is at x=15, y=3
        Sensor at x=20, y=1: closest beacon is at x=15, y=3
    """.trimIndent()
