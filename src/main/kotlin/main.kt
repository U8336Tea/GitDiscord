import net.dv8tion.jda.core.AccountType
import net.dv8tion.jda.core.JDABuilder
import net.dv8tion.jda.core.OnlineStatus
import com.jagrosh.jdautilities.command.CommandClientBuilder

import discord.commands.*

import shared.Config

fun main(args: Array<String>) {
	val discordClient = CommandClientBuilder()
			.setPrefix("!")
			.setAlternativePrefix(".")
			.setOwnerId(Config.Discord.ownerID)
			.addCommand(QuitBot())
			.addCommand(Ping())
			.addCommand(Commit())
			.build()

	val bot = JDABuilder(AccountType.BOT)
			.setStatus(OnlineStatus.ONLINE)
			.setAutoReconnect(true)
			.addEventListener(discordClient)
			.setToken(Config.Discord.token)
			.buildBlocking()
}