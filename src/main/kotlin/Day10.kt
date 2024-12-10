package day01

import java.awt.Point
import java.io.File

class Day10 : Base<List<List<Int>>, Int>(10) {

    override fun part1(input: List<List<Int>>): Int =
        findStart(input).fold(0) { sum, startPoint ->
            fun traverse(x: Int, y: Int, prevValue: Int, points: MutableSet<Point>) {
                if (x !in 0..input.lastIndex) return
                if (y !in 0..input.first().lastIndex) return
                val currentValue = input[x][y]

                if (currentValue - prevValue != 1) return
                if (currentValue == 9) {
                    points.add(Point(x, y))
                    return
                }
                traverse(x + 1, y, currentValue, points)
                traverse(x, y + 1, currentValue, points)
                traverse(x - 1, y, currentValue, points)
                traverse(x, y - 1, currentValue, points)
            }
            val points = mutableSetOf<Point>()
            traverse(startPoint.x, startPoint.y, -1, points)
            sum + points.size
        }

    override fun part2(input: List<List<Int>>): Int =
        findStart(input).fold(0) { sum, startPoint ->
            fun traverse(x: Int, y: Int, prevValue: Int, points: MutableList<Point>) {
                if (x !in 0..input.lastIndex) return
                if (y !in 0..input.first().lastIndex) return
                val currentValue = input[x][y]

                if (currentValue - prevValue != 1) return
                if (currentValue == 9) {
                    points.add(Point(x, y))
                    return
                }
                traverse(x + 1, y, currentValue, points)
                traverse(x, y + 1, currentValue, points)
                traverse(x - 1, y, currentValue, points)
                traverse(x, y - 1, currentValue, points)
            }
            val points = mutableListOf<Point>()
            traverse(startPoint.x, startPoint.y, -1, points)
            sum + points.size
        }

    private fun findStart(input: List<List<Int>>): List<Point> =
        buildList {
            input.forEachIndexed { index1, ints ->
                ints.forEachIndexed { index2, i ->
                    if (i == 0) add(Point(index1, index2))
                }
            }
        }

    override fun mapInputData(file: File): List<List<Int>> =
        file.readLines().map { it.map { it.toString().toInt() } }
}

fun main() {
    Day10().submitPart1TestInput() // 36
    Day10().submitPart1Input() // 468
    Day10().submitPart2TestInput() // 81
    Day10().submitPart2Input() // 966
}