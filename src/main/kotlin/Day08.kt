package day01

import java.awt.Point
import java.io.File

class Day08 : Base<List<CharArray>, Int>(8) {

    override fun part1(input: List<CharArray>): Int =
        buildList {
            input.mapIndexed { x, chars ->
                chars.mapIndexed { y, c ->
                    if (c != '.')
                        add(c to Point(x, y))
                }
            }
        }.let { list ->
            buildSet {
                list.map { (c1, point1) ->
                    list.map { (c2, point2) ->
                        if (c1 == c2 && point1 != point2) {
                            val dx = point1.x - (point2.x - point1.x)
                            val dy = point1.y - (point2.y - point1.y)
                            val newPoint = Point(dx, dy)
                            if (newPoint.x in 0..input.lastIndex && newPoint.y in 0..input.first().lastIndex) {
                                add(newPoint)
                            }
                        }
                    }
                }
            }
        }.size


    override fun part2(input: List<CharArray>): Int =
        buildList {
            input.mapIndexed { x, chars ->
                chars.mapIndexed { y, c ->
                    if (c != '.')
                        add(c to Point(x, y))
                }
            }
        }.let { list ->
            buildSet {
                list.map { (c1, point1) ->
                    add(point1)
                    list.map { (c2, point2) ->
                        if (c1 == c2 && point1 != point2) {
                            var mult = 1
                            val dx = point1.x - (point2.x - point1.x)
                            val dy = point1.y - (point2.y - point1.y)
                            var newPoint = Point(dx, dy)

                            while (newPoint.x in 0..input.lastIndex && newPoint.y in 0..input.first().lastIndex) {
                                if (list.find { it.second == newPoint } == null) {
                                    add(newPoint)
                                }
                                mult++
                                val dx1 = point1.x - ((point2.x - point1.x) * mult)
                                val dy1 = point1.y - ((point2.y - point1.y) * mult)
                                newPoint = Point(dx1, dy1)
                            }
                        }
                    }
                }
            }
        }.size

    override fun mapInputData(file: File): List<CharArray> =
        file.readLines().map { it.toCharArray() }
}

fun main() {
    Day08().submitPart1TestInput() // 14
    Day08().submitPart1Input() // 396
    Day08().submitPart2TestInput() // 34
    Day08().submitPart2Input() // 1200
}