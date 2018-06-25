package discord.commands

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent

class Ping : Command() {
	init {
		this.name = "ping"
		this.help = "Responds \"Pong!\"."
	}

	override fun execute(event: CommandEvent) {
		event.reply("Pong!")
	}
}