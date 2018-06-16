package shared

object Config {
	fun get(key: String): Any {
		return System.getenv(key)
	}
}