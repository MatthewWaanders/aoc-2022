package day5

import utils.readInput
import java.util.Stack

fun main() {
    val testInput = readInput("Day05_test", "day5")
    check(part1(testInput) == "CMZ")

    val input = readInput("Day05", "day5")
    println(part1(input))
    println(part2(input))
}

fun part1(input: List<String>): String {
    val (stacks, moves) = parseInput(input)
    moves.forEach {move -> repeat((1..move.first).count()) { stacks[move.third].push(stacks[move.second].pop()) } }
    return parseStacksToOutput(stacks)
}

fun part2(input: List<String>): String {
    val (stacks, moves) = parseInput(input)
    moves.forEach {move -> (1..move.first).map { stacks[move.second].pop() }.reversed().forEach {stacks[move.third].push(it)} }
    return parseStacksToOutput(stacks)
}

fun parseInput(input: List<String>): Pair<List<Stack<String>>, List<Triple<Int, Int, Int>>> {
    val (stacksInput, movesInput) = input.fold(listOf<List<String>>(emptyList(), emptyList())) { acc, it -> if (it.startsWith("move")) listOf(acc[0], listOf(*(acc[1].toTypedArray()), it)) else if (it.trim().startsWith("[")) listOf(listOf(*(acc[0].toTypedArray()), it), acc[1]) else acc}
    val stacks = parseStackInputToStacks(stacksInput.reversed().map { parseLineAsStackInput(it)})
    val moves = parseMovesInputToMoves(movesInput)

    return Pair(stacks, moves)
}

fun parseLineAsStackInput(input: String) =
    input
        .toList()
        .chunked(4)
        .map { it[1] }
        .foldIndexed(emptyList<Pair<String, Int>>()) { index, acc, it -> if (it.isWhitespace()) acc else acc + Pair(it.toString(), index)}

fun parseStackInputToStacks(input: List<List<Pair<String, Int>>>) = List(input.first().maxOf { it.second } + 1) {Stack<String>()}.apply { input.forEach {line -> line.forEach { stackInput -> this[stackInput.second].add(stackInput.first) } } }

fun parseMovesInputToMoves(input: List<String>) = input.map { movesDescription -> movesDescription.split(" ").let { Triple(it[1].toInt(), it[3].toInt()-1, it[5].toInt()-1) } }

fun parseStacksToOutput(stacks: List<Stack<String>>) = stacks.joinToString("") { it.pop() }