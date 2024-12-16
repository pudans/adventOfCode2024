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
        val visited = mutableListOf<Pair<PointX, Direction>>()
        val queue = LinkedList<TraverseStep>()
        queue.addLast(TraverseStep(Direction.RIGHT, input.start, mutableListOf(), 0))
        var result = Long.MAX_VALUE
        while (queue.isNotEmpty()) {
            val step = queue.poll()
            step.execute(input, queue, visited)?.let {
                result = min(result, it)
            }
            println("Step: ${queue.size} result: $result")
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
            visited: MutableList<Pair<PointX, Direction>>
        ): Long? {
            if (data.end == point) {
                val result = path.size.toLong() + rotates * 1000L
                println("Result: $result")
                printMap(data, path)
                return result
            } else {
                if (!visited.contains(Pair(point, direction))) {
                    path.add(point)
                    visited.add(Pair(point, direction))
                    val newPoint1 = point.move1(direction)
                    if (!data.barriers.contains(newPoint1) && !path.contains(newPoint1)) {
                        val newPath = mutableListOf<PointX>().apply { addAll(path) }
                        queue.addLast(TraverseStep(direction, newPoint1, newPath, rotates))
                    }
                    val newDirection2 = direction.turnLeft()
                    val newPoint2 = point.move1(newDirection2)
                    if (!data.barriers.contains(newPoint2) && !path.contains(newPoint2)) {
                        val newPath = mutableListOf<PointX>().apply { addAll(path) }
                        queue.addLast(TraverseStep(newDirection2, newPoint2, newPath, rotates + 1))
                    }
                    val newDirection3 = direction.turnRight()
                    val newPoint3 = point.move1(newDirection3)
                    if (!data.barriers.contains(newPoint3) && !path.contains(newPoint3)) {
                        val newPath = mutableListOf<PointX>().apply { addAll(path) }
                        queue.addLast(TraverseStep(newDirection3, newPoint3, newPath, rotates + 1))
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

private fun printMap(data: Day16.Data, path: List<PointX>) {
    val maxX = data.barriers.maxOf { it.x }
    val maxY = data.barriers.maxOf { it.y }
    for (x in 0..maxX) {
        println()
        for (y in 0..maxY) {
            val point = PointX(x, y)
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
    Day16().submitPart1TestInput() // 7036 / 11048
    Day16().submitPart1Input() // 170592 failed
//    Day16().submitPart2TestInput() //
//    Day16().submitPart2Input() //
}