package nl.dirkgroot.adventofcode.year2020

import nl.dirkgroot.adventofcode.util.Input
import nl.dirkgroot.adventofcode.util.Puzzle
import kotlin.math.max
import kotlin.math.min

class Day17(input: Input) : Puzzle() {
    class Slice(val cubes: List<List<Boolean>>)

    private val dimension by lazy {
        val inputSlice = parseSlice(input.lines())
        val inactiveSlice = inactiveCopyOf(inputSlice)

        List(6) { inactiveSlice } + inputSlice + List(6) { inactiveSlice }
    }

    private val dimension4d by lazy {
        val emptyDimension = List(dimension.size) {
            Slice(List(dimension[0].cubes.size) {
                List(dimension[0].cubes[0].size) { false }
            })
        }

        List(6) { emptyDimension } + listOf(dimension) + List(6) { emptyDimension }
    }

    private fun parseSlice(lines: List<String>): Slice {
        val width = lines[0].length + 12
        val height = lines.size + 12

        return Slice(
            List(height) { index ->
                if (index < 6 || index >= (height - 6)) List(width) { false }
                else List(6) { false } + lines[index - 6].map { it == '#' } + List(6) { false }
            }
        )
    }

    private fun inactiveCopyOf(source: Slice) = Slice(source.cubes.map { it.map { false } })

    override fun part1(): Int {
        val dim = (1..6).fold(dimension) { dim, _ ->
            generation(dim)
        }

        return countActiveCells(dim)
    }

    override fun part2(): Int {
        val dim = (1..6).fold(dimension4d) { dim, _ ->
            generation4d(dim)
        }

        return countActiveCells4d(dim)
    }

    private fun countActiveCells4d(dim: List<List<Slice>>) =
        dim.sumOf { countActiveCells(it) }

    private fun countActiveCells(dim: List<Slice>) = dim.sumOf {
        it.cubes.sumOf { row ->
            row.count { active -> active }
        }
    }

    private fun generation4d(dim: List<List<Slice>>): List<List<Slice>> {
        return dim.mapIndexed { w, wdim ->
            wdim.mapIndexed { z, slice ->
                Slice(slice.cubes.mapIndexed { y, line ->
                    line.mapIndexed { x, _ ->
                        shouldBeActive4d(dim, x, y, z, w)
                    }
                })
            }
        }
    }

    private fun generation(dim: List<Slice>) = dim.mapIndexed { z, slice ->
        Slice(slice.cubes.mapIndexed { y, line ->
            line.mapIndexed { x, _ ->
                shouldBeActive(dim, x, y, z)
            }
        })
    }

    private fun shouldBeActive4d(dim: List<List<Slice>>, x: Int, y: Int, z: Int, w: Int) =
        if (dim[w][z].cubes[y][x]) activeNeighbors4d(dim, x, y, z, w) in 2..3
        else activeNeighbors4d(dim, x, y, z, w) == 3

    private fun shouldBeActive(dim: List<Slice>, x: Int, y: Int, z: Int) =
        if (dim[z].cubes[y][x]) activeNeighbors(dim, x, y, z) in 2..3
        else activeNeighbors(dim, x, y, z) == 3

    private fun activeNeighbors4d(dim: List<List<Slice>>, x: Int, y: Int, z: Int, w: Int): Int {
        return (max(0, w - 1)..min(dim.lastIndex - 1, w + 1)).sumOf { wn ->
            (max(0, z - 1)..min(dim[w].lastIndex - 1, z + 1)).sumOf { zn ->
                (max(0, y - 1)..min(dim[w][z].cubes.lastIndex - 1, y + 1)).sumOf { yn ->
                    (max(0, x - 1)..min(dim[w][z].cubes[y].lastIndex - 1, x + 1)).count { xn ->
                        if (xn == x && yn == y && zn == z && wn == w) false
                        else dim[wn][zn].cubes[yn][xn]
                    }
                }
            }
        }
    }

    private fun activeNeighbors(dim: List<Slice>, x: Int, y: Int, z: Int): Int {
        return (max(0, z - 1)..min(dim.lastIndex - 1, z + 1)).sumOf { zn ->
            (max(0, y - 1)..min(dim[z].cubes.lastIndex - 1, y + 1)).sumOf { yn ->
                (max(0, x - 1)..min(dim[z].cubes[y].lastIndex - 1, x + 1)).count { xn ->
                    if (xn == x && yn == y && zn == z) false
                    else dim[zn].cubes[yn][xn]
                }
            }
        }
    }
}