package day01

import java.io.File

class Day22 : Base<List<Long>, Long>(22) {

    override fun part1(input: List<Long>): Long = input.fold(0L) { r, secret ->
        var result = secret
        repeat(2000) { result = result.calculatePrice() }
        r + result
    }

    private fun Long.calculatePrice(): Long {
        var result = this
        val d = result * 64L
        result = result.mix(d)
        result = result.prune()

        val d1 = result / 32
        result = result.mix(d1)
        result = result.prune()

        val d3 = result * 2048L
        result = result.mix(d3)
        result = result.prune()
        return result
    }

    private fun Long.mix(secret: Long): Long = this.xor(secret)

    private fun Long.prune(): Long = this.mod(16777216L)

    override fun part2(input: List<Long>): Long {
        val allDiffs = mutableListOf<List<List<Int>>>()
        val allPrices = mutableListOf<List<Int>>()
        val mapDiffs = mutableListOf<Map<List<Int>, Int>>()

        input.forEach { secret ->
            var result = secret
            val prices = mutableListOf<Int>()
            val diff = mutableListOf<Int>()
            val map = mutableMapOf<List<Int>, Int>()
            prices.add(result.mod(10))

            repeat(2000) { index ->
                result = result.calculatePrice()
                val price = result.mod(10)
                prices.add(price)
                diff.add(price - prices[index])
            }
            val windowed = diff.windowed(4)
            windowed.forEachIndexed { index, ints -> map[ints] = prices[index + 4] }
            mapDiffs.add(map)
            allDiffs.add(windowed)
            allPrices.add(prices)
        }

        val setOfDiffs = buildSet { allDiffs.forEach { diff -> diff.forEach { add(it) } } }

        val max = setOfDiffs.maxOf { targetDiff ->
            mapDiffs.fold(0) { r, map ->
                val foundedPrice = map[targetDiff]
                r + (foundedPrice ?: 0)
            }

//            allDiffs.foldIndexed(0) { index, r, diffs ->
//                val foundDiffIndex = diffs.indexOfFirst { it == targetDiff }
//                val foundedPrice = if (foundDiffIndex >= 0) {
//                    val prices = allPrices[index]
//                    prices[foundDiffIndex + 4]
//                } else {
//                    0
//                }
//                r + foundedPrice
//            }
        }

        return max.toLong()
    }

    override fun mapInputData(file: File): List<Long> = file.readLines().map { it.toLong() }
}

fun main() {
//    Day22().submitPart1TestInput() // 37327623
//    Day22().submitPart1Input() // 20071921341
//    Day22().submitPart2TestInput() // 23
    Day22().submitPart2Input() // 2246 failed
}