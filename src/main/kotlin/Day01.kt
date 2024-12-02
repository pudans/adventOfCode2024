package day01

import java.io.File
import kotlin.math.abs

class Day01 : Base<Pair<List<Int>, List<Int>>, Int>(1) {

    override fun part1(input: Pair<List<Int>, List<Int>>): Int {
        val firstSorted = input.first.sorted()
        val secondSorted = input.second.sorted()
        return firstSorted.foldIndexed(0) { i, r, t ->
            r + (abs(t - secondSorted[i]))
        }
    }

    override fun part2(input: Pair<List<Int>, List<Int>>): Int =
        input.first.fold(0) { r, t ->
            r + input.second.count { it == t } * t
        }

    override fun mapInputData(file: File): Pair<List<Int>, List<Int>> =
        file.readLines()
            .map { it.split("   ").map { it.toInt() } }
            .fold(Pair(mutableListOf<Int>(), mutableListOf<Int>())) { r, t ->
                r.first.add(t[0])
                r.second.add(t[1])
                r
            }
}

fun main() {
    Day01().submitAll()
}