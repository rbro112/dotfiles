package cli

import kotlinx.cli.ArgParser

/**
 * Parses the cli args into your custom kotlin class of the given type.
 *
 * This version should be used by .kt files with a main function "fun main(args: Array<String>)"
 */
inline fun <reified T> parseArgs(
    args: Array<String>,
    name: String,
    argBuilder: ArgParser.() -> T
): T {
    val argParser = ArgParser(name)
    return argBuilder.invoke(argParser)
}