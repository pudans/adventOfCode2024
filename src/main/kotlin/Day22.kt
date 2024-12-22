package day01

import java.io.File

class Day22 : Base<List<Long>, Long>(22) {

    override fun part1(input: List<Long>): Long = input.fold(0L) { r, secret ->
        var result = secret
        repeat(2000) {
            val d = result * 64L
            result = result.mix(d)
            result = result.prune()

            val d1 = result / 32
            result = result.mix(d1)
            result = result.prune()

            val d3 = result * 2048L
            result = result.mix(d3)
            result = result.prune()
        }
        r + result
    }

    private fun Long.mix(secret: Long): Long = this.xor(secret)

    private fun Long.prune(): Long = this.mod(16777216L)

    override fun part2(input: List<Long>): Long {

        input.forEach { secret ->

            var result = secret
            val prices = mutableListOf<Int>()
            val diff = mutableListOf<Int>()
            prices.add(result.mod(10))

            repeat(10) { index ->
                val d = result * 64L
                result = result.mix(d)
                result = result.prune()

                val d1 = result / 32
                result = result.mix(d1)
                result = result.prune()

                val d3 = result * 2048L
                result = result.mix(d3)
                result = result.prune()

                val price = result.mod(10)
                prices.add(price)
                diff.add(price - prices[index])
            }


            println(prices)
            println(diff)
            println(diff.windowed(4))
            println("-------")
        }

        return 0L
    }

    override fun mapInputData(file: File): List<Long> = file.readLines().map { it.toLong() }
}

fun main() {
//    Day22().submitPart1TestInput() // 37327623
//    Day22().submitPart1Input() // 20071921341
    Day22().submitPart2TestInput() //
//    Day22().submitPart2Input() //
}