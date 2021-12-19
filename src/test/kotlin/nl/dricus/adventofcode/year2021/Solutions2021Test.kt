package nl.dricus.adventofcode.year2021

import nl.dricus.adventofcode.base.AbstractYearSolutionsTest
import nl.dricus.adventofcode.util.ClasspathResourceInput

class Solutions2021Test : AbstractYearSolutionsTest() {
    override val testCases = listOf(
        TestCase(Day18(ClasspathResourceInput(2021, 18)), 4116L, 4638L),
    )
}
