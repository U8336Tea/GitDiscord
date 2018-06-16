package git

import java.util.regex.Pattern
import git.exceptions.ParsingException

/**
 * Parses the output of git diff in order to edit a file.
 *
 * This is a class in case we decide later to add support for different formats.
 */
class DiffParser {
	/**
	 * Parses the diff.
	 *
	 * THIS CURRENTLY DOES NOT WORK
	 *
	 * @param diff The diff to parse
	 * @return The filepath and a Map in the form of {lineNum: text}. Should this be a class?
	 */
	// TODO: Fix this
	@Throws(ParsingException::class)
	fun parse(diff: String): Pair<String, Map<Int, String>> {
		// We don't need subtractions or the name of the file on two lines.
		val lines = diff.lines().filterNot { it.startsWith("-") }
		// TODO: This may cause errors later on.
		var filepath = ""
		val changes = hashMapOf<Int, String>()
		var currentLine: Int? = null

		for (line in lines) {
			when {
				line.startsWith("+++") ->
					filepath = getFilepath(line) ?: throw ParsingException("An error occurred.")
				line.startsWith("@@") ->
					currentLine = getLineNumber(line)
							?: throw ParsingException("An error occurred.")
				line.startsWith("+") ->
					changes[currentLine ?: throw ParsingException("An error occurred.")] =
							line.drop(1)
			}

			if (currentLine != null) currentLine++
		}

		return Pair(filepath, changes)
	}

	/**
	 * Gets the filename from a line in the form of +++ b/path
	 *
	 * @param line The line to parse
	 * @return The line number
	 */
	private fun getFilepath(line: String): String? {
		val pattern = Pattern.compile("\\+{3} b/(.+)")
		val matcher = pattern.matcher(line)

		if (!matcher.find()) return null

		return matcher.group(1)
	}

	/**
	 * Gets the line number from a line in the form of @@ -x,x +x,x @@
	 *
	 * @param line The line to parse
	 * @return The line number
	 */
	private fun getLineNumber(line: String): Int? {
		val pattern = Pattern.compile("@@ -\\d+,\\d+ \\+(\\d+),\\d+ @@")
		val matcher = pattern.matcher(line)

		if (!matcher.find()) return null

		return matcher.group(1).toInt()
	}
}