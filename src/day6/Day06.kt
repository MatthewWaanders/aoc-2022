package day6

import utils.readInput

const val partOneWindowSize = 4
const val partTwoWindowSize = 14

fun main() {
    val testInput = readInput("Day06_test", "day6")
    check(part1(testInput) == 11)

    val input = readInput("Day06", "day6")
    println(part1(input))
    println(part2(input))
}

fun part1(input: List<String>) = findIndexOfFirstUniqueWindow(input.first(), partOneWindowSize)

fun part2(input: List<String>) = findIndexOfFirstUniqueWindow(input.first(), partTwoWindowSize)

fun findIndexOfFirstUniqueWindow(input: String, windowSize: Int) = input
    .toList()
    .windowed(windowSize, 1)
    .mapIndexed { index, it -> if (it.toSet().size == it.size) index + windowSize else null }
    .filterNotNull()
    .first()