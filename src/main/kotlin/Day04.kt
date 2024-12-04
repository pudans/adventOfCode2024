package day01

import java.io.File

class Day04 : Base<List<CharArray>, Int>(4) {

    override fun part1(input: List<CharArray>): Int {
        var result = 0
        input.forEachIndexed { x, chars ->
            chars.forEachIndexed { y, char ->
                if (char == 'X') {
                    result += findXMAS(input, x, y)
                }
            }
        }
        return result
    }

    private fun findXMAS(input: List<CharArray>, x: Int, y: Int): Int {
        var result = 0

        kotlin.runCatching {
            if (input[x][y + 1] == 'M' && input[x][y + 2] == 'A' && input[x][y + 3] == 'S') {
                result++
            }
        }

        kotlin.runCatching {
            if (input[x][y - 1] == 'M' && input[x][y - 2] == 'A' && input[x][y - 3] == 'S') {
                result++
            }
        }

        kotlin.runCatching {
            if (input[x + 1][y] == 'M' && input[x + 2][y] == 'A' && input[x + 3][y] == 'S') {
                result++
            }
        }

        kotlin.runCatching {
            if (input[x - 1][y] == 'M' && input[x - 2][y] == 'A' && input[x - 3][y] == 'S') {
                result++
            }
        }

        kotlin.runCatching {
            if (input[x + 1][y + 1] == 'M' && input[x + 2][y + 2] == 'A' && input[x + 3][y + 3] == 'S') {
                result++
            }
        }

        kotlin.runCatching {
            if (input[x - 1][y - 1] == 'M' && input[x - 2][y - 2] == 'A' && input[x - 3][y - 3] == 'S') {
                result++
            }
        }

        kotlin.runCatching {
            if (input[x + 1][y - 1] == 'M' && input[x + 2][y - 2] == 'A' && input[x + 3][y - 3] == 'S') {
                result++
            }
        }

        kotlin.runCatching {
            if (input[x - 1][y + 1] == 'M' && input[x - 2][y + 2] == 'A' && input[x - 3][y + 3] == 'S') {
                result++
            }
        }

        return result
    }

    override fun part2(input: List<CharArray>): Int {
        var result = 0
        input.forEachIndexed { x, chars ->
            chars.forEachIndexed { y, char ->
                if (char == 'A') {
                    result += if (findCrossXMAS(input, x, y)) 1 else 0
                }
            }
        }
        return result
    }

    private fun findCrossXMAS(input: List<CharArray>, x: Int, y: Int): Boolean =
        kotlin.runCatching {
            val s1 = input[x - 1][y - 1]
            val s2 = input[x - 1][y + 1]
            val s3 = input[x + 1][y + 1]
            val s4 = input[x + 1][y - 1]
            (s1 == 'M' && s2 == 'M' && s3 == 'S' && s4 == 'S') ||
            (s1 == 'M' && s2 == 'S' && s3 == 'S' && s4 == 'M') ||
            (s1 == 'S' && s2 == 'S' && s3 == 'M' && s4 == 'M') ||
            (s1 == 'S' && s2 == 'M' && s3 == 'M' && s4 == 'S')
        }.getOrNull() ?: false

    override fun mapInputData(file: File): List<CharArray> =
        file.readLines().map { it.toCharArray() }
}

fun main() {
    Day04().submitPart1TestInput() // 18
    Day04().submitPart1Input() // 2434
    Day04().submitPart2TestInput() // 9
    Day04().submitPart2Input() // 1835

}