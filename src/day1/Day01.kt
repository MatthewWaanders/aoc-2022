package day1

import utils.readInput

fun main() {
    val testInput = readInput("Day01_test", "day1")
    check(part1(testInput) == 24000)

    val input = readInput("Day01", "day1")
    println(part1(input))
    println(part2(input))
}

fun convertInputToCaloriesPerElf(input: List<String>) = input
    .fold(listOf(listOf<String>())) { acc, s -> if (s.isBlank()) listOf(*(acc.toTypedArray()), emptyList()) else listOf(*(acc.subList(0, acc.size - 1).toTypedArray()), acc.last() + s) }
    .fold(emptyList<Int>()) { acc, ls -> acc + ls.sumOf { it.toInt() } }

fun part1(input: List<String>) = convertInputToCaloriesPerElf(input)
    .maxOf { it }

fun part2(input: List<String>) = convertInputToCaloriesPerElf(input)
    .sortedDescending()
    .subList(0, 3)
    .sum()
