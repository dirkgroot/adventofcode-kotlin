package nl.dirkgroot.adventofcode.year2021

import nl.dirkgroot.adventofcode.base.AbstractYearSolutionsTest
import nl.dirkgroot.adventofcode.util.ClasspathResourceInput

class Solutions2021Test : AbstractYearSolutionsTest() {
    override val testCases = listOf(
        TestCase(Day18(ClasspathResourceInput(2021, 18)), 4116L, 4638L),
        TestCase(Day19(ClasspathResourceInput(2021, 19)), 459, 19130),
        TestCase(Day24(), 59998426997979, 13621111481315),
        TestCase(Day25(ClasspathResourceInput(2021, 25)), 278)
    )
}
