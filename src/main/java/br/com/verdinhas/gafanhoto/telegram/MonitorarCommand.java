package br.com.verdinhas.gafanhoto.telegram;

import static br.com.verdinhas.gafanhoto.util.Utils.addSeparators;
import static java.lang.System.lineSeparator;
import static org.telegram.abilitybots.api.objects.Locality.USER;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.MessageContext;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import br.com.verdinhas.gafanhoto.monitor.CriadorDeMonitor;
import br.com.verdinhas.gafanhoto.monitor.Monitor;
import br.com.verdinhas.gafanhoto.monitor.MonitorRepository;

@Component
public class MonitorarCommand extends BaseBot {

	@Autowired
	private CriadorDeMonitor criadorDeMonitor;

	@Autowired
	private MonitorRepository monitorRepository;

	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasCallbackQuery()) {
			callbackApagarMonitor(update);
		}

		super.onUpdateReceived(update);
	}

	private void callbackApagarMonitor(Update update) {
		String callData = update.getCallbackQuery().getData();
		long chatId = update.getCallbackQuery().getMessage().getChatId();

		if (callData.startsWith("apagar")) {
			String monitorId = callData.substring(callData.indexOf("-") + 1, callData.length());
			monitorRepository.delete(monitorId);
			silent.send("Monitor apagado. Você não vai mais receber avisos destas palavras-chave.", chatId);
		}
	}

	public Ability monitorar() {
		return Ability.builder().name("monitorar").info("Adiciona um monitor para as palavras-chave escolhidas")
				.locality(USER).privacy(PUBLIC).action(ctx -> {
					if (isValidArgumentsQuantity(ctx)) {
						silent.send("Quantidade de palavras-chave inválida. Insira de 1 a 5 palavras-chave.",
								ctx.chatId());
					} else {
						criadorDeMonitor.criar(ctx.user().id(), ctx.chatId(),
								new ArrayList<>(Arrays.asList(ctx.arguments())));
						silent.send(buildMonitorarFeedbackMessage(ctx), ctx.chatId());
					}
				}).build();
	}

	public void sendMessage(String message, long chatId) {
		silent.send(message, chatId);
	}

	public Ability apagarMonitor() {
		return Ability.builder().name("apagarMonitor").info("Apaga um monitor para não ser mais alertado")
				.locality(USER).privacy(PUBLIC).action(ctx -> {
					List<Monitor> userMonitors = monitorRepository.findByUserId(ctx.user().id());

					if (CollectionUtils.isEmpty(userMonitors)) {
						silent.send("Você aind não possui monitores", ctx.chatId());
						return;
					}

					SendMessage message = new SendMessage().setChatId(ctx.chatId())
							.setText("Qual monitor você quer apagar?");

					InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
					List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
					List<InlineKeyboardButton> rowInline = new ArrayList<>();

					for (Monitor monitor : userMonitors) {
						rowInline.add(new InlineKeyboardButton().setText(monitor.getKeyWordsWithSeparator())
								.setCallbackData("apagar-" + monitor.id));
					}

					rowsInline.add(rowInline);
					markupInline.setKeyboard(rowsInline);
					message.setReplyMarkup(markupInline);

					silent.execute(message);
				}).build();
	}

	private boolean isValidArgumentsQuantity(MessageContext ctx) {
		return ctx.arguments().length == 0 || ctx.arguments().length > 5;
	}

	private String buildMonitorarFeedbackMessage(MessageContext ctx) {
		StringBuilder sb = new StringBuilder();

		sb.append("Monitor criado com sucesso. ");
		sb.append("Vou te avisar quando aparecer uma oferta que atenda as palavras-chave:").append(lineSeparator());
		sb.append(addSeparators(Arrays.asList(ctx.arguments())));

		return sb.toString();
	}

}
