package nl.dricus.adventofcode.year2021

import nl.dricus.adventofcode.util.Input
import nl.dricus.adventofcode.util.Puzzle
import kotlin.math.abs

class Day19(input: Input) : Puzzle() {
    val scanners by lazy {
        input.string().split("\n\n".toRegex())
            .map { scanner ->
                Scanner(
                    "(-?\\d+),(-?\\d+),(-?\\d+)".toRegex()
                        .findAll(scanner)
                        .map { beaconMatch ->
                            Point3D(beaconMatch.groupValues[1].toInt(), beaconMatch.groupValues[2].toInt(), beaconMatch.groupValues[3].toInt())
                        }
                        .toList()
                )
            }
    }

    data class Point3D(var x: Int, var y: Int, var z: Int) {
        fun getRotations() = listOf(
            Point3D(x, y, z), Point3D(x, z, -y), Point3D(x, -z, y), Point3D(x, -y, -z),
            Point3D(y, z, x), Point3D(y, x, -z), Point3D(y, -x, z), Point3D(y, -z, -x),
            Point3D(z, x, y), Point3D(z, y, -x), Point3D(z, -y, x), Point3D(z, -x, -y),
            Point3D(-x, -z, -y), Point3D(-x, -y, z), Point3D(-x, y, -z), Point3D(-x, z, y),
            Point3D(-y, -x, -z), Point3D(-y, -z, x), Point3D(-y, z, -x), Point3D(-y, x, z),
            Point3D(-z, -y, -x), Point3D(-z, -x, y), Point3D(-z, x, -y), Point3D(-z, y, x),
        )

        operator fun minus(other: Point3D): Point3D {
            return Point3D(x - other.x, y - other.y, z - other.z)
        }

        operator fun plus(other: Point3D): Point3D {
            return Point3D(x + other.x, y + other.y, z + other.z)
        }

        override fun toString() = "($x, $y, $z)"
    }

    data class Scanner(val beacons: List<Point3D>, val permutationOf: Scanner? = null, val offset: Point3D = Point3D(0, 0, 0)) {
        private val permutations by lazy {
            beacons.map { it.getRotations() }
                .let { beaconPermutations ->
                    (0..23).asSequence()
                        .map { index -> Scanner(beaconPermutations.map { it[index] }) }
                }
        }

        fun findOverlappingPermutation(other: Scanner): Scanner? {
            return permutations
                .map { permutation ->
                    permutation.findOverlapOffset(other)?.let { offset ->
                        val beacons = permutation.beacons.map { it + offset }
                        Scanner(beacons, this, offset)
                    }
                }
                .filterNotNull()
                .firstOrNull()
        }

        private fun findOverlapOffset(other: Scanner): Point3D? {
            val myBeaconOffsets = beacons.associateWith { source ->
                beacons.map { destination -> destination - source }.toSet()
            }
            val otherBeaconOffsets = other.beacons.associateWith { source ->
                other.beacons.map { destination -> destination - source }.toSet()
            }

            return myBeaconOffsets.mapNotNull { (beacon, offsets) ->
                otherBeaconOffsets.asSequence().filter { (_, otherOffsets) ->
                    (offsets intersect otherOffsets).size >= 12
                }.firstOrNull()?.let { (otherBeacon, _) ->
                    beacon to otherBeacon
                }
            }.firstOrNull()?.let { (first, second) ->
                second - first
            }
        }
    }

    override fun part1(): Int =
        adjustedScanners().flatMap { it.beacons }.distinct().size

    override fun part2(): Int =
        adjustedScanners().map { it.offset }.let { offsets ->
            offsets.flatMap { source ->
                offsets.map { destination ->
                    abs(destination.x - source.x) + abs(destination.y - source.y) + abs(destination.z - source.z)
                }
            }.maxOrNull()!!
        }

    private fun adjustedScanners(): List<Scanner> {
        val todo = scanners.toMutableList()
        val adjustedScanners = mutableListOf(todo.removeFirst())

        while (todo.isNotEmpty()) {
            val overlaps = todo.mapNotNull { scannerInProgress ->
                adjustedScanners.asSequence()
                    .mapNotNull { matchCandidate -> scannerInProgress.findOverlappingPermutation(matchCandidate) }
                    .firstOrNull()
            }
            adjustedScanners.addAll(overlaps)
            overlaps.forEach { todo.remove(it.permutationOf) }
        }

        return adjustedScanners
    }
}
