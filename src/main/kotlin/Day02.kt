package day01

import java.io.File

class Day02 : Base<List<List<Int>>, Int>(2) {

    private fun check(input: List<Int>): Boolean {
        if (input[0] - input[1] == 0) return false
        val increasing = input[1] - input[0] > 0
        if (increasing) {
            for (i in 1..input.lastIndex) {
                if (input[i] - input[i - 1] !in 1..3) return false
            }
        } else {
            for (i in 1..input.lastIndex) {
                if (input[i - 1] - input[i] !in 1..3) return false
            }
        }
        return true
    }


    override fun part1(input: List<List<Int>>): Int =
        input.count { check(it) }

    override fun part2(input: List<List<Int>>): Int =
        input.count {
            val result = check(it)
            if (result) return@count true
            for (i in it.indices) {
                if (check(it.toMutableList().apply { removeAt(i) })) return@count true
            }
            return@count false
        }

    override fun mapInputData(file: File): List<List<Int>> =
        file.readLines().map { it.split(" ").map { it.toInt() } }
}

fun main() {
    Day02().submitAll()
}