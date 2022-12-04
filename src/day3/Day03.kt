package day3

import utils.readInput

fun main() {
    val testInput = readInput("Day03_test", "day3")
    println(part1(testInput))
    check(part1(testInput) == 157)

    val input = readInput("Day03", "day3")
    println(part1(input))
    println(part2(input))
}

fun part1(input: List<String>) = input
    .map { it.substring(0, it.length / 2).toSet().intersect(it.substring(it.length / 2, it.length).toSet()).first() }
    .sumOf { if (it.code >= 97) it.code - 96 else it.code - 38 }

fun part2(input: List<String>) = input
    .chunked(3)
    .map { it[0].toSet().intersect(it[1].toSet().intersect(it[2].toSet())).first() }
    .sumOf { if (it.code >= 97) it.code - 96 else it.code - 38 }