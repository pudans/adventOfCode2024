package day01

import java.awt.Point
import java.io.File

class Day12 : Base<List<CharArray>, Long>(12) {

    override fun part1(input: List<CharArray>): Long {
        val map = hashMapOf<Char, MutableList<MutableSet<Point>>>()

        input.forEachIndexed { x, chars ->
            chars.forEachIndexed { y, char ->
                val point = Point(x, y)
                val regions = map[char]
                if (regions == null) {
                    map[char] = mutableListOf(mutableSetOf(point))
                } else {
                    var addedRegion: MutableSet<Point>? = null
                    if (x > 0) {
                        val prevChar = input[x - 1][y]
                        if (prevChar == char) {
                            addedRegion = regions.find { it.contains(Point(x - 1, y)) }
                            addedRegion!!.add(point)
                        }
                    }
                    if (y > 0) {
                        val prevChar = input[x][y - 1]
                        if (prevChar == char) {
                            val regionIndex = regions.indexOfFirst { it.contains(Point(x, y - 1)) }
                            val region = regions[regionIndex]
                            if (addedRegion != null && region != addedRegion) {
                                regions.removeAt(regionIndex)
                                addedRegion.addAll(region.toList())
                            } else {
                                region.add(point)
                                addedRegion = region
                            }
                        }
                    }
                    if (addedRegion == null) {
                        regions.add(mutableSetOf(point))
                    }
                }
            }
        }

        map.forEach {
            it.value.forEach { it2 ->
                println("${it.key} : ${it2}")
            }
        }

        var result = 0L
        map.forEach {
            val char = it.key
            val region = it.value
            val regionPrice = region.fold(0L) { r, points ->
                val perimeter = points.fold(0L) { r1, point ->
                    val left = point.x == 0 || input[point.x - 1][point.y] != char
                    val right = point.x == input.lastIndex || input[point.x + 1][point.y] != char
                    val top = point.y == 0 || input[point.x][point.y - 1] != char
                    val bottom = point.y == input.first().lastIndex || input[point.x][point.y + 1] != char
                    r1 + (if (left) 1 else 0) + (if (right) 1 else 0) + (if (top) 1 else 0) + (if (bottom) 1 else 0)
                }
                val area = points.size
                r + perimeter * area
            }
            result += regionPrice
        }
        return result
    }

    override fun part2(input: List<CharArray>): Long = 0L

    override fun mapInputData(file: File): List<CharArray> =
        file.readLines().map { it.toCharArray() }
}

fun main() {
    Day12().submitPart1TestInput() // 1930
    Day12().submitPart1Input() // 1352976
//    Day12().submitPart2TestInput() //
//    Day12().submitPart2Input() //
}