package day01

import java.io.File

class Day23 : Base<List<Pair<String, String>>, Int>(23) {

    override fun part1(input: List<Pair<String, String>>): Int = 0

    override fun part2(input: List<Pair<String, String>>): Int = 0

    override fun mapInputData(file: File): List<Pair<String, String>> =
        file.readLines().map { it.split("-") }.map { Pair(it[0], it[1]) }
}

fun main() {
    Day23().submitPart1TestInput() //
//    Day23().submitPart1Input() //
//    Day23().submitPart2TestInput() //
//    Day22().submitPart2Input() //
}