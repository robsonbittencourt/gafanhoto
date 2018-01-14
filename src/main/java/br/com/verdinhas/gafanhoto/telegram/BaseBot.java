package br.com.verdinhas.gafanhoto.telegram;

import org.telegram.abilitybots.api.bot.AbilityBot;

public abstract class BaseBot extends AbilityBot {

	public static String BOT_USERNAME = "GafanhotoMobBot";

	protected BaseBot() {
		super(System.getenv("GAFANHOTO_TOKEN"), BOT_USERNAME);
	}

	@Override
	public int creatorId() {
		return Integer.valueOf(System.getenv("TELEGRAM_USER_ID"));
	}

}
