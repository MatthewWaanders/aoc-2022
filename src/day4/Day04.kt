package day4

import utils.readInput

fun main() {
    val testInput = readInput("Day04_test", "day4")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04", "day4")
    println(part1(input))
    println(part2(input))
}

fun part1(input: List<String>) = input
    .map { pair -> pair.split(",").map { range -> val (start, end) = range.split("-"); Pair(start.toInt(), end.toInt()) } }
    .sumOf { rangePair -> if ((rangePair[0].second >= rangePair[1].second && rangePair[0].first <= rangePair[1].first) || (rangePair[1].second >= rangePair[0].second && rangePair[1].first <= rangePair[0].first)) 1 else 0 as Int }

fun part2(input: List<String>) = input
    .map { pair -> pair.split(",").map { range -> val (start, end) = range.split("-"); generateRange(start.toInt(), end.toInt()) } }
    .sumOf { rangePair -> if (rangePair[0].any { rangePair[1].contains(it) } || rangePair[1].any { rangePair[0].contains(it) }) 1 else 0 as Int }

fun generateRange(start: Int, end: Int): IntRange {
    return start..end
}