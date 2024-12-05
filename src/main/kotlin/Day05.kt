package day01

import java.io.File

class Day05 : Base<Day05.Data, Int>(5) {

    data class Data(
        val rules: List<Pair<Int, Int>>,
        val updates: List<List<Int>>
    )

    override fun part1(input: Data): Int =
        input.updates.fold(0) { r, update ->
            if (isValid(update, input.rules)) {
                r + update[update.lastIndex / 2]
            } else r
        }

    private fun isValid(update: List<Int>, rules: List<Pair<Int, Int>>): Boolean {
        update.forEachIndexed { index, page ->
            for (i1 in 0..<index) {
                if (rules.find { it.first == page && it.second == update[i1] } != null) return false
            }
            for (i2 in index+1..update.lastIndex) {
                if (rules.find { it.second == page && it.first == update[i2] } != null) return false
            }
        }
        return true
    }

    override fun part2(input: Data): Int =
        input.updates.fold(0) { r, update1 ->
            var update = update1
            var isValid = isValid(update, input.rules)
            val notValid = !isValid
            while (!isValid) {
                update = fix(update, input.rules)
                isValid = isValid(update, input.rules)
            }
            if (notValid) {
                r + update[update.lastIndex / 2]
            } else {
                r
            }
        }

    private fun fix(update1: List<Int>, rules: List<Pair<Int, Int>>): List<Int> {
        val update = update1.toMutableList()
        for (index in update.indices) {
            val page = update[index]
            for (i1 in 0..<index) {
                val rule = rules.find { it.first == page && it.second == update[i1] }
                if (rule != null) {
                    val dd = update[i1]
                    update[i1] = page
                    update[index] = dd
                    break
                }
            }
            for (i2 in index+1..update.lastIndex) {
                val rule = rules.find { it.second == page && it.first == update[i2] }
                if (rule != null) {
                    update[i2] = rule.second
                    update[index] = rule.first
                    break
                }
            }
        }
        return update
    }

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
    Day05().submitPart2TestInput() // 123
    Day05().submitPart2Input() // 4681
}