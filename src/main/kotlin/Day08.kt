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
            buildList {
                list.map { (c1, point1) ->
                    list.map { (c2, point2) ->
                        if (c1 == c2 && point1 != point2) {
                            add(Point(point1.x - (point2.x - point1.x), point1.y - (point2.y - point1.y)))
                        }
                    }
                }
            }
        }
            .filter { it.x >= 0 && it.x <= input.lastIndex && it.y >= 0 && it.y <= input.first().lastIndex }
            .distinct()
            .size


    override fun part2(input: List<CharArray>): Int = 0

    override fun mapInputData(file: File): List<CharArray> =
        file.readLines().map { it.toCharArray() }
}

fun main() {
    Day08().submitPart1TestInput() // 14
    Day08().submitPart1Input() // 396
//    Day08().submitPart2TestInput() // 11387
//    Day08().submitPart2Input() // 362646859298554
}