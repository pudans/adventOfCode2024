package day01

import day01.utils.PointX
import java.io.File

class Day20 : Base<Day20.Data, Long>(20) {

    data class Data(
        val start: PointX,
        val end: PointX,
        val walls: List<PointX>
    )

    override fun part1(input: Data): Long = 0L

    override fun part2(input: Data): Long = 0L

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
}

fun main() {
    Day20().submitPart1TestInput() //
//    Day20().submitPart1Input() //
//    Day20().submitPart2TestInput() //
//    Day20().submitPart2Input() //
}