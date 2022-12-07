package nl.dirkgroot.adventofcode.year2022

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import nl.dirkgroot.adventofcode.util.input
import nl.dirkgroot.adventofcode.util.invokedWith
import java.nio.file.Path
import java.util.*

private fun solution1(input: String): Long {
    val directories = findDirectories(input)

    return directories.entries.map { (_, size) -> size }.filter { it <= 100000 }.sum()
}

private fun solution2(input: String): Long {
    val directories = findDirectories(input)
    val rootSize = directories.getValue(Path.of("/"))

    return directories.entries.map { (_, size) -> size }.sorted()
        .first { 70000000L - rootSize + it >= 30000000L }
}

private fun findDirectories(input: String): MutableMap<Path, Long> {
    val stack = Stack<Pair<Path, Long>>()
    val directories = mutableMapOf<Path, Long>()
    var currentDir = Path.of("/")
    var currentSize = 0L

    input.lineSequence().drop(1).forEach { entry ->
        if (entry.startsWith("$ cd")) {
            when (val newDir = entry.substring(5)) {
                ".." -> {
                    directories[currentDir] = currentSize

                    val (dir, size) = stack.pop()
                    currentDir = dir
                    currentSize += size
                }
                else -> {
                    stack.push(currentDir to currentSize)
                    currentSize = 0L
                    currentDir = currentDir.resolve(newDir)
                }
            }
        } else if (entry != "$ ls") {
            entry.takeWhile { it != ' ' }.toLongOrNull()?.let { fileSize ->
                currentSize += fileSize
            }
        }
    }
    directories[currentDir] = currentSize
    while (stack.isNotEmpty()) {
        val (dir, size) = stack.pop()
        currentDir = dir
        currentSize += size
        directories[currentDir] = currentSize
    }

    return directories
}

//===============================================================================================\\

private const val YEAR = 2022
private const val DAY = 7

class Day07Test : StringSpec({
    "example part 1" { ::solution1 invokedWith exampleInput shouldBe 95437L }
    "part 1 solution" { ::solution1 invokedWith input(YEAR, DAY) shouldBe 1611443L }
    "example part 2" { ::solution2 invokedWith exampleInput shouldBe 24933642L }
    "part 2 solution" { ::solution2 invokedWith input(YEAR, DAY) shouldBe 2086088L }
})

private val exampleInput =
    """
        ${'$'} cd /
        ${'$'} ls
        dir a
        14848514 b.txt
        8504156 c.dat
        dir d
        ${'$'} cd a
        ${'$'} ls
        dir e
        29116 f
        2557 g
        62596 h.lst
        ${'$'} cd e
        ${'$'} ls
        584 i
        ${'$'} cd ..
        ${'$'} cd ..
        ${'$'} cd d
        ${'$'} ls
        4060174 j
        8033020 d.log
        5626152 d.ext
        7214296 k
    """.trimIndent()
