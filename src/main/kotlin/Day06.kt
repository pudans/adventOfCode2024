package day01

import java.awt.Point
import java.io.File

class Day06 : Base<List<String>, Int>(6) {

    override fun part1(input: List<String>): Int {
        val start = getStart(input)
        val path = mutableSetOf<Pair<Int, Int>>()

        var direction = 2 // 1-left, 2-up. 3-right, 4-bottom
        var x = start.first
        var y = start.second

        while (true) {
            if (x < 0 || y < 0 || x > input.lastIndex || y > input.first().lastIndex) {
                break
            }
            path.add(Pair(x, y))

            var x1 = when(direction) {
                2 -> x - 1
                4 -> x + 1
                else -> x
            }
            var y1 = when(direction) {
                1 -> y - 1
                3 -> y + 1
                else -> y
            }
            if (x1 < 0 || y1 < 0 || x1 > input.lastIndex || y1 > input.first().lastIndex) {
                break
            }
            if (input[x1][y1] == '#') {
                when(direction) {
                    1 -> { x1 = x - 1; y1 = y; direction = 2 }
                    2 -> { x1 = x; y1 = y + 1; direction = 3 }
                    3 -> { x1 = x + 1; y1 = y; direction = 4 }
                    4 -> { x1 = x; y1 = y - 1; direction = 1 }
                }
            }
            x = x1
            y = y1
        }

        return path.size
    }

    private fun getStart(input: List<String>): Pair<Int, Int> {
        input.forEachIndexed { i1, it1 -> it1.forEachIndexed { i2, it2 -> if (it2 == '^') return Pair(i1, i2) } }
        return Pair(0, 0)
    }

    override fun part2(input: List<String>): Int {
        val start = getStart(input)
        val path = mutableSetOf<Pair<Int, Point>>()

        val result = mutableSetOf<Point>()

        var direction = 2 // 1-left, 2-up. 3-right, 4-bottom
        var x = start.first
        var y = start.second

        while (true) {
            if (x < 0 || y < 0 || x > input.lastIndex || y > input.first().lastIndex) {
                break
            }
            path.add(Pair(direction, Point(x, y)))

            val blockedDirection = when(direction) {
                in 1..3 -> direction + 1
                else -> 1
            }

            var x1 = when(direction) {
                2 -> x - 1
                4 -> x + 1
                else -> x
            }
            var y1 = when(direction) {
                1 -> y - 1
                3 -> y + 1
                else -> y
            }

            if (blockedDirection == 1 || blockedDirection == 3) {
                if (path.find { it.first == blockedDirection && it.second.x == x } != null) result.add(Point(x1, y1))
            } else {
                if (path.find { it.first == blockedDirection && it.second.y == y } != null) result.add(Point(x1, y1))
            }

            if (x1 < 0 || y1 < 0 || x1 > input.lastIndex || y1 > input.first().lastIndex) {
                break
            }
            if (input[x1][y1] == '#') {
                when(direction) {
                    1 -> { x1 = x - 1; y1 = y; direction = 2 }
                    2 -> { x1 = x; y1 = y + 1; direction = 3 }
                    3 -> { x1 = x + 1; y1 = y; direction = 4 }
                    4 -> { x1 = x; y1 = y - 1; direction = 1 }
                }
            }
            x = x1
            y = y1
        }

//        input.mapIndexed { i1, s1 ->
//            s1.mapIndexed { i2, s2 ->
//                if (result.find { it == Point(i1, i2) } != null) 'O' else
//                if (path.find { it.second == Point(i1, i2) } != null && s2 != '^') 'X' else s2 }
//        }.forEach {
//            println(it.toCharArray())
//        }

        return result.size
    }


    override fun mapInputData(file: File): List<String> =
        file.readLines()
}

fun main() {
    Day06().submitPart1TestInput() // 41
    Day06().submitPart1Input() // 5101
    Day06().submitPart2TestInput() // 6
    Day06().submitPart2Input() // 913 FAILED!!
}