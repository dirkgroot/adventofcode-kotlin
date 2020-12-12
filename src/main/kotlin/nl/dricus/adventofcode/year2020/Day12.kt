package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.Input
import nl.dricus.adventofcode.util.Puzzle
import java.lang.IllegalStateException
import kotlin.math.abs

class Day12(input: Input) : Puzzle() {
    private val instructions by lazy { input.lines().map { it[0] to it.substring(1).toInt() } }

    class State(val waypoint: Pair<Int, Int> = 1 to 0, val posX: Int = 0, val posY: Int = 0) {
        val manhattanDistance = abs(posX) + abs(posY)
    }

    override fun part1() = followInstructions(State(), ::moveShip).manhattanDistance

    override fun part2() = followInstructions(State(waypoint = 10 to 1), ::moveWaypoint).manhattanDistance

    private fun followInstructions(initialState: State, moveCommand: (State, Char, Int) -> State) =
        instructions.fold(initialState) { state, (action, value) ->
            val (dirX, dirY) = state.waypoint
            when (action) {
                'F' -> State(state.waypoint, state.posX + dirX * value, state.posY + dirY * value)
                'R' -> State(rotate(dirX, dirY, value), state.posX, state.posY)
                'L' -> State(rotate(dirX, dirY, -value), state.posX, state.posY)
                else -> moveCommand(state, action, value)
            }
        }

    private fun moveShip(state: State, direction: Char, value: Int) =
        when (direction) {
            'N' -> State(state.waypoint, state.posX, state.posY + value)
            'E' -> State(state.waypoint, state.posX + value, state.posY)
            'S' -> State(state.waypoint, state.posX, state.posY - value)
            'W' -> State(state.waypoint, state.posX - value, state.posY)
            else -> throw IllegalStateException("Invalid direction: ($direction, $value)")
        }

    private fun moveWaypoint(state: State, direction: Char, value: Int) =
        state.waypoint.let { (dirX, dirY) ->
            when (direction) {
                'N' -> State(dirX to dirY + value, state.posX, state.posY)
                'E' -> State(dirX + value to dirY, state.posX, state.posY)
                'S' -> State(dirX to dirY - value, state.posX, state.posY)
                'W' -> State(dirX - value to dirY, state.posX, state.posY)
                else -> throw IllegalStateException("Invalid direction: ($direction, $value)")
            }
        }

    private tailrec fun rotate(dirX: Int, dirY: Int, degrees: Int): Pair<Int, Int> =
        when (degrees % 360) {
            0 -> dirX to dirY
            else -> rotate(dirY, -dirX, degrees - 90)
        }
}