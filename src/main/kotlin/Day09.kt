package day01

import java.io.File

class Day09 : Base<List<Int>, Long>(9) {

    override fun part1(input: List<Int>): Long =
        buildList {
            input.mapIndexed { index, int ->
                when {
                    index % 2 == 0 -> repeat(int) { add((index / 2).toString().first()) }
                    else -> repeat(int) { add('.') }
                }
            }
        }
            .let { list ->
                buildList {
                    var i1 = 0
                    var i2 = list.lastIndex
                    while (i1 <= i2) {
                        if (list[i1] != '.') {
                            add(list[i1]); i1++
                        } else if (list[i1] == '.' && list[i2] != '.') {
                            add(list[i2]); i2--; i1++
                        } else if (list[i2] == '.') {
                            i2--
                        }
                    }
                }
            }
            .map { it.toString().toInt() }
            .foldIndexed(0L) { index, r, it ->
                r + it * index
            }

    override fun part2(input: List<Int>): Long = 0

    override fun mapInputData(file: File): List<Int> =
        file.readLines().joinToString { it }.map { it.toString().toInt() }
}

fun main() {
    Day09().submitPart1TestInput() // 1928
    Day09().submitPart1Input() // 5776434966 is wrong
//    Day09().submitPart2TestInput()
//    Day09().submitPart2Input()
}