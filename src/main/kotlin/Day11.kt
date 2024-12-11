package day01

import java.io.File

class Day11 : Base<List<String>, Long>(11) {

    private val memo = hashMapOf<String, Long>()

    override fun part1(input: List<String>): Long =
        input.sumOf { count(25, it) }

    override fun part2(input: List<String>): Long =
        input.sumOf { count(75, it) }

    private fun count(level: Int, input: String): Long =
        when {
            memo["$input $level"] != null -> memo["$input $level"]!!
            level <= 0 -> 1
            input == "0" -> count(level -1, "1")
            input.length % 2 == 0 -> {
                (count(level - 1, input.take(input.length / 2)) +
                        count(level - 1, input.takeLast(input.length / 2).toLong().toString()))
            }
            else -> (count(level - 1, (input.toLong() * 2024L).toString()))
        }.also { memo["$input $level"] = it }

    override fun mapInputData(file: File): List<String> =
        file.readLines().first().split(" ")
}

fun main() {
    Day11().submitPart1TestInput() // 55312
    Day11().submitPart1Input() // 183248
    Day11().submitPart2TestInput() // 65601038650482
    Day11().submitPart2Input() // 218811774248729
}