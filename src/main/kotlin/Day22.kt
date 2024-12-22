package day01

import java.io.File

class Day22 : Base<List<Long>, Long>(22) {

    override fun part1(input: List<Long>): Long = 0L

    override fun part2(input: List<Long>): Long = 0L

    override fun mapInputData(file: File): List<Long> = file.readLines().map { it.toLong() }
}

fun main() {
    Day22().submitPart1TestInput() //
//    Day22().submitPart1Input() //
//    Day22().submitPart2TestInput() //
//    Day22().submitPart2Input() //
}