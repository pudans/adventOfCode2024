package day01

import java.io.File

class Day25 : Base<Day25.Data, Int>(25) {

    data class Data(
        val keys: List<List<Int>>,
        val locks: List<List<Int>>
    )

    override fun part1(input: Data): Int {
        println(input)
        return 0
    }

    override fun part2(input: Data): Int = 0

    override fun mapInputData(file: File): Data {
        val keys = mutableListOf<List<Int>>()
        val locks = mutableListOf<List<Int>>()
        val fields = file.readLines().chunked(8)
        fields.forEach { field ->
            val shape = mutableListOf(0, 0, 0, 0, 0)
            for (i in 1..6) {
                field[i].forEachIndexed { index, c ->
                    if (c == '#') shape[index] = shape[index] + 1
                }
            }
            val isKey = field[0][0] == '#'
            if (isKey) keys.add(shape) else locks.add(shape)
        }
        return Data(keys, locks)
    }

}

fun main() {
    Day25().submitPart1TestInput() //
//    Day25().submitPart1Input() //
//    Day25().submitPart2TestInput() //
//    Day25().submitPart2Input() //
}