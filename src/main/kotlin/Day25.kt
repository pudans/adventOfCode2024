package day01

import java.io.File

class Day25 : Base<Day25.Data, Int>(25) {

    data class Data(
        val keys: List<List<Int>>,
        val locks: List<List<Int>>
    )

    override fun part1(input: Data): Int {
        var result = 0
        input.keys.forEach { key ->
            input.locks.forEach { lock ->
                var res = true
                for (i in 0..4) {
                    res = res && lock[i] + key[i] <= 5
                }
                if (res) result++
            }
        }
        return result
    }

    override fun part2(input: Data): Int = 0

    override fun mapInputData(file: File): Data {
        val keys = mutableListOf<List<Int>>()
        val locks = mutableListOf<List<Int>>()
        val fields = file.readLines().chunked(8)
        fields.forEach { field ->
            val shape = mutableListOf(0, 0, 0, 0, 0)
            for (i in 1..5) {
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
    Day25().submitPart1TestInput() // 3
    Day25().submitPart1Input() //
//    Day25().submitPart2TestInput() //
//    Day25().submitPart2Input() //
}