package day01

import java.io.File
import kotlin.math.*

class Day11 : Base<List<String>, Int>(11) {

    override fun part1(input: List<String>): Int {
        var result = input
        repeat(25) {
            result = doRearrange1(result)
        }
        return result.size
    }

    private fun doRearrange1(input: List<String>): List<String> = buildList {
        input.forEach {
            when {
                it == "0" -> add("1")
                it.length % 2 == 0 -> {
                    add(it.take(it.length / 2))
                    add(it.takeLast(it.length / 2).toLong().toString())
                }
                else -> add((it.toLong() * 2024L).toString())
            }
        }
    }

    private fun doRearrange2(input: List<Long>): List<Long> = buildList {
        input.forEach {
            if (it == 0L) {
                add(1)
            } else {
                val d = it.countDigits()
                if (it.countDigits() % 2 == 0) {
                    add((it / (10.0.pow(d / 2))).toLong())
                    add((it % (10.0.pow(d / 2))).toLong())
                } else {
                    add(it * 2024L)
                }
            }
        }
    }

    override fun part2(input: List<String>): Int = 0

    private fun Long.countDigits(): Int {
        var length = 0
        var n = this
        while (n != 0L) {
            n /= 10
            length++
        }
        return length
    }

    override fun mapInputData(file: File): List<String> =
        file.readLines().first().split(" ")
}

fun main() {
    Day11().submitPart1TestInput() // 55312
    Day11().submitPart1Input() // 183248
//    Day11().submitPart2Input() //
}