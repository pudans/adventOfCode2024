package day01

import day01.utils.*
import java.io.File
import java.util.*
import kotlin.math.min

class Day18 : Base<List<Point<Int>>, Int>(18) {

    override fun part1(input: List<Point<Int>>): Int {
        val maxBarriresCount = 1024 // 12
        val start = Point(0, 0)
//        val end = Point(6, 6)
        val end = Point(70, 70)
        val barriers = input.take(maxBarriresCount)

        printMap18(barriers, emptyList(), end)

        val visited = mutableMapOf<Point<Int>, Int>()
        val queue = LinkedList<TraverseStep>()
        queue.addLast(TraverseStep(Direction.DOWN, start, mutableListOf()))
        var result = Int.MAX_VALUE
        var i = 0
        while (queue.isNotEmpty()) {
            val step = queue.poll()
            step.execute(end, barriers, queue, visited)?.let {
                val newResult = it
                println("${newResult.size}")
                printMap18(barriers, newResult, end)
                result = min(result, newResult.size)
            }
            i++
        }
        return result
    }

    override fun part2(input: List<Point<Int>>): Int {
        return 0
    }

    class TraverseStep(
        private val direction: Direction,
        private val point: Point<Int>,
        private val path: MutableList<Point<Int>>
    ) {

        fun execute(
            endPoint: Point<Int>,
            barriers: List<Point<Int>>,
            queue: LinkedList<TraverseStep>,
            visited: MutableMap<Point<Int>, Int>
        ): List<Point<Int>>? {
            if (endPoint == point) {
                return path
            } else {
                if (visited[point] == null || visited[point]!! > path.size) {
                    visited[point] = path.size
                    path.add(point)
                    val newPoint1 = point.move1(direction)
                    if (!barriers.contains(newPoint1) && newPoint1.x in 0..endPoint.x && newPoint1.y in 0..endPoint.y) {
                        val newPath = mutableListOf<Point<Int>>().apply { addAll(path) }
                        queue.addLast(TraverseStep(direction, newPoint1, newPath))
                    }
                    val newDirection2 = direction.turnLeft()
                    val newPoint2 = point.move1(newDirection2)
                    if (!barriers.contains(newPoint2) && newPoint2.x in 0..endPoint.x && newPoint2.y in 0..endPoint.y) {
                        val newPath = mutableListOf<Point<Int>>().apply { addAll(path) }
                        queue.addLast(TraverseStep(newDirection2, newPoint2, newPath))
                    }
                    val newDirection3 = direction.turnRight()
                    val newPoint3 = point.move1(newDirection3)
                    if (!barriers.contains(newPoint3) && newPoint3.x in 0..endPoint.x && newPoint3.y in 0..endPoint.y) {
                        val newPath = mutableListOf<Point<Int>>().apply { addAll(path) }
                        queue.addLast(TraverseStep(newDirection3, newPoint3, newPath))
                    }
                }
                return null
            }
        }
    }

    override fun mapInputData(file: File): List<Point<Int>> =
        file.readLines().map { it.split(",") }.map { Point(it[1].toInt(), it[0].toInt()) }
}

private fun printMap18(data: List<Point<Int>>, path: List<Point<Int>>, endPoint: Point<Int>, visitors: Map<Point<Int>, Int>? = null) {
    for (x in 0..endPoint.x) {
        println()
        for (y in 0..endPoint.y) {
            val point = Point(x, y)
            when {
                visitors?.contains(point) == true -> print("?")
                path.contains(point) -> print("O")
                data.contains(point) -> print("#")
                else -> print(".")
            }
        }
    }
    println()
}

fun main() {
//    Day18().submitPart1TestInput() // 22
    Day18().submitPart1Input() // 226
//    Day18().submitPart2TestInput() //
//    Day18().submitPart2Input() //
}