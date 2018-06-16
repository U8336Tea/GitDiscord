package git

import java.util.regex.Pattern

/**
 * Parses the output of git diff in order to edit a file.
 *
 * This is a class in case we decide later to add support for different formats.
 */
class DiffParser {
	/**
	 * Parses the diff.
	 *
	 * @param diff The diff to parse
	 * @return The filepath and a Map in the form of {lineNum: text}. Should this be a class?
	 */
	fun parse(diff: String): Pair<String, Map<Int, String>> {
		// We don't need subtractions or the name of the file on two lines.
		val lines = diff.lines().filterNot { it.startsWith("-") }
		// TODO: This may cause errors later on.
		var filepath = ""
		val changes = hashMapOf<Int, String>()
		var currentLine: Int? = null

		for (line in lines) {
			when {
				line.startsWith("+++") -> // TODO: Get a better exception.
					filepath = getFilepath(line) ?: throw Exception("An error occurred.")
				line.startsWith("@@") -> // TODO: Get a better exception.
					currentLine = getLineNumber(line) ?: throw Exception("An error occurred.")
				line.startsWith("+") -> // TODO: Get a better exception.
					changes[currentLine ?: throw Exception("An error occurred.")] = line.drop(1)
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