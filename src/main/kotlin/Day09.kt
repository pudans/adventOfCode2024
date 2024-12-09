package day01

import java.io.File

class Day09 : Base<List<Int>, Long>(9) {

    override fun part1(input: List<Int>): Long = input.extend()
        .let { list ->
            buildList {
                var i1 = 0
                var i2 = list.lastIndex
                while (i1 <= i2) {
                    if (list[i1] != ".") {
                        add(list[i1]); i1++
                    } else if (list[i1] == "." && list[i2] != ".") {
                        add(list[i2]); i2--; i1++
                    } else if (list[i2] == ".") {
                        i2--
                    }
                }
            }
        }
        .calculateResult()

    private fun List<Int>.extend(): List<String> = buildList {
        this@extend.mapIndexed { index, int ->
            when {
                index % 2 == 0 -> repeat(int) { add((index / 2).toString()) }
                else -> repeat(int) { add(".") }
            }
        }
    }

    private fun List<String>.calculateResult() = foldIndexed(0L) { index, r, it ->
        if (it != ".") r + it.toInt() * index else r
    }

    override fun part2(input: List<Int>): Long = input.extend()
        .let { list ->
            val result = list.toMutableList()
            var i2 = list.lastIndex
            while (i2 >= 0) {
                if (result[i2] != ".") {
                    val size = findBlockSize(i2, result)
                    var d1 = 0
                    var emptySpace = 0
                    while (d1 < i2 && emptySpace != size) {
                        if (result[d1] == ".") emptySpace++ else emptySpace = 0
                        d1++
                    }
                    if (emptySpace == size) {
                        d1 -= size
                        i2 = i2 - size + 1
                        repeat(size) {
                            result[d1 + it] = result[i2 + it]
                            result[i2 + it] = "."
                        }
                    } else {
                        i2 -= size
                    }
                } else {
                    i2--
                }
            }
            result
        }
        .calculateResult()

    private fun findBlockSize(i2: Int, list: List<String>): Int {
        var i22 = i2
        var size = 0
        while (i22 >= 0 && list[i22] == list[i2]) {
            size++
            i22--
        }
        return size
    }

    override fun mapInputData(file: File): List<Int> =
        file.readLines().joinToString { it }.map { it.toString().toInt() }
}

fun main() {
    Day09().submitPart1TestInput() // 1928
    Day09().submitPart1Input() // 6241633730082
    Day09().submitPart2TestInput() // 2858
    Day09().submitPart2Input() // 6265268809555
}