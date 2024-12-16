package day01

import day01.utils.*
import java.io.File

class Day16 : Base<Day16.Data, Long>(16) {

    data class Data(
        val start: Point<Int>,
        val end: Point<Int>,
        val barriers: List<Point<Int>>
    )

    override fun part1(input: Data): Long {
        val result = traverse(input, Direction.RIGHT, input.start, mutableSetOf(), 0)
        return result
    }

    private fun traverse(
        data: Data,
        direction: Direction,
        point: Point<Int>,
        path: MutableSet<Point<Int>>,
        rotates: Int
    ): Long {
        if (data.end == point) {
            val result = path.size.toLong() + rotates * 1000L
            println("Result: $result")
            printMap(data, path)
            return result
        } else {
            path.add(point)
            println("Step: ${path.size}")

            return minOf(
                run {
                    val newPoint = point.move1(direction)
                    return@run if (!data.barriers.contains(newPoint) && !path.contains(newPoint)) {
                        val newPath = mutableSetOf<Point<Int>>().apply { addAll(path) }
                        traverse(data, direction, newPoint, newPath, rotates)
                    } else Long.MAX_VALUE
                },
                run {
                    val newDirection = direction.turnRight()
                    val newPoint = point.move1(newDirection)
                    return@run if (!data.barriers.contains(newPoint) && !path.contains(newPoint)) {
                        val newPath = mutableSetOf<Point<Int>>().apply { addAll(path) }
                        traverse(data, newDirection, newPoint, newPath, rotates + 1)
                    } else Long.MAX_VALUE
                },
                run {
                    val newDirection = direction.turnLeft()
                    val newPoint = point.move1(newDirection)
                    return@run if (!data.barriers.contains(newPoint) && !path.contains(newPoint)) {
                        val newPath = mutableSetOf<Point<Int>>().apply { addAll(path) }
                        traverse(data, newDirection, newPoint, newPath, rotates + 1)
                    } else Long.MAX_VALUE
                }
            )
        }
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
                path.contains(point) -> print("O")
                data.barriers.contains(point) -> print("#")
                else -> print(".")
            }
        }
    }
    println()
}

fun main() {
    Day16().submitPart1TestInput() // 7036
    Day16().submitPart1Input() //
//    Day16().submitPart2TestInput() //
//    Day16().submitPart2Input() //
}