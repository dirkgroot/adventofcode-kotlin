package nl.dirkgroot.adventofcode.year2022

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import nl.dirkgroot.adventofcode.util.input
import nl.dirkgroot.adventofcode.util.invokedWith

private fun solution1(input: String) = input.findMarker(4)
private fun solution2(input: String) = input.findMarker(14)

private fun String.findMarker(size: Int) = windowed(size, step = 1)
    .indexOfFirst { it.toSet().size == size } + size

//===============================================================================================\\

private const val YEAR = 2022
private const val DAY = 6

class Day06Test : StringSpec({
    "example part 1" {
        ::solution1 invokedWith "mjqjpqmgbljsphdztnvjfqwrcgsmlb" shouldBe 7
        ::solution1 invokedWith "bvwbjplbgvbhsrlpgdmjqwftvncz" shouldBe 5
        ::solution1 invokedWith "nppdvjthqldpwncqszvftbrmjlhg" shouldBe 6
        ::solution1 invokedWith "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" shouldBe 10
        ::solution1 invokedWith "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw" shouldBe 11
    }
    "part 1 solution" { ::solution1 invokedWith input(YEAR, DAY) shouldBe 1896 }
    "example part 2" {
        ::solution2 invokedWith "mjqjpqmgbljsphdztnvjfqwrcgsmlb" shouldBe 19
        ::solution2 invokedWith "bvwbjplbgvbhsrlpgdmjqwftvncz" shouldBe 23
        ::solution2 invokedWith "nppdvjthqldpwncqszvftbrmjlhg" shouldBe 23
        ::solution2 invokedWith "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" shouldBe 29
        ::solution2 invokedWith "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw" shouldBe 26
    }
    "part 2 solution" { ::solution2 invokedWith input(YEAR, DAY) shouldBe 3452 }
})
