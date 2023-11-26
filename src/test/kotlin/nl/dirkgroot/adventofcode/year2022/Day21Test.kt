package nl.dirkgroot.adventofcode.year2022

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import nl.dirkgroot.adventofcode.util.input
import nl.dirkgroot.adventofcode.util.invokedWith
import java.lang.IllegalStateException

private fun solution1(input: String) = parse(input).let { ctx ->
    ctx.getValue("root").evaluate(ctx)
}

private fun solution2(input: String) = parse(input, true).toMutableMap().let { ctx ->
    val root = ctx.getValue("root")
    var min = 0L
    var max = Long.MAX_VALUE
    var interval = 1_000_000_000_000L

    while (min != max) {
        (min..max step interval).asSequence()
            .onEach { ctx["humn"] = ValueExpr(it) }
            .map { it to root.evaluate(ctx) }
            .windowed(2)
            .first { (a, b) -> (a.second >= 0L && b.second <= 0L) || (a.second <= 0 && b.second >= 0) }
            .let { (a, b) ->
                if (a.second == 0L) {
                    min = a.first
                    max = a.first
                } else if (b.second == 0L) {
                    min = b.first
                    max = b.first
                } else {
                    min = a.first
                    max = b.first
                }
            }
        interval /= 10
    }
    min
}

private fun parse(input: String, part2: Boolean = false) =
    input.lineSequence().map { line -> Expr.parse(line, part2) }.associate { it }

private interface Expr {
    fun evaluate(ctx: Map<String, Expr>): Long

    companion object {
        fun parse(str: String, part2: Boolean) = "(.*): (.*) ([+\\-/*]) (.*)".toRegex().matchEntire(str)
            ?.groupValues?.let {
                it[1] to when (it[3]) {
                    "+" -> if (part2 && it[1] == "root") SubExpr(it[2], it[4]) else AddExpr(it[2], it[4])
                    "-" -> SubExpr(it[2], it[4])
                    "*" -> MulExpr(it[2], it[4])
                    "/" -> DivExpr(it[2], it[4])
                    else -> throw IllegalStateException()
                }
            } ?: "(.*): (\\d+)".toRegex().matchEntire(str)!!
            .groupValues.let { it[1] to ValueExpr(it[2].toLong()) }
    }
}

private class ValueExpr(private val value: Long) : Expr {
    override fun evaluate(ctx: Map<String, Expr>) = value
}

private class AddExpr(private val id1: String, private val id2: String) : Expr {
    override fun evaluate(ctx: Map<String, Expr>) = ctx.getValue(id1).evaluate(ctx) + ctx.getValue(id2).evaluate(ctx)
}

private class SubExpr(private val id1: String, private val id2: String) : Expr {
    override fun evaluate(ctx: Map<String, Expr>) = ctx.getValue(id1).evaluate(ctx) - ctx.getValue(id2).evaluate(ctx)
}

private class MulExpr(private val id1: String, private val id2: String) : Expr {
    override fun evaluate(ctx: Map<String, Expr>) = ctx.getValue(id1).evaluate(ctx) * ctx.getValue(id2).evaluate(ctx)
}

private class DivExpr(private val id1: String, private val id2: String) : Expr {
    override fun evaluate(ctx: Map<String, Expr>) = ctx.getValue(id1).evaluate(ctx) / ctx.getValue(id2).evaluate(ctx)
}

//===============================================================================================\\

private const val YEAR = 2022
private const val DAY = 21

class Day21Test : StringSpec({
    "example part 1" { ::solution1 invokedWith exampleInput shouldBe 152L }
    "part 1 solution" { ::solution1 invokedWith input(YEAR, DAY) shouldBe 21208142603224L }
    "example part 2" { ::solution2 invokedWith exampleInput shouldBe 301L }
    "part 2 solution" { ::solution2 invokedWith input(YEAR, DAY) shouldBe 3882224466191L }
})

private val exampleInput =
    """
        root: pppw + sjmn
        dbpl: 5
        cczh: sllz + lgvd
        zczc: 2
        ptdq: humn - dvpt
        dvpt: 3
        lfqf: 4
        humn: 5
        ljgn: 2
        sjmn: drzm * dbpl
        sllz: 4
        pppw: cczh / lfqf
        lgvd: ljgn * ptdq
        drzm: hmdt - zczc
        hmdt: 32
    """.trimIndent()
