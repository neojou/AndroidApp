fun main() {
    val options = arrayOf("Rock", "Paper", "Scissors")
    var game_choice = ""
    var user_choice = "";
    var result = 0;
    var total_result = 0

    var loops = 3
    while (loops > 0) {
        game_choice = get_game_choice1(options, game_choice, user_choice, result)
        user_choice = get_user_choice(options)
        result = judge(game_choice, user_choice)
        total_result += result;
        loops--;
    }

    if (total_result > 0)
        println("I Win!")
    else if (total_result < 0)
        println("You win")
    else
        println("Tie!")

}

fun get_game_choice(options: Array<String>) =
    options[(Math.random() * options.size).toInt()];

fun get_game_choice1(options : Array<String>,
                     last_game_choice : String,
                     last_user_choice : String,
                     last_result : Int) : String {
    var next_choice = ""

    if (last_result == 0) {
        next_choice = options[(Math.random() * options.size).toInt()]
    } else if (last_result == 1) {
        next_choice = when (last_game_choice) {
            "Scissors" -> "Paper"
            "Rock" -> "Scissors"
            "Paper" -> "Rock"
            else -> ""
        }
    } else if (last_result == -1) {
        next_choice = when (last_user_choice) {
            "Scissors" -> "Rock"
            "Rock" -> "paper"
            "Paper" -> "Scissors"
            else -> ""
        }
    }
    return next_choice;
}

fun get_user_choice(options: Array<String>): String {
    var is_valid = false
    var user_choice = ""
    while (!is_valid) {
        print("Please enter one of the following:")
        for (item in options) print(" $item")
        println(".")

        val input = readLine()
        if (input != null && input in options) {
            is_valid = true;
            user_choice = input;
        }
        if (!is_valid) println("You must enter a valid choice")
    }
    return user_choice
}

fun judge(game_choice: String, user_choice: String) : Int {
    val result: Int

    if (user_choice == game_choice) result = 0
    else if ((user_choice == "Rock" && game_choice == "Scissors") ||
        (user_choice == "Paper" && game_choice == "Rock") ||
        (user_choice == "Scissors" && game_choice == "Paper"))
            result = -1
    else
        result = 1

    println("You choose $user_choice, I choose $game_choice")
    return result
}
