package day01

import java.io.File

class Day19 : Base<Day19.Data, Int>(19) {

    data class Data(
        val pattern: List<String>,
        val designs: List<String>
    )

    override fun part1(input: Data): Int {
        return 0
    }

    override fun part2(input: Data): Int {
        return 0
    }

    override fun mapInputData(file: File): Data {
        val lines = file.readLines()
        return Data(
            pattern = lines.first().split(", "),
            designs = lines.drop(2)
        )
    }
}

fun main() {
    Day19().submitPart1TestInput() //
//    Day19().submitPart1Input() //
//    Day19().submitPart2TestInput() //
//    Day19().submitPart2Input() //
}