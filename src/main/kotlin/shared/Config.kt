package shared

/**
 * Configuration settings for the program
 */
object Config {
	object GitHub {
		val username get() = Config["github_username"]
		val password get() = Config["github_password"]
	}

	object Discord {
		val key get() = Config["discord_token"]
	}

	/**
	 * Get a setting
	 *
	 * @param key The key for the setting
	 * @return The value for the key
	 */
	operator fun get(key: String): Any? = System.getenv(key)
}