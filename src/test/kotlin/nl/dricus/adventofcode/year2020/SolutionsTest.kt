package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.base.AbstractYearSolutionsTest
import nl.dricus.adventofcode.util.ClasspathResourceInput

class SolutionsTest : AbstractYearSolutionsTest() {
    override val testCases = listOf(
        TestCase(Day01(ClasspathResourceInput(2020, 1)), 365619, 236873508),
        TestCase(Day02(ClasspathResourceInput(2020, 2)), 424, 747),
        TestCase(Day03(ClasspathResourceInput(2020, 3)), 153L, 2421944712L),
        TestCase(Day04(ClasspathResourceInput(2020, 4)), 247, 145),
        TestCase(Day05(ClasspathResourceInput(2020, 5)), 915, 699),
        TestCase(Day06(ClasspathResourceInput(2020, 6)), 6170, 2947),
        TestCase(Day07(ClasspathResourceInput(2020, 7)), 161, 30899),
        TestCase(Day08(ClasspathResourceInput(2020, 8)), 2003, 1984),
        TestCase(Day09(ClasspathResourceInput(2020, 9), 25, 36845998L), 36845998L, 4830226L),
        TestCase(Day10(ClasspathResourceInput(2020, 10)), 1625L, 3100448333024L),
        TestCase(Day11(ClasspathResourceInput(2020, 11)), 2254, 2004),
        TestCase(Day12(ClasspathResourceInput(2020, 12)), 1441, 61616),
        TestCase(Day13(ClasspathResourceInput(2020, 13)), 102, 327300950120029L),
        TestCase(Day14(ClasspathResourceInput(2020, 14)), 9628746976360L, 4574598714592L),
        TestCase(Day15(ClasspathResourceInput(2020, 15)), 1009, 62714),
        TestCase(Day16(ClasspathResourceInput(2020, 16)), 23009, 10458887314153L),
        TestCase(Day17(ClasspathResourceInput(2020, 17)), 391, 2264),
        TestCase(Day18(ClasspathResourceInput(2020, 18)), 29839238838303L, 201376568795521L),
        TestCase(Day19(ClasspathResourceInput(2020, 19)), 147, 263),
        TestCase(Day20(ClasspathResourceInput(2020, 20)), 66020135789767L, 1537),
    )
}