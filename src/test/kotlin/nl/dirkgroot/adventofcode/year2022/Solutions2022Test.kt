package nl.dirkgroot.adventofcode.year2022

import nl.dirkgroot.adventofcode.base.AbstractYearSolutionsTest
import nl.dirkgroot.adventofcode.util.ClasspathResourceInput

class Solutions2022Test : AbstractYearSolutionsTest() {
    override val testCases = listOf(
        TestCase(Day01(ClasspathResourceInput(2022, 1)), 69836L, 207968L),
    )
}
