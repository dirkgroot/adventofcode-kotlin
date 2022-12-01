package nl.dirkgroot.adventofcode.year2021

import assertk.assertThat
import assertk.assertions.isEqualTo
import nl.dirkgroot.adventofcode.util.ClasspathResourceInput
import nl.dirkgroot.adventofcode.util.StringInput
import org.junit.jupiter.api.Test

class Day25Test {
    @Test
    fun `move east`() {
        val day25 = Day25(
            StringInput(
                """
                    >..
                    >>.
                    .>>
                """.trimIndent()
            )
        )

        day25.move()
        assertThat(day25.map.asString(day25.width)).isEqualTo(
            """
                .>.
                >.>
                >>.
            """.trimIndent()
        )
    }

    @Test
    fun `move south`() {
        val day25 = Day25(
            StringInput(
                """
                    vv.
                    .vv
                    ..v
                """.trimIndent()
            )
        )

        day25.move()
        assertThat(day25.map.asString(day25.width)).isEqualTo(
            """
                .vv
                v.v
                .v.
            """.trimIndent()
        )
    }

    @Test
    fun `example move`() {
        val example = Day25(
            StringInput(
                """
                    ...>...
                    .......
                    ......>
                    v.....>
                    ......>
                    .......
                    ..vvv..
                """.trimIndent()
            )
        )
        example.move()
        assertThat(example.map.asString(example.width)).isEqualTo(
            """
                ..vv>..
                .......
                >......
                v.....>
                >......
                .......
                ....v..
            """.trimIndent()
        )
    }

    @Test
    fun example() {
        val example = Day25(
            StringInput(
                """
                    v...>>.vv>
                    .vv>>.vv..
                    >>.>v>...v
                    >>v>>.>.v.
                    v>v.vv.v..
                    >.>>..v...
                    .vv..>.>v.
                    v.v..>>v.v
                    ....v..v.>
                """.trimIndent()
            )
        )
        assertThat(example.part1()).isEqualTo(58)
    }

    @Test
    fun part1() {
        val result = Day25(ClasspathResourceInput(2021, 25)).part1()
        println(result)
        assertThat(result).isEqualTo(278)
    }

    private fun CharArray.asString(width: Int) =
        asSequence().windowed(width, width)
            .joinToString("\n") { it.joinToString("") }
}
