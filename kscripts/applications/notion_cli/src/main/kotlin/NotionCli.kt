@file:OptIn(ExperimentalCli::class)

import cli.parseArgs
import kotlinx.cli.ArgType
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking


// Args

// Add a todo item from the command line
// notion todo --add

// List all todos (future: add filtering)
// notion todo --list

// Switch page for todos
// notion todo --update name?

sealed class NotionCliCommands {
    /**
     * Create
     */
    object CreateTodo: Subcommand("create-todo", "Add a new todo item to the current page.") {
        // TODO: Args

        override fun execute() {
            // TODO: Handle todo action
        }
    }

    /**
     * Read
     */

    // TODO: Better description
    object ListTodos: Subcommand("list-todos", "List all todo items from the current page.") {
        // TODO: Args

        override fun execute() {
            // TODO: Handle todo action
        }
    }
}

// TODO: Sealed class with SubCommand extensions
enum class NotionTodoAction {
    ADD,
    LIST,
    // TODO: How to update
}

// TODO
fun main(args: Array<String>) = runBlocking(Dispatchers.Default) {
    parseArgs(args, "notion") {
        subcommands(
            NotionCliCommands.AddTodo,
            // TODO
        )
    }


    // TODO: Login if not authed

    when ()
}
