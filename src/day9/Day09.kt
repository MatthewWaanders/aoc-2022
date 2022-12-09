package day9

import utils.readInput

typealias PositionState = Pair<Int, Int>
typealias Dimension = String
typealias Direction = Int

const val row: Dimension = "ROW"
const val column: Dimension = "COLUMN"

const val reverse: Direction = -1
const val normal: Direction = 1

fun main() {
    val testInput = readInput("Day09_test", "day9")
    check(part1(testInput) == 13)

    val testInputPart2 = readInput("Day09_test_2", "day9")
    check(part2(testInputPart2) == 36)

    val input = readInput("Day09", "day9")
    println(part1(input))
    println(part2(input))
}

fun part1(input: List<String>) = solution(input, List(2) { PositionState(0,0) })

fun part2(input: List<String>) = solution(input, List(10) { PositionState(0,0) })

fun solution(input: List<String>, knots: List<PositionState>) = knots.run {
        var workingList = this
        input
            .map { line -> line.split(" ").let { splitInputLine -> Pair(splitInputLine[0], splitInputLine[1].toInt()) } }
            .fold(emptyList<PositionState>()) { acc, move ->
                val head = workingList[0]
                val tails = workingList.subList(1, workingList.size)

                val (newlyVisited, headUpdated, tailsUpdated) = when (move.first) {
                    "R" -> processSteps(column, normal, head, tails, move.second)
                    "L" -> processSteps(column, reverse, head, tails, move.second)
                    "U" -> processSteps(row, normal, head, tails, move.second)
                    "D" -> processSteps(row, reverse, head, tails, move.second)
                    else -> Triple(emptyList(), head, tails)
                }

                workingList = listOf(headUpdated, *(tailsUpdated.toTypedArray()))

                acc + newlyVisited
            }.toSet().size
    }

fun processSteps(dimension: Dimension, direction: Direction, head: PositionState, tails: List<PositionState>, steps: Int): Triple<List<PositionState>, PositionState, List<PositionState>> {
    var headAdjustable = head
    val tailsAdjustable = tails.toMutableList()
    val visitedByLastTail = (0 until steps).fold(listOf(tails.last())) { acc, it ->
        headAdjustable = if (dimension == column && direction == normal) {
            PositionState(headAdjustable.first + 1, headAdjustable.second)
        } else if (dimension == column && direction == reverse) {
            PositionState(headAdjustable.first - 1, headAdjustable.second)
        } else if (dimension == row && direction == normal) {
            PositionState(headAdjustable.first, headAdjustable.second + 1)
        } else {
            PositionState(headAdjustable.first, headAdjustable.second - 1)
        }

        tailsAdjustable[0] = processTailStep(headAdjustable, tailsAdjustable[0])
        if (tailsAdjustable.size == 1) {
            acc + tailsAdjustable[0]
        } else {
            for (j in 1 until tailsAdjustable.size) {
                tailsAdjustable[j] = processTailStep(tailsAdjustable[j-1], tailsAdjustable[j])
            }

            acc + tailsAdjustable.last()
        }
    }

    return Triple(visitedByLastTail, headAdjustable, tailsAdjustable)
}

fun processTailStep(newHeadPosition: PositionState, tailPosition: PositionState): PositionState {
    var xPos = tailPosition.first
    var yPos = tailPosition.second

    if (areTouching(newHeadPosition, tailPosition)) {
        return tailPosition
    }

    if (newHeadPosition.first != tailPosition.first) {
        if (newHeadPosition.first > tailPosition.first) xPos++ else xPos--
    }

    if (newHeadPosition.second != tailPosition.second) {
        if (newHeadPosition.second > tailPosition.second) yPos++ else yPos--
    }

    return PositionState(xPos, yPos)
}

fun areTouching(head: PositionState, tail: PositionState): Boolean {
    return (head.first == tail.first && head.second == tail.second) || // overlapping
        (head.first == tail.first && (head.second - tail.second) in -1..1) || // same column, different row
        ((head.first - tail.first in -1..1) && head.second == tail.second) || // same row, different column
        ((head.first - tail.first in -1..1) && (head.second - tail.second in -1..1)) // diagonally touching
}