package day01

import java.io.File

class Day03 : Base<String, Int>(3) {

    private fun String.find(): List<String> =
        "mul\\(\\d{1,3},\\d{1,3}\\)"
            .toRegex()
            .findAll(this).toList().map { it.value }

    private fun List<String>.mult(): Int =
        map { it.split(",") }
            .fold(0) { r, t -> r + t[0].drop(4).toInt() * t[1].dropLast(1).toInt() }

    override fun part1(input: String): Int =
        input.find().mult()

    override fun part2(input: String): Int {
        val inputs = mutableListOf<String>()
        var doIndex = 0
        while (true) {
            var dontIndex = input.indexOf("don't()", doIndex)
            if (dontIndex < 0) {
                dontIndex = input.lastIndex
            }
            inputs.addAll(input.substring(doIndex, dontIndex).find())
            doIndex = input.indexOf("do()", dontIndex)
            if (doIndex < 0) break
        }
        return inputs.mult()
    }

    override fun mapInputData(file: File): String =
        file.readLines().joinToString { it }
}

fun main() {
    Day03().submitAll()
}