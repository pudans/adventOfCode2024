package day01

import day01.utils.PointX
import java.io.File

class Day21 : Base<List<String>, Long>(21) {

    private val numKeys = mapOf(
        '7' to PointX(0, 0),
        '8' to PointX(0, 1),
        '9' to PointX(0, 2),
        '4' to PointX(1, 0),
        '5' to PointX(1, 1),
        '6' to PointX(1, 2),
        '1' to PointX(2, 0),
        '2' to PointX(2, 1),
        '3' to PointX(2, 2),
        '0' to PointX(3, 1),
        'A' to PointX(3, 2)
    )

    private val dirKeys = mapOf(
        '^' to PointX(0, 1),
        'A' to PointX(0, 2),
        '<' to PointX(1, 0),
        'v' to PointX(1, 1),
        '>' to PointX(1, 2)
    )

    override fun part1(input: List<String>): Long = 0L

    override fun part2(input: List<String>): Long = 0L

    override fun mapInputData(file: File): List<String> = file.readLines()
}

fun main() {
    Day21().submitPart1TestInput() //
//    Day21().submitPart1Input() //
//    Day21().submitPart2TestInput() //
//    Day21().submitPart2Input() //
}