package shared

/**
 * Configuration settings for the program
 */
object Config {
	object GitHub {
		val username get() = Config["github_username"] as String
		val password get() = Config["github_password"] as String
	}

	object Discord {
		val key get() = Config["discord_token"] as String
	}

	/**
	 * Get a setting
	 *
	 * @param key The key for the setting
	 * @return The value for the key
	 */
	operator fun get(key: String): Any? = System.getenv(key)
}