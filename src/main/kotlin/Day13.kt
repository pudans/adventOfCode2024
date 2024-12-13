package day01

import day01.utils.Point
import java.io.File

class Day13 : Base<List<Day13.Play>, Int>(13) {

    data class Play(
        val a: Point,
        val b: Point,
        val prize: Point
    )

    override fun part1(input: List<Play>): Int = 0

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