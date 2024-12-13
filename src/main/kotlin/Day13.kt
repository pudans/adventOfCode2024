package day01

import day01.utils.Point
import java.io.File
import kotlin.math.min

class Day13 : Base<List<Day13.Play>, Int>(13) {

    companion object {
        private const val MAX_PRESS = 100
    }

    data class Play(
        val a: Point,
        val b: Point,
        val prize: Point
    )

    private val memo = mutableMapOf<String, Int>()

    override fun part1(input: List<Play>): Int = listOf(input[0]).fold(0) { r, play ->
        val result = pressButton(play, Point(0, 0), 0, 0)
        println(result)

        r + (result ?: 0)
    }

    private fun pressButton(play: Play, point: Point, a: Int, b: Int): Int? =
        if (memo["$point $a $b"] != null) {
            memo["$point $a $b"]
        } else if (point.x == play.prize.x && point.y == play.prize.y) {
            println("$point $a $b")
            val result = a * 3 + b
            memo["$point $a $b"] = result
            result
        } else if (point.x < play.prize.x && point.y < play.prize.y && a < MAX_PRESS && b < MAX_PRESS) {
            val point1 = Point(point.x + play.a.x, point.y + play.a.y)
            val result1 = pressButton(play, point1, a + 1, b)
            val point2 = Point(point.x + play.b.x, point.y + play.b.y)
            val result2 = pressButton(play, point2, a, b + 1)
            min(result1 ?: Int.MAX_VALUE, result2 ?: Int.MAX_VALUE)
        } else {
            memo["$point $a $b"] = Int.MAX_VALUE
            null
        }

    override fun part2(input: List<Play>): Int = 0

    override fun mapInputData(file: File): List<Play> =
        file.readLines().chunked(4).map { line ->
            val d1 = line[0].removePrefix("Button A: ").split(", ").map { it.split("+") }
            val d2 = line[1].removePrefix("Button B: ").split(", ").map { it.split("+") }
            val d3 = line[2].removePrefix("Prize: ").split(", ").map { it.split("=") }
            Play(
                a = Point(d1[0][1].toInt(), d1[1][1].toInt()),
                b = Point(d2[0][1].toInt(), d2[1][1].toInt()),
                prize = Point(d3[0][1].toInt(), d3[1][1].toInt())
            )
        }
}

fun main() {
    Day13().submitPart1TestInput() //
//    Day13().submitPart1Input() //
//    Day13().submitPart2TestInput() //
//    Day13().submitPart2Input() //
}