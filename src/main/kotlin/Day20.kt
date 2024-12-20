package day01

import day01.utils.Direction
import day01.utils.PointX
import day01.utils.move1
import java.io.File

class Day20 : Base<Day20.Data, Int>(20) {

    data class Data(
        val start: PointX,
        val end: PointX,
        val walls: List<PointX>
    )

    override fun part1(input: Data): Int {
        val atLeastPicoseconds = 100 // 0
        val path = mutableListOf<PointX>()
        val result = mutableMapOf<Int, Int>()
        var point = input.start
        while (point != input.end) {
            path.add(point)
            point = Direction.entries.map { point.move1(it) }.find { !path.contains(it) && !input.walls.contains(it) }!!
        }
        path.add(input.end)
        path.forEachIndexed { index, pathPoint ->
            Direction.entries.forEach { direction ->
                pathPoint.move1(direction).takeIf { input.walls.contains(it) }?.move1(direction)
                    ?.let { path.indexOf(it) }?.takeIf { it > 0 }?.takeIf { it > index }
                    ?.let { result[it - index - 2] = result.getOrDefault(it - index - 2, 0) + 1 }
            }
        }
        return result.filter { entry -> entry.key >= atLeastPicoseconds }.entries.sumOf { it.value }
    }

    override fun part2(input: Data): Int = 0

    override fun mapInputData(file: File): Data {
        var start: PointX? = null
        var end: PointX? = null
        val walls = mutableListOf<PointX>()
        file.readLines().forEachIndexed { x, line ->
            line.forEachIndexed { y, char ->
                when (char) {
                    'S' -> start = PointX(x, y)
                    'E' -> end = PointX(x, y)
                    '#' -> walls.add(PointX(x, y))
                }
            }
        }
        return Data(start!!, end!!, walls)
    }
//
//    private fun print(data: Data, path: List<PointX>) {
//        val maxX = data.walls.maxOf { it.x }
//        val maxY = data.walls.maxOf { it.y }
//        for (x in 0..maxX) {
//            println()
//            for (y in 0..maxY) {
//                val point = PointX(x, y)
//                when {
//                    point == data.start -> print("S")
//                    point == data.end -> print("E")
//                    path.contains(point) -> print("Z")
//                    data.walls.contains(point) -> print("#")
//                    else -> print(".")
//                }
//            }
//        }
//        println()
//    }
}

fun main() {
    Day20().submitPart1TestInput() // 44 / 0
    Day20().submitPart1Input() // 1507
//    Day20().submitPart2TestInput() //
//    Day20().submitPart2Input() //
}