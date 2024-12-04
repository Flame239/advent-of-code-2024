fun main() {
    val input: List<CharArray> by lazy {
        readInput("Day4").map { it.toCharArray() }
    }
    val n = input.size
    val m = input[0].size

    data class D(val x: Int, val y: Int)

    val XMAS = "XMAS"
    val MAS = "MAS"
    val DIRS = listOf(D(0, 1), D(1, 0), D(0, -1), D(-1, 0), D(1, 1), D(-1, -1), D(1, -1), D(-1, 1))

    fun part1(): Int {
        var count = 0

        for (i in input.indices) {
            for (j in input[0].indices) {
                DIRS.forEach d@{ d ->
                    var curI = i
                    var curJ = j
                    XMAS.forEach { c ->
                        if (curI !in 0 until n || curJ !in 0 until m) {
                            return@d
                        }
                        if (c != input[curI][curJ]) {
                            return@d
                        }
                        curI += d.x
                        curJ += d.y
                    }
                    count++
                }
            }
        }
        return count
    }

    fun part2(): Int {
        var count = 0

        for (i in 0 until n - 2) {
            for (j in 0 until m - 2) {
                val diag1 = "${input[i][j]}${input[i + 1][j + 1]}${input[i + 2][j + 2]}"
                val diag2 = "${input[i][j + 2]}${input[i + 1][j + 1]}${input[i + 2][j]}"

                if ((diag1 == MAS || diag1.reversed() == MAS) && (diag2 == MAS || diag2.reversed() == MAS)) {
                    count++
                }
            }
        }

        return count
    }

    measure { part1() }
    measure { part2() }
}
