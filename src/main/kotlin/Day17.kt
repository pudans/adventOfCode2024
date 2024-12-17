package day01

import java.io.File

class Day17 : Base<Day17.Data, String>(17) {

    data class Data(
        val a: Long,
        val b: Long,
        val c: Long,
        val program: List<Int>
    )

    override fun part1(input: Data): String {
        return ""
    }

    override fun part2(input: Data): String {
        return ""
    }

    override fun mapInputData(file: File): Data {
        val lines = file.readLines()
        return Data(
            a = lines[0].removePrefix("Register A: ").toLong(),
            b = lines[1].removePrefix("Register B: ").toLong(),
            c = lines[2].removePrefix("Register C: ").toLong(),
            program = lines[4].removePrefix("Program: ").toCharArray().filter { it.isDigit() }
                .map { it.toString().toInt() }
        )
    }
}

fun main() {
    Day17().submitPart1TestInput() //
    Day17().submitPart1Input() //
//    Day17().submitPart2TestInput() //
//    Day17().submitPart2Input() //
}