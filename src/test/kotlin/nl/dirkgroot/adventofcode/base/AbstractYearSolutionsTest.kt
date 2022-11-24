package nl.dirkgroot.adventofcode.base

import nl.dirkgroot.adventofcode.util.Puzzle
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

abstract class AbstractYearSolutionsTest {
    protected data class TestCase(val puzzle: Puzzle, val part1Solution: Any? = null, val part2Solution: Any? = null)

    protected abstract val testCases: List<TestCase>

    @ExperimentalTime
    @TestFactory
    fun solutions() = testCases.flatMap { testCase ->
        val name = testCase.puzzle::class.java.simpleName

        listOf(
            DynamicTest.dynamicTest("$name part 1") {
                val timedValue = measureTimedValue { testCase.puzzle.part1() }

                if (testCase.part1Solution != null)
                    assertEquals(testCase.part1Solution, timedValue.value)
                else
                    println("$name part 1: " + timedValue.value)

                println("$name part 1 took ${timedValue.duration}")
            },
            DynamicTest.dynamicTest("$name part 2") {
                val timedValue = measureTimedValue { testCase.puzzle.part2() }

                if (testCase.part2Solution != null)
                    assertEquals(testCase.part2Solution, timedValue.value)
                else
                    println("$name part 2: " + timedValue.value)

                println("$name part 2 took ${timedValue.duration}")
            }
        )
    }
}