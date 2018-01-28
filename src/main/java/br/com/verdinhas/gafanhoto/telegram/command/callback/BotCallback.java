package br.com.verdinhas.gafanhoto.telegram.command.callback;

import br.com.verdinhas.gafanhoto.telegram.GafanhotoBot;
import br.com.verdinhas.gafanhoto.telegram.ReceivedMessage;

public interface BotCallback {

	String prefixIdentifier();

	void callback(GafanhotoBot bot, ReceivedMessage message);

}
