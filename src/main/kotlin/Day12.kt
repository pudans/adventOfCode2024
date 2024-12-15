package day01

import day01.utils.Point
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
        calculateMap(input).forEach { (_, regions) ->
            val regionPrice = regions.fold(0L) { r, points ->
                val leftSlides = mutableSetOf<Point<Int>>()
                val rightSlide = mutableSetOf<Point<Int>>()
                val upSlides = mutableSetOf<Point<Int>>()
                val bottomSlides = mutableSetOf<Point<Int>>()
                points.forEach { point ->
                    if (!points.contains(Point(point.x, point.y - 1))) leftSlides.add(point)
                    if (!points.contains(Point(point.x, point.y + 1))) rightSlide.add(point)
                    if (!points.contains(Point(point.x - 1, point.y))) upSlides.add(point)
                    if (!points.contains(Point(point.x + 1, point.y))) bottomSlides.add(point)
                }
                val left = leftSlides.fold(0.0) { r1, point ->
                    val has1 = leftSlides.contains(Point(point.x + 1, point.y))
                    val has2 = leftSlides.contains(Point(point.x - 1, point.y))
                    r1 + 1 + (if (has1) -0.5 else 0.0) + (if (has2) -0.5 else 0.0)
                }
                val right = rightSlide.fold(0.0) { r1, point ->
                    val has1 = rightSlide.contains(Point(point.x + 1, point.y))
                    val has2 = rightSlide.contains(Point(point.x - 1, point.y))
                    r1 + 1 + (if (has1) -0.5 else 0.0) + (if (has2) -0.5 else 0.0)
                }
                val up = upSlides.fold(0.0) { r1, point ->
                    val has1 = upSlides.contains(Point(point.x, point.y + 1))
                    val has2 = upSlides.contains(Point(point.x, point.y - 1))
                    r1 + 1 + (if (has1) -0.5 else 0.0) + (if (has2) -0.5 else 0.0)
                }
                val bottom = bottomSlides.fold(0.0) { r1, point ->
                    val has1 = bottomSlides.contains(Point(point.x, point.y + 1))
                    val has2 = bottomSlides.contains(Point(point.x, point.y - 1))
                    r1 + 1 + (if (has1) -0.5 else 0.0) + (if (has2) -0.5 else 0.0)
                }
                val area = points.size
                r + (right + left + up + bottom).toLong() * area
            }
            result += regionPrice
        }
        return result
    }

    private fun calculateMap(input: List<CharArray>): Map<Char, List<Set<Point<Int>>>> {
        val map = hashMapOf<Char, MutableList<MutableSet<Point<Int>>>>()
        input.forEachIndexed { x, chars ->
            chars.forEachIndexed { y, char ->
                val point = Point(x, y)
                val regions = map[char]
                if (regions == null) {
                    map[char] = mutableListOf(mutableSetOf(point))
                } else {
                    var addedRegion: MutableSet<Point<Int>>? = null
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
    Day12().submitPart2TestInput() // 1206
    Day12().submitPart2Input() // 808796
}