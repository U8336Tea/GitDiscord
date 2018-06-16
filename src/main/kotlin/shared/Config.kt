package shared

/**
 * Configuration settings for the program
 */
object Config {
	/**
	 * Get a setting
	 *
	 * @param key The key for the setting
	 * @return The value for the key
	 */
	operator fun get(key: String): Any? {
		return System.getenv(key)
	}
}