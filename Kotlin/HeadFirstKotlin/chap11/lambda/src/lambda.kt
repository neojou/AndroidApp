fun main() {
    val list = listOf(9,5,3,7,1,2,6,4,8)
    val mod_int = {x : Int -> (x - 1) % 3}
    val mod_int1 : (Int) -> Int = {(it - 1) % 3}
    val print_test : (Int, (Int) -> Int) -> Unit
            = {x : Int, func : (Int) -> Int
                -> println("Test: mod($x) = " + func(x))}
    print_test(98, mod_int)
    print_test(99, mod_int1)
    print_test(100) {(it - 1) % 3}

    val cmp_func = Comparator<Int>{ x, y ->
        val a = mod_int(x)
        val b = mod_int(y)
        when {
            a > b -> 1
            a < b -> -1
            else ->  when {
                x > y -> 1
                x < y -> -1
                else -> 0
            }
        }
    }
    val sorted_list = list.sortedWith(cmp_func)

    println("List: " + list)
    println("sorted: " + sorted_list)
}