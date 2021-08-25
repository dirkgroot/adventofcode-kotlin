package nl.dricus.adventofcode.year2019

import nl.dricus.adventofcode.base.AbstractYearSolutionsTest
import nl.dricus.adventofcode.util.ClasspathResourceInput

class Solutions2019Test : AbstractYearSolutionsTest() {
    override val testCases = listOf(
        TestCase(Day02(ClasspathResourceInput(2019, 2)), 6730673),
    )
}
