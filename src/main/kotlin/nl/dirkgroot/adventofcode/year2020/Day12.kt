package nl.dirkgroot.adventofcode.year2020

import nl.dirkgroot.adventofcode.util.Input
import nl.dirkgroot.adventofcode.util.Puzzle
import java.lang.IllegalStateException
import kotlin.math.abs

class Day12(input: Input) : Puzzle() {
    private val instructions by lazy { input.lines().map { it[0] to it.substring(1).toInt() } }

    class State(val waypoint: Pair<Int, Int>, val ship: Pair<Int, Int> = 0 to 0) {
        val manhattanDistance = abs(ship.first) + abs(ship.second)
    }

    override fun part1() = followInstructions(State(waypoint = 1 to 0), ::moveShip).manhattanDistance

    override fun part2() = followInstructions(State(waypoint = 10 to 1), ::moveWaypoint).manhattanDistance

    private fun followInstructions(initialState: State, moveCommand: (State, Char, Int) -> State) =
        instructions.fold(initialState) { state, (action, value) ->
            val (waypointX, waypointY) = state.waypoint
            val (shipX, shipY) = state.ship
            when (action) {
                'F' -> State(state.waypoint, shipX + waypointX * value to shipY + waypointY * value)
                'R' -> State(rotate(waypointX, waypointY, value), state.ship)
                'L' -> State(rotate(waypointX, waypointY, -value), state.ship)
                else -> moveCommand(state, action, value)
            }
        }

    private fun moveShip(state: State, direction: Char, value: Int) =
        state.ship.let { (x, y) -> State(state.waypoint, move(x, y, direction, value)) }

    private fun moveWaypoint(state: State, direction: Char, value: Int) =
        state.waypoint.let { (x, y) -> State(move(x, y, direction, value), state.ship) }

    private fun move(x: Int, y: Int, direction: Char, value: Int) =
        when (direction) {
            'N' -> x to y + value
            'E' -> x + value to y
            'S' -> x to y - value
            'W' -> x - value to y
            else -> throw IllegalStateException("Invalid direction: ($direction, $value)")
        }

    private tailrec fun rotate(dirX: Int, dirY: Int, degrees: Int): Pair<Int, Int> =
        if (degrees % 360 == 0) dirX to dirY
        else rotate(dirY, -dirX, degrees - 90)
}