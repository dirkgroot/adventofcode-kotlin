package nl.dricus.adventofcode.base

import nl.dricus.adventofcode.util.Puzzle
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals

abstract class AbstractYearSolutionsTest {
    protected data class TestCase(val puzzle: Puzzle, val part1Solution: Any? = null, val part2Solution: Any? = null)

    protected abstract val testCases: List<TestCase>

    @TestFactory
    fun solutions() = testCases.flatMap { testCase ->
        val name = testCase.puzzle::class.java.simpleName

        listOf(
            DynamicTest.dynamicTest("$name part 1") {
                if (testCase.part1Solution != null)
                    assertEquals(testCase.part1Solution, testCase.puzzle.part1())
                else
                    println("$name part 1: " + testCase.puzzle.part1())
            },
            DynamicTest.dynamicTest("$name part 2") {
                if (testCase.part2Solution != null)
                    assertEquals(testCase.part2Solution, testCase.puzzle.part2())
                else
                    println("$name part 2: " + testCase.puzzle.part2())
            }
        )
    }
}