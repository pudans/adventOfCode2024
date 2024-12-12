package day01

import java.io.File

class Day12 : Base<List<CharArray>, Long>(12) {

    override fun part1(input: List<CharArray>): Long = 0L

    override fun part2(input: List<CharArray>): Long = 0L

    override fun mapInputData(file: File): List<CharArray> =
        file.readLines().map { it.toCharArray() }
}

fun main() {
    Day12().submitPart1TestInput() //
//    Day12().submitPart1Input() //
//    Day12().submitPart2TestInput() //
//    Day12().submitPart2Input() //
}