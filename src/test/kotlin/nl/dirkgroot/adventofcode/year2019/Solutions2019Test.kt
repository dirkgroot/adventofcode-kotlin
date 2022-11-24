package nl.dirkgroot.adventofcode.year2019

import nl.dirkgroot.adventofcode.base.AbstractYearSolutionsTest
import nl.dirkgroot.adventofcode.util.ClasspathResourceInput

class Solutions2019Test : AbstractYearSolutionsTest() {
    override val testCases = listOf(
        TestCase(Day01(ClasspathResourceInput(2019, 1)), 3406527, 5106932),
        TestCase(Day02(ClasspathResourceInput(2019, 2)), 6730673, 3749),
    )
}
