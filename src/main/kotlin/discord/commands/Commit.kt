package discord.commands

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent

import git.Commit

class Commit : Command() {
	init {
		name = "commit"
		help = "Commits the changes"
		arguments = "<event>"
	}

	override fun execute(event: CommandEvent) {
		val args = event.args.split(" ")
		val message = args.getOrNull(0)

		if (message == null) {
			event.replyError("You must add a commit message!")
			return
		}

		Commit.current.commit(message)
	}
}