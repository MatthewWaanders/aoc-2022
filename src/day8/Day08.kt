package day8

import utils.readInput

typealias Direction = String

const val up: Direction = "UP"
const val down: Direction = "DOWN"
const val left: Direction = "LEFT"
const val right: Direction = "RIGHT"

fun main() {
    val testInput = readInput("Day08_test", "day8")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("Day08", "day8")
    println(part1(input))
    println(part2(input))
}

fun part1(input: List<String>) = inputToMatrix(input).let { matrix ->
        matrix.mapIndexed { rowIndex, row ->
            row.mapIndexed { columnIndex, value ->
                val column = selectColumn(matrix, columnIndex)
                if (
                    treeIsVisible(value, matrix[rowIndex].subList(0, columnIndex), emptyList(), left) ||
                    treeIsVisible(value, matrix[rowIndex].subList(columnIndex + 1, row.size), emptyList(), right) ||
                    treeIsVisible(value, emptyList(), column.subList(0, rowIndex), up) ||
                    treeIsVisible(value, emptyList(), column.subList(rowIndex + 1, matrix.size), down)
                ) 1 else 0
            }
        }.sumOf { it.sum() }
    }

fun part2(input: List<String>) = inputToMatrix(input).let { matrix ->
        matrix.mapIndexed { rowIndex, row ->
            row.mapIndexed {columnIndex, value ->
                countVisibleTrees(value, matrix[rowIndex].subList(0, columnIndex), emptyList(), left) *
                countVisibleTrees(value, matrix[rowIndex].subList(columnIndex + 1, row.size), emptyList(), right) *
                countVisibleTrees(value, emptyList(), selectColumn(matrix, columnIndex).subList(0, rowIndex), up) *
                countVisibleTrees(value, emptyList(), selectColumn(matrix, columnIndex).subList(rowIndex + 1, matrix.size), down)
            }
        }.maxOf { it.max() }
    }

fun inputToMatrix(input: List<String>): List<List<Int>> = input.map { line -> line.toList().map { tree -> tree.digitToInt() } }

fun treeIsVisible(tree: Int, row: List<Int>, column: List<Int>, direction: Direction) = if (direction == up || direction == down) column.none { it >= tree } else row.none { it >= tree }

fun countVisibleTrees(tree: Int, row: List<Int>, column: List<Int>, direction: Direction) = (if (direction == up || direction == down) column else row).let {relevantDimension ->
        if (direction == right || direction == down) {
            relevantDimension.indexOfFirst { it >= tree }.let { if (it == -1) relevantDimension.size else it + 1 }
        } else {
            relevantDimension.indexOfLast { it >= tree }.let { if (it == -1) relevantDimension.size else relevantDimension.size - it }
        }
    }

fun selectColumn(matrix: List<List<Int>>, columnIndex: Int): List<Int> = matrix.map { it[columnIndex] }