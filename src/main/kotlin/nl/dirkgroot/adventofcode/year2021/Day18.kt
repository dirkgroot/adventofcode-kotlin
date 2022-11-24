package nl.dirkgroot.adventofcode.year2021

import nl.dirkgroot.adventofcode.util.Input
import nl.dirkgroot.adventofcode.util.Puzzle
import java.util.Stack
import kotlin.IllegalStateException
import kotlin.math.max

class Day18(input: Input) : Puzzle() {
    sealed interface Node {
        val depth: Int
        val explodes: Boolean
        val mustExplode: Boolean
        val splits: Boolean
        val isReduced: Boolean
        val magnitude: Long

        fun assertDepth(d: Int)

        fun increaseDepth(): Node

        fun add(other: Node) = reduce(Intermediate(1, increaseDepth(), other.increaseDepth()))

        data class Intermediate(override val depth: Int, var left: Node, var right: Node) : Node {
            override val explodes: Boolean
                get() = depth > 4 && left is Leaf && right is Leaf
            override val splits = false
            override val isReduced: Boolean
                get() = !explodes && left.isReduced && right.isReduced
            override val mustExplode: Boolean
                get() = explodes || left.mustExplode || right.mustExplode
            override val magnitude: Long
                get() = (3L * left.magnitude) + (2L * right.magnitude)

            override fun assertDepth(d: Int) {
                if (depth != d) throw IllegalStateException("Invalid depth!")
                left.assertDepth(d + 1)
                right.assertDepth(d + 1)
            }

            override fun increaseDepth() = Intermediate(depth + 1, left.increaseDepth(), right.increaseDepth())

            override fun toString(): String {
                if (explodes)
                    return "$ANSI_RED[$left$ANSI_RED,$right$ANSI_RED]$ANSI_RESET"
                return "[$left,$right]"
            }
        }

        data class Leaf(override val depth: Int, var value: Int) : Node {
            override val explodes = false
            override val mustExplode = false
            override val splits: Boolean
                get() = value > 9
            override val isReduced: Boolean
                get() = !splits
            override val magnitude: Long
                get() = value.toLong()

            override fun assertDepth(d: Int) {
                if (depth != d) throw IllegalStateException("Invalid depth!")
            }

            override fun increaseDepth() = copy(depth = depth + 1)

            fun split(): Intermediate {
                val left = value / 2
                val right = value - left
                return Intermediate(depth, Leaf(depth + 1, left), Leaf(depth + 1, right))
            }

            override fun toString(): String {
                if (splits)
                    return "$ANSI_GREEN$value$ANSI_RESET"
                return value.toString()
            }
        }
    }

    companion object {
        const val ANSI_RESET = "\u001B[0m"
        const val ANSI_RED = "\u001B[31m"
        const val ANSI_GREEN = "\u001B[32m"

        fun parse(lines: List<String>) = lines.map { parseNumber(it) }

        fun parseNumber(number: String): Node {
            val stack = Stack<Node>()
            var depth = 1

            Regex("(\\[|]|\\d+)").findAll(number).forEach {
                when (it.value) {
                    "[" -> depth++
                    "]" -> {
                        depth--
                        val right = stack.pop()
                        val left = stack.pop()
                        stack.push(Node.Intermediate(depth, left, right))
                    }
                    else -> stack.push(Node.Leaf(depth, it.value.toInt()))
                }
            }
            return stack.single()
        }

        fun explode(number: Node): Node {
            val nodes = allNodes(number).toList()
            val explodeIndex = nodes.indexOfFirst { it.explodes }

            if (explodeIndex < 0) return number

            val explode = nodes[explodeIndex] as Node.Intermediate

            nodes.take(explodeIndex)
                .filterIsInstance<Node.Leaf>()
                .lastOrNull()
                ?.let {
                    it.value += (explode.left as Node.Leaf).value
                }

            nodes.drop(explodeIndex)
                .filterIsInstance<Node.Leaf>()
                .drop(2)
                .firstOrNull()
                ?.let {
                    it.value += (explode.right as Node.Leaf).value
                }

            nodes.filterIsInstance<Node.Intermediate>()
                .first { it.left === explode || it.right === explode }
                .let {
                    if (it.left === explode) {
                        it.left = Node.Leaf(it.left.depth, 0)
                    } else if (it.right === explode) {
                        it.right = Node.Leaf(it.right.depth, 0)
                    }
                }

            return number
        }

        fun split(number: Node): Node {
            val nodes = allNodes(number)

            nodes.firstOrNull { it.splits }?.let { split ->
                nodes.filterIsInstance<Node.Intermediate>()
                    .first { it.left === split || it.right === split }
                    .let { splitParent ->
                        if (splitParent.left.splits)
                            splitParent.left = (splitParent.left as Node.Leaf).split()
                        else if (splitParent.right.splits)
                            splitParent.right = (splitParent.right as Node.Leaf).split()
                    }
            }

            return number
        }

        private fun allNodes(number: Node) = sequence {
            val stack = Stack<Node>()

            stack.push(number)
            while (stack.isNotEmpty()) {
                val node = stack.pop()
                yield(node)
                if (node is Node.Intermediate) {
                    stack.push(node.right)
                    stack.push(node.left)
                }
            }
        }

        fun reduce(number: Node): Node {
            var count = 0

            @Suppress("unused")
            fun debug(n: Node) {
                val nodes = allNodes(n).toList()
                val firstExplode = nodes.firstOrNull { it.explodes }
                val firstSplit = nodes.firstOrNull { it.splits }
                println("${String.format("%03d", ++count)} $number (ex: $firstExplode | sp: $firstSplit)")
            }

//            debug(number)
            while (!number.isReduced) {
                number.assertDepth(1)
                if (number.mustExplode)
                    explode(number)
                else
                    split(number)
//                debug(number)
            }

            return number
        }
    }

    private val input by lazy { parse(input.lines()) }

    override fun part1() =
        input.reduce { acc, node ->
            acc.add(node)
        }.magnitude

    override fun part2(): Any {
        var largestMagnitude = 0L

        input.forEach { n1 ->
            input.forEach { n2 ->
                if (n1 !== n2) {
                    val magnitude = n1.add(n2).magnitude
                    largestMagnitude = max(magnitude, largestMagnitude)
                }
            }
        }
        return largestMagnitude
    }
}