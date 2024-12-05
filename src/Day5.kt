fun main() {
    data class Input(val goesBefore: MutableMap<Int, MutableSet<Int>>, val pages: List<List<Int>>)

    val input by lazy {
        val lines = readInput("Day5")
        val rules =
            lines.takeWhile { it.isNotBlank() }.map { it.split("|").map { it.toInt() }.let { Pair(it[0], it[1]) } }
        val goesBefore = mutableMapOf<Int, MutableSet<Int>>()
        rules.forEach { (a, b) -> goesBefore.computeIfAbsent(b) { mutableSetOf() }.add(a) }

        val pages = lines.dropWhile { it.isNotBlank() }.drop(1).map { it.split(",").map { it.toInt() } }
        Input(goesBefore, pages)
    }

    fun isValid(page: List<Int>, goesBefore: MutableMap<Int, MutableSet<Int>>): Boolean {
        for (i in page.indices) {
            for (j in i + 1 until page.size) {
                if (goesBefore.getOrDefault(page[i], emptyList()).contains(page[j])) {
                    return false
                }
            }
        }
        return true
    }

    fun fix(goesBefore: MutableMap<Int, MutableSet<Int>>, page: List<Int>): MutableList<Int> {
        val res = mutableListOf<Int>()
        val nums = page.toHashSet()
        repeat(page.size) {
            val n = nums.find { num ->
                val b = goesBefore.getOrDefault(num, emptyList())
                b.none { it in nums }
            } ?: error("No number found")
            res.add(n)
            nums.remove(n)
        }
        return res
    }

    fun part1(): Int = input.pages
        .filter { page -> isValid(page, input.goesBefore) }
        .sumOf { it[it.size / 2] }

    fun part2(): Int = input.pages
        .filter { page -> !isValid(page, input.goesBefore) }
        .map { fix(input.goesBefore, it) }
        .sumOf { it[it.size / 2] }

    measure { part1() }
    measure { part2() }
}
