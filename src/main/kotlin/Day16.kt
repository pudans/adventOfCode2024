package day01

import day01.utils.*
import java.io.File
import java.util.LinkedList
import kotlin.math.min

class Day16 : Base<Day16.Data, Int>(16) {

    data class Data(
        val start: PointX,
        val end: PointX,
        val barriers: Set<PointX>
    )

    override fun part1(input: Data): Int {
        val visited = mutableMapOf<PointX, Long>()
        val queue = LinkedList<TraverseStep>()
        queue.addLast(TraverseStep(Direction.RIGHT, input.start, mutableListOf(), 0))
        var result = Int.MAX_VALUE
        while (queue.isNotEmpty()) {
            val step = queue.poll()
            step.execute(input, queue, visited)?.let {
                val newResult = it.first.size + it.second * 1000
                result = min(result, newResult)
            }
        }
        return result
    }

    class TraverseStep(
        private val direction: Direction,
        private val point: PointX,
        private val path: MutableList<PointX>,
        private val rotates: Int
    ) {

        fun execute(
            data: Data,
            queue: LinkedList<TraverseStep>,
            visited: MutableMap<PointX, Long>
        ): Pair<List<PointX>, Int>? {
            val result = path.size + rotates * 1000L
            if (data.end == point) {
                return Pair(path, rotates)
            } else {
                if (visited[point] == null || visited[point]!! >= result - 1000L) {
                    path.add(point)
                    visited[point] = result
                    val newPoint1 = point.move1(direction)
                    if (!data.barriers.contains(newPoint1)) {
                        val newPath = mutableListOf<PointX>().apply { addAll(path) }
                        queue.addLast(TraverseStep(direction, newPoint1, newPath, rotates))
                    }
                    val newDirection2 = direction.turnLeft()
                    val newPoint2 = point.move1(newDirection2)
                    if (!data.barriers.contains(newPoint2)) {
                        val newPath = mutableListOf<PointX>().apply { addAll(path) }
                        queue.addLast(TraverseStep(newDirection2, newPoint2, newPath, rotates + 1))
                    }
                    val newDirection3 = direction.turnRight()
                    val newPoint3 = point.move1(newDirection3)
                    if (!data.barriers.contains(newPoint3)) {
                        val newPath = mutableListOf<PointX>().apply { addAll(path) }
                        queue.addLast(TraverseStep(newDirection3, newPoint3, newPath, rotates + 1))
                    }
                }
                return null
            }
        }
    }

    override fun part2(input: Data): Int {
        val visited = mutableMapOf<PointX, Long>()
        val queue = LinkedList<TraverseStep>()
        queue.addLast(TraverseStep(Direction.RIGHT, input.start, mutableListOf(), 0))
        var result = Long.MAX_VALUE
        val spotPath = mutableSetOf<PointX>()
        while (queue.isNotEmpty()) {
            val step = queue.poll()
            step.execute(input, queue, visited)?.let {
                val newResult = it.first.size + it.second * 1000L
                if (newResult == result) {
                    spotPath.addAll(it.first)
                } else if (newResult < result) {
                    spotPath.clear()
                    spotPath.addAll(it.first)
                    result = min(result, newResult)
                } else {
                }
            }
        }
        spotPath.add(input.start)
        spotPath.add(input.end)

        return spotPath.size
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

//private fun printMap(data: Day16.Data, path: Set<PointX>) {
//    val maxX = data.barriers.maxOf { it.x }
//    val maxY = data.barriers.maxOf { it.y }
//    for (x in 0..maxX) {
//        println()
//        for (y in 0..maxY) {
//            val point = PointX(x, y)
//            when {
//                data.start == point -> print("S")
//                data.end == point -> print("E")
//                path.contains(point) -> print("O")
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
    Day16().submitPart2TestInput() // 45 / 64
    Day16().submitPart2Input() // 692
}