package day01

import day01.utils.Direction
import day01.utils.Direction.Companion.fromSign
import day01.utils.Point
import day01.utils.move
import java.io.File

class Day15 : Base<Day15.Data, Long>(15) {

    data class Data(
        val start: Point<Int>,
        val boxes: List<Point<Int>>,
        val barriers: List<Point<Int>>,
        val moves: List<Direction>
    )

    override fun part1(input: Data): Long {
        var start = input.start
        val boxes = mutableListOf<Point<Int>>()
        boxes.addAll(input.boxes)

        println("Initial:")
        printMap(start, boxes, input.barriers)

        input.moves.forEach { move ->
            var currentPoint = start
            val movedBoxes = mutableListOf<Point<Int>>()
            while (true) {
                currentPoint = currentPoint.move(move)
                val isBarrier = input.barriers.contains(currentPoint)
                val isBox = boxes.contains(currentPoint)
                if (isBarrier) {
                    break
                } else if (isBox) {
                    movedBoxes.add(currentPoint)
                } else {
                    start = start.move(move)
                    movedBoxes.forEach { boxes.remove(it) }
                    movedBoxes.forEach { boxes.add(it.move(move)) }
                    break
                }
            }
//            println()
//            println("Move: $move")
//            printMap(start, boxes, input.barriers)
        }
        println()
        println("Final:")
        printMap(start, boxes, input.barriers)

        return boxes.fold(0L) { r, box ->
            r + (box.y * 100 + box.x)
        }
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

private fun printMap(start: Point<Int>, boxes: List<Point<Int>>, barriers: List<Point<Int>>) {
    val maxX = barriers.maxOf { it.x }
    val maxY = barriers.maxOf { it.y }

    for (y in 0..maxY) {
        println()
        for (x in 0..maxX) {
            val point = Point(x, y)
            when {
                start == point -> print("@")
                boxes.contains(point) -> print("O")
                barriers.contains(point) -> print("#")
                else -> print(".")
            }
        }
    }
}

fun main() {
    Day15().submitPart1TestInput() // 2028 / 10092
    Day15().submitPart1Input() // 1526673
//    Day15().submitPart2TestInput() //
//    Day15().submitPart2Input() //
}