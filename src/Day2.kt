import kotlin.math.abs

fun main() {
    val input by lazy {
        readInput("Day2").map { it.split(" ").map { it.toInt() } }
    }

    fun isSafe(report: List<Int>): Boolean {
        if (report.isEmpty() || report.size == 1) return true
        if (report[1] == report[0]) return false
        val inc = report[1] > report[0]

        report.windowed(2).forEach {
            val diff = if (inc) it[1] - it[0] else it[0] - it[1]
            if (diff !in 1..3) return false
        }
        return true
    }

    fun isSafeWithMistake(report: List<Int>): Boolean {
        // if mistake is diff too big = false, because we can't fix it by deleting and element
        // can only fix if 2 numbers are equals or in inverted order
        if (report.size <= 2) return true
        val diffs = report.windowed(2).map { it[1] - it[0] }
        val incs = diffs.count { it > 0 }
        val decs = diffs.count { it < 0 }
        val eq = diffs.count { it == 0 }
        val tooMuch = diffs.count { abs(it) > 3 }

        if (eq > 1) return false
        // can fix only 1 inversion
        if (incs >= 2 && decs >= 2) return false

        // just delete duplicated element
        if ((incs == 0 || decs == 0) && eq == 1) {
            return isSafe(report.toMutableList().apply { removeAt(diffs.indexOf(0)) })
        }

        if (incs == 0 || decs == 0) {
            // same order - check for diffs
            if (tooMuch > 1) return false
            if (tooMuch == 0) {
                return true
            }
            return isSafe(report.toMutableList().apply { removeAt(diffs.indexOfFirst { abs(it) > 3 }) }) || isSafe(report.toMutableList().apply { removeAt(diffs.indexOfFirst { abs(it) > 3 } + 1) })
        }
        // have 1 inversion

        // can't fix both inversion and eq
        if (eq == 1) return false

        val isInc = (decs == 1)

        val invIndex = if (isInc) diffs.indexOfFirst { it < 0 } else diffs.indexOfFirst { it > 0 }
        val rem1 = isSafe(report.toMutableList().apply { removeAt(invIndex) })
        val rem2 = isSafe(report.toMutableList().apply { removeAt(invIndex + 1) })

        return rem1 || rem2
    }

    fun part1(): Int = input.count(::isSafe)

    fun part2(): Int = input.count(::isSafeWithMistake)

    measure { part1() }
    measure { part2() }
}
