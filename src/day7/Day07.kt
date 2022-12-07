package day7

import day7.tree.Directory
import day7.tree.File
import day7.tree.Node
import day7.tree.Root
import utils.readInput

const val part1Limit = 100000
const val requiredSpace = 30000000
const val totalSpace = 70000000

fun main() {
    val testInput = readInput("Day07_test", "day7")
    check(part1(testInput) == 95437)

    val input = readInput("Day07", "day7")
    println(part1(input))
    println(part2(input))
}

fun part1(input: List<String>) = calculateChildDirectorySizes(
        buildTreeFromInput(input)
    ).sumOf { if (it <= part1Limit) it else 0 }

fun part2(input: List<String>): Int {
    val directorySizes = calculateChildDirectorySizes(
        buildTreeFromInput(input)
    ).sorted()

    return directorySizes.first { it >= (requiredSpace - (totalSpace - directorySizes.last())) }
}

fun buildTreeFromInput(input: List<String>) = buildSubtreeFromInput(Root(), input, 1).first

fun buildSubtreeFromInput(parent: Node, input: List<String>, start: Int): Pair<Node, Int> {
    var i = start
    while (i < input.size) {
        val line = input[i]
        if (line.startsWith("$")) {
            val parts = line.split(" ")
            val (_, command) = parts
            if (command == "ls") {
                i++
                continue
            }

            val (_, _, target) = parts
            if (target == "..") {
                return Pair(parent, i)
            }

            val (child, endIndex) = buildSubtreeFromInput(
                Node(Directory, emptyList(), 0),
                input,
                i + 1,
            )
            parent.addChild(child)
            i = endIndex + 1
        } else {
            if (line.startsWith("dir")) {
                i++
                continue
            }

            val (size, _) = line.split(" ")
            parent.addChild(Node(File, emptyList(), size.toInt()))

            i++
        }
    }
    return Pair(parent, i)
}

fun calculateChildDirectorySizes(parent: Node): List<Int> = parent.children.fold(listOf(parent.totalSize)) { acc, it ->
    if (it.type == Directory) {
        if (it.children.isNotEmpty()) {
            acc + calculateChildDirectorySizes(it)
        } else {
            acc + it.totalSize
        }
    } else {
        acc
    }
}