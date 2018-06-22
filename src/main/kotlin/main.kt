import net.dv8tion.jda.core.AccountType
import net.dv8tion.jda.core.JDABuilder
import net.dv8tion.jda.core.OnlineStatus
import com.jagrosh.jdautilities.command.CommandClientBuilder

import shared.Config

fun main(args: Array<String>) {
	val discordClient = CommandClientBuilder()
			.setPrefix("!")
			.setOwnerId(Config.Discord.ownerID)
			.build()

	val bot = JDABuilder(AccountType.BOT)
			.setStatus(OnlineStatus.ONLINE)
			.setAutoReconnect(true)
			.addEventListener(discordClient)
			.setToken(Config.Discord.token)
			.buildBlocking()
}