package day01

import day01.utils.Point
import java.io.File

class Day14 : Base<List<Day14.Data>, Int>(14) {

    data class Data(
        val start: Point<Int>,
        val velocity: Point<Int>
    )

    override fun part1(input: List<Data>): Int {
        val width = 101 // 11
        val height = 103 // 7
        return input.map { data ->
            var position = data.start
            repeat(100) {
                position = Point(position.x + data.velocity.x, position.y + data.velocity.y)
                if (position.x < 0) position = Point(position.x + width, position.y)
                if (position.x >= width) position = Point(position.x - width, position.y)
                if (position.y < 0) position = Point(position.x, position.y + height)
                if (position.y >= height) position = Point(position.x, position.y - height)
            }
            position
        }.map {
            Point(it.x % width, it.y % height)
        }.let { positions ->
            val q1 = positions.count { it.x < width / 2 && it.y < height / 2 }
            val q2 = positions.count { it.x > width / 2 && it.y < height / 2 }
            val q3 = positions.count { it.x < width / 2 && it.y > height / 2 }
            val q4 = positions.count { it.x > width / 2 && it.y > height / 2 }
            q1 * q2 * q3 * q4
        }
    }

    override fun part2(input: List<Data>): Int = 0

    override fun mapInputData(file: File): List<Data> =
        file.readLines().map { line ->
            val d1 = line.split(" ")
            val d2 = d1[0].removePrefix("p=").split(",").map { it.toInt() }
            val d3 = d1[1].removePrefix("v=").split(",").map { it.toInt() }
            Data(
                start = Point(d2[0], d2[1]),
                velocity = Point(d3[0], d3[1])
            )
        }
}

fun main() {
    Day14().submitPart1TestInput() // 12 / 21
    Day14().submitPart1Input() // 228421332
//    Day14().submitPart2TestInput() //
//    Day14().submitPart2Input() //
}