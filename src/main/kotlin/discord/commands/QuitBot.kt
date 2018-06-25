package discord.commands

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent

class QuitBot : Command() {
	init {
		this.name = "quit"
		this.aliases = arrayOf("quitbot")
		this.help = "Quits the bot."
	}

	override fun execute(event: CommandEvent) {
		event.reply("Quitting!")
		System.exit(0)
	}
}