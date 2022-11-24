package nl.dirkgroot.adventofcode.year2020

import nl.dirkgroot.adventofcode.util.Input
import nl.dirkgroot.adventofcode.util.Puzzle

class Day21(input: Input) : Puzzle() {
    private val foodRegex = "(.*) \\(contains (.*)\\)".toRegex()

    class Food(val ingredients: List<String>, val allergents: List<String>)

    private val foods by lazy {
        input.lines()
            .map {
                val match = foodRegex.matchEntire(it)!!
                Food(match.groupValues[1].split(" "), match.groupValues[2].split(", "))
            }
    }

    override fun part1() =
        ingredientPerAllergent()
            .map { (_, ingredients) -> ingredients }
            .let { ingredientsWithAllergent ->
                foods.sumOf { food -> food.ingredients.count { it !in ingredientsWithAllergent } }
            }

    override fun part2() =
        ingredientPerAllergent().sortedBy { (allergent, _) -> allergent }
            .joinToString(",") { (_, ingredient) -> ingredient }

    private fun ingredientPerAllergent(): List<Pair<String, String>> {
        val allAllergents = foods.flatMap { it.allergents }.toSet()
        val foodsPerAllergent = allAllergents.map { allergent ->
            allergent to foods.filter { it.allergents.contains(allergent) }
        }.toMap()
        val ingredientsPerAllergent = allAllergents.map { allergent ->
            allergent to foods
                .filter { it.allergents.contains(allergent) }
                .flatMap { it.ingredients }
                .distinct()
        }

        val allergentIngredient = ingredientsPerAllergent.map { (allergent, ingredients) ->
            allergent to ingredients.filter { ingredient ->
                foodsPerAllergent[allergent]!!.all { food -> food.ingredients.contains(ingredient) }
            }.toMutableList()
        }

        while (!allergentIngredient.all { it.second.size == 1 }) {
            allergentIngredient.forEach { (_, ingredients) ->
                if (ingredients.size == 1) {
                    val excludeFromRest = ingredients[0]
                    allergentIngredient
                        .filter { (_, ingredients) -> ingredients.size > 1 }
                        .forEach { (_, ingredients) -> ingredients.remove(excludeFromRest) }
                }
            }
        }

        return allergentIngredient.map { (allergent, ingredients) -> allergent to ingredients[0] }
    }
}