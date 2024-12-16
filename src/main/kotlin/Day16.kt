package day01

import day01.utils.Point
import java.io.File

class Day16 : Base<Day16.Data, Long>(16) {

    data class Data(
        val start: Point<Int>,
        val end: Point<Int>,
        val barriers: List<Point<Int>>
    )

    override fun part1(input: Data): Long {
        var rotates = 0
        val path = mutableSetOf<Point<Int>>()

        printMap(input, path)

        return path.size.toLong() + rotates * 1000L
    }

    override fun part2(input: Data): Long {
        return 0L
    }

    override fun mapInputData(file: File): Data {
        var start: Point<Int>? = null
        var end: Point<Int>? = null
        val barriers = mutableListOf<Point<Int>>()
        file.readLines().forEachIndexed { x, line ->
            line.forEachIndexed { y, c ->
                when (c) {
                    '#' -> barriers.add(Point(x, y))
                    'S' -> start = Point(x, y)
                    'E' -> end = Point(x, y)
                }
            }
        }
        return Data(
            start = start!!,
            end = end!!,
            barriers = barriers
        )
    }
}

private fun printMap(data: Day16.Data, path: Set<Point<Int>>) {
    val maxX = data.barriers.maxOf { it.x }
    val maxY = data.barriers.maxOf { it.y }
    for (x in 0..maxX) {
        println()
        for (y in 0..maxY) {
            val point = Point(x, y)
            when {
                data.start == point -> print("S")
                data.end == point -> print("E")
                data.barriers.contains(point) -> print("#")
                path.contains(point) -> print("!")
                else -> print(".")
            }
        }
    }
    println()
}

fun main() {
    Day16().submitPart1TestInput() //
//    Day16().submitPart1Input() //
//    Day16().submitPart2TestInput() //
//    Day16().submitPart2Input() //
}