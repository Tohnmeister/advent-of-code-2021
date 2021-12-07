package nl.tonni.adventofcode2021

import kotlin.math.abs

private fun calculateAlignmentCosts(currentPositions: List<Int>, requiredPosition: Int): Int {
    return currentPositions.sumOf { abs(it - requiredPosition) }
}

private fun calculateAlignmentCostsPart2(currentPositions: List<Int>, requiredPosition: Int): Int {
    return currentPositions.sumOf {
        val distance = abs(it - requiredPosition)

        val costs = (distance * (distance + 1) / 2)

        costs
    }
}

data class AlignmentCosts(val horizontalPosition: Int, val costs: Int)

fun main() {
    val horizontalPositions = readLine("input/day7/input.txt").split(",").map { it.toInt() }

    val min = horizontalPositions.minOrNull()!!
    val max = horizontalPositions.maxOrNull()!!

    val positionsToTry = min..max

    val allPossibleAlignmentCosts = positionsToTry.map { AlignmentCosts(it, calculateAlignmentCosts(horizontalPositions, it))}
    println(allPossibleAlignmentCosts.minByOrNull { it.costs })

    val allPossibleAlignmentCostsPart2 = positionsToTry.map { AlignmentCosts(it, calculateAlignmentCostsPart2(horizontalPositions, it))}
    println(allPossibleAlignmentCostsPart2.minByOrNull { it.costs })
}