package day01

import day01.utils.*
import java.io.File
import java.util.LinkedList
import kotlin.math.min

class Day16 : Base<Day16.Data, Long>(16) {

    data class Data(
        val start: PointX,
        val end: PointX,
        val barriers: Set<PointX>
    )

    override fun part1(input: Data): Long {
        val visited = mutableMapOf<PointX, Long>()
        val queue = LinkedList<TraverseStep>()
        queue.addLast(TraverseStep(Direction.RIGHT, input.start, 0, 0))
        var result = Long.MAX_VALUE
        while (queue.isNotEmpty()) {
            val step = queue.poll()
            step.execute(input, queue, visited)?.let {
                result = min(result, it)
            }
        }
        return result
    }

    class TraverseStep(
        private val direction: Direction,
        private val point: PointX,
        private val pathSize: Int,
        private val rotates: Int
    ) {

        fun execute(
            data: Data,
            queue: LinkedList<TraverseStep>,
            visited: MutableMap<PointX, Long>
        ): Long? {
            val result = pathSize + rotates * 1000L
            if (data.end == point) {
                return result
            } else {
                if (visited[point] == null || visited[point]!! > result + 1) {
                    visited[point] = result + 1
                    val newPoint1 = point.move1(direction)
                    if (!data.barriers.contains(newPoint1)) {
                        queue.addLast(TraverseStep(direction, newPoint1, pathSize + 1, rotates))
                    }
                    val newDirection2 = direction.turnLeft()
                    val newPoint2 = point.move1(newDirection2)
                    if (!data.barriers.contains(newPoint2)) {
                        queue.addLast(TraverseStep(newDirection2, newPoint2, pathSize + 1, rotates + 1))
                    }
                    val newDirection3 = direction.turnRight()
                    val newPoint3 = point.move1(newDirection3)
                    if (!data.barriers.contains(newPoint3)) {
                        queue.addLast(TraverseStep(newDirection3, newPoint3, pathSize + 1, rotates + 1))
                    }
                }
                return null
            }
        }
    }

    override fun part2(input: Data): Long {
        return 0L
    }

    override fun mapInputData(file: File): Data {
        var start: PointX? = null
        var end: PointX? = null
        val barriers = mutableSetOf<PointX>()
        file.readLines().forEachIndexed { x, line ->
            line.forEachIndexed { y, c ->
                when (c) {
                    '#' -> barriers.add(PointX(x, y))
                    'S' -> start = PointX(x, y)
                    'E' -> end = PointX(x, y)
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

//private fun printMap(data: Day16.Data, path: List<PointX>, visited: MutableList<Pair<PointX, Direction>>) {
//    val maxX = data.barriers.maxOf { it.x }
//    val maxY = data.barriers.maxOf { it.y }
//    for (x in 0..maxX) {
//        println()
//        for (y in 0..maxY) {
//            val point = PointX(x, y)
//            when {
//                visited.find { it.first == point } != null -> print("X")
//                data.start == point -> print("S")
//                data.end == point -> print("E")
//                path.contains(point) -> print("O")
//                visited.find { it.first == point } != null -> print("X")
//                data.barriers.contains(point) -> print("#")
//                else -> print(".")
//            }
//        }
//    }
//    println()
//}

fun main() {
    Day16().submitPart1TestInput() // 7036 / 11048
    Day16().submitPart1Input() // 160624
//    Day16().submitPart2TestInput() //
//    Day16().submitPart2Input() //
}