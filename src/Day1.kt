import kotlin.math.abs

fun main() {
    val input by lazy {
        readInput("Day1")
    }

    data class Ls(val l1: MutableList<Int>, val l2: MutableList<Int>)

    fun parseLs(ls: List<String>): Ls {
        val l1 = mutableListOf<Int>()
        val l2 = mutableListOf<Int>()
        ls.forEach { l -> l.split("\\s+".toRegex()).let { l1.add(it[0].toInt()); l2.add(it[1].toInt()) } }
        return Ls(l1, l2)
    }

    fun diff(ls: Ls): Int {
        ls.l1.sort()
        ls.l2.sort()
        return ls.l1.mapIndexed { i, v -> abs(v - ls.l2[i]) }.sum()
    }

    fun similarity(ls: Ls): Int {
        val l2Count = ls.l2.groupingBy { it }.eachCount()
        return ls.l1.sumOf { it * l2Count.getOrDefault(it, 0) }
    }

    fun part1(): Int = diff(parseLs(input))

    fun part2(): Int = similarity(parseLs(input))

    measure { part1() }
    measure { part2() }
}
