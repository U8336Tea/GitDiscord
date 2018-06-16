package shared

object Config {
	operator fun get(key: String): Any? {
		return System.getenv(key)
	}
}