package discord.commands

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent

class QuitBot: Command() {
	init {
		name = "quit"
		aliases = arrayOf("quitbot")
		help = "Quits the bot."
	}

	override fun execute(event: CommandEvent) {
		System.exit(0)
	}
}