package br.com.verdinhas.gafanhoto.telegram.command;

import br.com.verdinhas.gafanhoto.telegram.GafanhotoBot;
import br.com.verdinhas.gafanhoto.telegram.ReceivedMessage;

public interface BotCommand {

	String command();

	void doIt(GafanhotoBot bot, ReceivedMessage message);

}
