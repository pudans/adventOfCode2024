package day01

import java.io.File

class Day05 : Base<Day05.Data, Int>(5) {

    data class Data(
        val rules: List<Pair<Int, Int>>,
        val updates: List<List<Int>>
    )

    override fun part1(input: Data): Int =
        input.updates.fold(0) { r, update ->
            update.forEachIndexed { index, page ->
                for (i1 in 0..<index) {
                    if (input.rules.find { it.first == page && it.second == update[i1] } != null) return@fold r
                }
                for (i2 in index+1..update.lastIndex) {
                    if (input.rules.find { it.second == page && it.first == update[i2] } != null) return@fold r
                }
            }
            r + update[update.lastIndex / 2]
        }

    override fun part2(input: Data): Int = 0

    override fun mapInputData(file: File): Data {
        var isRules = true
        val rules = mutableListOf<Pair<Int, Int>>()
        val updates = mutableListOf<List<Int>>()
        file.readLines().forEach { line ->
            if (line.isEmpty()) {
                isRules = false
            } else if (isRules) {
                val rule = line.split("|").map { it.toInt() }
                rules.add(Pair(rule[0], rule[1]))
            } else {
                updates.add(line.split(",").map { it.toInt() })
            }
        }
        return Data(rules, updates)
    }
}

fun main() {
    Day05().submitPart1TestInput() // 143
    Day05().submitPart1Input() // 5091
//    Day05().submitPart2TestInput() // 9
//    Day05().submitPart2Input() // 1835
}