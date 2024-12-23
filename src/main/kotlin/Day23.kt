package day01

import java.io.File

class Day23 : Base<List<Pair<String, String>>, Int>(23) {

    override fun part1(input: List<Pair<String, String>>): Int {
        val map = mutableMapOf<String, MutableList<String>>()
        input.forEach {
            map[it.first] = map.getOrDefault(it.first, mutableListOf()).apply { add(it.second) }
            map[it.second] = map.getOrDefault(it.second, mutableListOf()).apply { add(it.first) }
        }
        val connected = mutableSetOf<Set<String>>()
        map.forEach { (t1, values) ->
            values.forEach { t2 ->
                map[t2]!!.forEach { t3 ->
                    if (values.contains(t3)) {
                        connected.add(setOf(t1, t2, t3))
                    }
                }
            }
        }
        val filtered = connected.filter { it.any { it.startsWith("t") } }
        return filtered.size
    }

    override fun part2(input: List<Pair<String, String>>): Int = 0

    override fun mapInputData(file: File): List<Pair<String, String>> =
        file.readLines().map { it.split("-") }.map { Pair(it[0], it[1]) }
}

fun main() {
    Day23().submitPart1TestInput() // 7
    Day23().submitPart1Input() // 1000
//    Day23().submitPart2TestInput() //
//    Day22().submitPart2Input() //
}