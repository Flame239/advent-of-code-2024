fun main() {
    val input by lazy {
        readInput("Day3")[0]
    }

    fun part1(): Long {
        val mulRegex = Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)")
        return mulRegex.findAll(input).sumOf {
            val (a, b) = it.destructured
            a.toLong() * b.toLong()
        }
    }

    fun part2(): Long {
        val mulRegex = Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)|do\\(\\)|don't\\(\\)")
        var enabled = true
        var res = 0L
        mulRegex.findAll(input).forEach {
            if (it.value == "do()") {
                enabled = true
            } else if (it.value == "don't()") {
                enabled = false
            } else if (enabled) {
                val (a, b) = it.destructured
                res += a.toLong() * b.toLong()
            }
        }
        return res
    }

    measure { part1() }
    measure { part2() }
}
