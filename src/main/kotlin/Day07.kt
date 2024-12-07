package day01

import java.io.File

class Day07 : Base<List<Day07.Data>, Long>(7) {

    data class Data(
        val sum: Long,
        val values: List<Long>
    )

    override fun part1(input: List<Data>): Long = input.fold(0) { r, data ->
        if (isValidPart1(data)) r + data.sum else r
    }

    private fun isValidPart1(data: Data, sum: Long = data.values[0], pos: Int = 0): Boolean =
        if (pos == data.values.lastIndex) {
            sum == data.sum
        } else {
            isValidPart1(data, sum * data.values[pos + 1], pos + 1) || isValidPart1(data, sum + data.values[pos + 1], pos + 1)
        }

    override fun part2(input: List<Data>): Long = input.fold(0) { r, data ->
        if (isValidPart2(data)) r + data.sum else r
    }

    private fun isValidPart2(data: Data, sum: Long = data.values[0], pos: Int = 0): Boolean =
        if (pos == data.values.lastIndex) {
            sum == data.sum
        } else {
            isValidPart2(data, sum * data.values[pos + 1], pos + 1)
                    || isValidPart2(data, sum + data.values[pos + 1], pos + 1)
                    || isValidPart2(data, (sum.toString() + (data.values[pos + 1]).toString()).toLong(), pos + 1)
        }

    override fun mapInputData(file: File): List<Data> =
        file.readLines().map {
            val split1 = it.split(": ")
            val split2 = split1[1].split(" ")
            return@map Data(
                sum = split1[0].toLong(),
                values = split2.map { it.toLong() }
            )
        }
}

fun main() {
    Day07().submitPart1TestInput() // 3749
    Day07().submitPart1Input() // 2299996598890
    Day07().submitPart2TestInput() // 11387
    Day07().submitPart2Input() // 362646859298554
}