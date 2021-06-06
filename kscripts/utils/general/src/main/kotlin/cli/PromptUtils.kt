package cli

/**
 * Request a string input from the user.
 */
fun requestString(msg: String): String {
    print("$msg: ")
    return readLine() ?: ""
}

fun requestPassword(msg: String): String {
    print("$msg: ")
    return System.console()?.let { String(it.readPassword()) } ?: readLine() ?: ""
}

/**
 * Prints the message to the console and reads a yes or no response back.
 *
 * If an invalid response is provided (neither yes or no) then null will be returned.
 */
fun ask(msg: String): Answer? {
    print("$msg (y/n): ")
    val input = readLine()?.trim()

    val answer = if (input.equals("y", ignoreCase = true) || input.equals("yes", ignoreCase = true)) {
        Answer.Yes
    } else if (input.equals("n", ignoreCase = true) || input.equals("no", ignoreCase = true)) {
        Answer.No
    } else {
        null
    }

    print("\n")
    return answer
}

enum class Answer(private val validAnswers: List<String>) {
    Yes(listOf("Y", "Yes")),
    No(listOf("N", "No"));

    fun fromAnswer(stringAnswer: String): Answer? {
        return values().firstOrNull {
            it.validAnswers.contains(stringAnswer)
        }
    }
}