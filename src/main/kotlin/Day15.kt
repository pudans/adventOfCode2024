package day01

import day01.utils.Direction
import day01.utils.Direction.Companion.fromSign
import day01.utils.Point
import java.io.File

class Day15 : Base<Day15.Data, Long>(15) {

    data class Data(
        val start: Point<Int>,
        val boxes: List<Point<Int>>,
        val barriers: List<Point<Int>>,
        val moves: List<Direction>
    )

    override fun part1(input: Data): Long {
        println(input)
        return 0
    }

    override fun part2(input: Data): Long = 0

    override fun mapInputData(file: File): Data {
        var isMap = true
        var lineCount = 0
        var start: Point<Int>? = null
        val boxes = mutableListOf<Point<Int>>()
        val barriers = mutableListOf<Point<Int>>()
        val moves = mutableListOf<Direction>()
        file.readLines().map { line ->
            if (line.isEmpty()) {
                isMap = false
            } else if (isMap) {
                line.forEachIndexed { index, c ->
                    when (c) {
                        '#' -> barriers.add(Point(index, lineCount))
                        'O' -> boxes.add(Point(index, lineCount))
                        '@' -> start = Point(index, lineCount)
                    }
                }
                lineCount++
            } else {
                line.forEach { moves.add(it.fromSign()) }
            }
        }
        return Data(
            start = start!!,
            boxes = boxes,
            barriers = barriers,
            moves = moves
        )
    }
}

fun main() {
    Day15().submitPart1TestInput() //
//    Day15().submitPart1Input() //
//    Day15().submitPart2TestInput() //
//    Day15().submitPart2Input() //
}