package nl.dirkgroot.adventofcode.util

class StringInput(private val input: String) : Input {
    override fun lines() = input.split('\n')

    override fun string() = input
}