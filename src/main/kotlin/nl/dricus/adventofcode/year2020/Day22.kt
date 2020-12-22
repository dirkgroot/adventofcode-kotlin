package nl.dricus.adventofcode.year2020

import nl.dricus.adventofcode.util.Input
import nl.dricus.adventofcode.util.Puzzle

class Day22(input: Input) : Puzzle() {
    private val decks by lazy {
        input.string().split("\n\n")
            .map { deck -> deck.split("\n").drop(1).map { card -> card.toInt() } }
    }

    override fun part1() = calculateScore(playNormal(decks[0], decks[1]))

    private tailrec fun playNormal(player1: List<Int>, player2: List<Int>): List<Int> {
        if (player1.isEmpty()) return player2
        if (player2.isEmpty()) return player1

        val (newDeck1, newDeck2) = normalRound(player1, player2)

        return playNormal(newDeck1, newDeck2)
    }

    override fun part2(): Int = calculateScore(playRecursive(decks[0], decks[1]).second)

    private tailrec fun playRecursive(
        player1: List<Int>, player2: List<Int>,
        previousRounds2: List<List<Int>> = listOf()
    ): Pair<Int, List<Int>> {
        if (player1.isEmpty()) return 2 to player2
        if (player2.isEmpty()) return 1 to player1

        val configuration = player1 + player2
        if (previousRounds2.any { it == configuration }) return 1 to player1

        val (newDeck1, newDeck2) = if ((player1.size - 1) >= player1[0] && (player2.size - 1) >= player2[0])
            recursiveRound(player1, player2)
        else
            normalRound(player1, player2)

        return playRecursive(
            newDeck1, newDeck2,
            previousRounds2.plusElement(player1 + player2)
        )
    }

    private fun recursiveRound(player1: List<Int>, player2: List<Int>): Pair<List<Int>, List<Int>> {
        val (winner, _) = playRecursive(player1.drop(1).take(player1[0]), player2.drop(1).take(player2[0]))

        val card1 = player1[0]
        val card2 = player2[0]
        val cards = if (winner == 1) listOf(card1, card2) else listOf(card2, card1)
        val newDeck1 = if (winner == 1) player1.drop(1) + cards else player1.drop(1)
        val newDeck2 = if (winner == 2) player2.drop(1) + cards else player2.drop(1)

        return Pair(newDeck1, newDeck2)
    }

    private fun normalRound(player1: List<Int>, player2: List<Int>): Pair<List<Int>, List<Int>> {
        val card1 = player1[0]
        val card2 = player2[0]
        val cards = listOf(card1, card2).sorted().reversed()
        val newDeck1 = if (card1 > card2) player1.drop(1) + cards else player1.drop(1)
        val newDeck2 = if (card2 > card1) player2.drop(1) + cards else player2.drop(1)

        return Pair(newDeck1, newDeck2)
    }

    private fun calculateScore(deck: List<Int>) =
        deck.foldIndexed(0) { index, acc, card -> acc + (deck.size - index) * card }
}
