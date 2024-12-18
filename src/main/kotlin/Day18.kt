package day01

import day01.utils.*
import java.io.File
import java.util.*
import kotlin.math.min

class Day18 : Base<List<PointX>, String>(18) {

    override fun part1(input: List<PointX>): String {
        val maxBarriresCount = 1024 // 12
        val start = PointX(0, 0)
//        val end = PointX(6, 6)
        val end = PointX(70, 70)
        val barriers = input.take(maxBarriresCount)

        val visited = mutableMapOf<PointX, Int>()
        val queue = LinkedList<TraverseStep>()
        queue.addLast(TraverseStep(Direction.DOWN, start, mutableListOf()))
        var result = Int.MAX_VALUE
        while (queue.isNotEmpty()) {
            val step = queue.poll()
            step.execute(end, barriers, queue, visited)?.let {
                val newResult = it
                printMap18(barriers, newResult, end)
                result = min(result, newResult.size)
            }
        }
        return result.toString()
    }

    override fun part2(input: List<PointX>): String {
        return "0"
    }

    class TraverseStep(
        private val direction: Direction,
        private val point: PointX,
        private val path: MutableList<PointX>
    ) {

        fun execute(
            endPoint: PointX,
            barriers: List<PointX>,
            queue: LinkedList<TraverseStep>,
            visited: MutableMap<PointX, Int>
        ): List<PointX>? {
            if (endPoint == point) {
                return path
            } else {
                if (visited[point] == null || visited[point]!! > path.size) {
                    visited[point] = path.size
                    path.add(point)
                    val newPoint1 = point.move1(direction)
                    if (!barriers.contains(newPoint1) && newPoint1.x in 0..endPoint.x && newPoint1.y in 0..endPoint.y) {
                        val newPath = mutableListOf<PointX>().apply { addAll(path) }
                        queue.addLast(TraverseStep(direction, newPoint1, newPath))
                    }
                    val newDirection2 = direction.turnLeft()
                    val newPoint2 = point.move1(newDirection2)
                    if (!barriers.contains(newPoint2) && newPoint2.x in 0..endPoint.x && newPoint2.y in 0..endPoint.y) {
                        val newPath = mutableListOf<PointX>().apply { addAll(path) }
                        queue.addLast(TraverseStep(newDirection2, newPoint2, newPath))
                    }
                    val newDirection3 = direction.turnRight()
                    val newPoint3 = point.move1(newDirection3)
                    if (!barriers.contains(newPoint3) && newPoint3.x in 0..endPoint.x && newPoint3.y in 0..endPoint.y) {
                        val newPath = mutableListOf<PointX>().apply { addAll(path) }
                        queue.addLast(TraverseStep(newDirection3, newPoint3, newPath))
                    }
                }
                return null
            }
        }
    }

    override fun mapInputData(file: File): List<PointX> =
        file.readLines().map { it.split(",") }.map { PointX(it[1].toInt(), it[0].toInt()) }
}

private fun printMap18(data: List<PointX>, path: List<PointX>, endPoint: PointX) {
    for (x in 0..endPoint.x) {
        println()
        for (y in 0..endPoint.y) {
            val point = PointX(x, y)
            when {
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