data class Stock(val id: String, val unit_price: Double, val quantity: Int)

fun main() {
    val stocks = listOf(Stock("2330", 578.0, 2),
                        Stock("2603", 152.0, 2),
                        Stock("2882", 59.4, 10))

    val high_stock_amounts = stocks.filter { it.unit_price > 100 }
                                   .map { it.unit_price * it.quantity }

    println("$high_stock_amounts")
}
