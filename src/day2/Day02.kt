package day2

import utils.readInput

const val rockValue = 1
const val paperValue = 2
const val scissorValue = 3

const val winValue = 6
const val drawValue = 3
const val lossValue = 0

val partOneChoiceValueMap = mapOf(
    "X" to rockValue,
    "Y" to paperValue,
    "Z" to scissorValue
)

val partOneMatchResultsMatrix = mapOf(
    "A" to mapOf(
        "X" to drawValue,
        "Y" to winValue,
        "Z" to lossValue,
    ),
    "B" to mapOf(
        "X" to lossValue,
        "Y" to drawValue,
        "Z" to winValue,
    ),
    "C" to mapOf(
        "X" to winValue,
        "Y" to lossValue,
        "Z" to drawValue,
    ),
)

val partTwoMatchResultValueMap = mapOf(
    "X" to lossValue,
    "Y" to drawValue,
    "Z" to winValue,
)

val partTwoChoiceMatrix = mapOf(
    "A" to mapOf(
        "X" to scissorValue,
        "Y" to rockValue,
        "Z" to paperValue,
    ),
    "B" to mapOf(
        "X" to rockValue,
        "Y" to paperValue,
        "Z" to scissorValue,
    ),
    "C" to mapOf(
        "X" to paperValue,
        "Y" to scissorValue,
        "Z" to rockValue,
    ),
)

fun main() {
    val testInput = readInput("Day02_test", "day2")
    check(part1(testInput) == 15)

    val input = readInput("Day02", "day2")
    println(part1(input))
    println(part2(input))
}

fun part1(input: List<String>) = input.sumOf {
    val (opponent, me) = it.split(" ")
    partOneMatchResultsMatrix[opponent]!![me]!! + partOneChoiceValueMap[me]!!
}

fun part2(input: List<String>) = input.sumOf {
    val (opponent, me) = it.split(" ")
    partTwoChoiceMatrix[opponent]!![me]!! + partTwoMatchResultValueMap[me]!!
}
