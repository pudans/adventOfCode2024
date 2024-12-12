package day01

import java.awt.Point
import java.io.File

class Day12 : Base<List<CharArray>, Long>(12) {

    override fun part1(input: List<CharArray>): Long {
        var result = 0L
        calculateMap(input).forEach { (char, regions) ->
            val regionPrice = regions.fold(0L) { r, points ->
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

    override fun part2(input: List<CharArray>): Long {
        var result = 0L
        calculateMap(input).forEach { (char, regions) ->
            val regionPrice = regions.fold(0L) { r, points ->
                val outerPointsList = buildList {
                    points.forEach { point ->
                        if (!points.contains(Point(point.x + 1, point.y))) add(Point(point.x + 1, point.y))
                        if (!points.contains(Point(point.x, point.y + 1))) add(Point(point.x, point.y + 1))
                        if (!points.contains(Point(point.x - 1, point.y))) add(Point(point.x - 1, point.y))
                        if (!points.contains(Point(point.x, point.y - 1))) add(Point(point.x, point.y - 1))
                    }
                }
                val outerPointsSet = outerPointsList.toSet()
                val slides = outerPointsList.size - outerPointsSet.fold(0.0) { r1 , point ->
                    val has1 = outerPointsSet.contains(Point(point.x + 1, point.y))
                    val has2 = outerPointsSet.contains(Point(point.x - 1, point.y))
                    val has3 = outerPointsSet.contains(Point(point.x, point.y + 1))
                    val has4 = outerPointsSet.contains(Point(point.x, point.y - 1))
                    r1 + (if (has1) 0.5 else 0.0) + (if (has2) 0.5 else 0.0) + (if (has3) 0.5 else 0.0) + (if (has4) 0.5 else 0.0)
                }
                println("$char ${outerPointsList.size} ${outerPointsSet.size} $slides")
                val area = points.size
                r + slides.toLong() * area
            }
            result += regionPrice
        }
        return result
    }

    private fun calculateMap(input: List<CharArray>): Map<Char, List<Set<Point>>> {
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
        return map
    }

    override fun mapInputData(file: File): List<CharArray> =
        file.readLines().map { it.toCharArray() }
}

fun main() {
    Day12().submitPart1TestInput() // 1930
    Day12().submitPart1Input() // 1352976
    Day12().submitPart2TestInput() //
//    Day12().submitPart2Input() //
}