package day01

import java.io.File

class Day10 : Base<List<List<Int>>, Int>(10) {

    override fun part1(input: List<List<Int>>): Int = 0

    override fun part2(input: List<List<Int>>): Int = 0

    override fun mapInputData(file: File): List<List<Int>> =
        file.readLines().map { it.map { it.toString().toInt() } }
}

fun main() {
    Day10().submitPart1TestInput() // 36
//    Day10().submitPart1Input() //
//    Day10().submitPart2TestInput() //
//    Day10().submitPart2Input() //
}