package br.com.verdinhas.gafanhoto.telegram;

import static br.com.verdinhas.gafanhoto.util.Utils.addSeparators;
import static java.lang.System.lineSeparator;
import static java.util.Arrays.asList;
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
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import br.com.verdinhas.gafanhoto.monitor.CriadorDeMonitor;
import br.com.verdinhas.gafanhoto.monitor.Monitor;
import br.com.verdinhas.gafanhoto.monitor.MonitorRepository;

@Component
public class MonitorarCommand extends BaseBot {

	private static final int MAX_MONITORS_BY_USER = 10;

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

	public Ability start() {
		return Ability.builder().name("start").info("Inicia o bot com a mensagem de boas vindas").locality(USER)
				.privacy(PUBLIC).action(ctx -> {
					List<String> messages = new ArrayList<>();
					messages.add("Olá jovem gafanhoto.");
					messages.add("Apartir de agora, vou poupar você de ficar frenéticamente atrás de promoções.");
					messages.add(
							"Posso te avisar quando novas promoções surgirem, para isso você pode me pedir para monitorar certas palavras-chave para você.");
					messages.add(
							"Para fazer isso digite o comando /monitorar e informe até 5 palavras-chave como por exemplo: TV Samsung LED");
					messages.add(
							"Escolha as palavras com cuidado, pois só vou te mostrar caso todas elas apareçam no link da promoção.");
					messages.add("Quando mais palavras, mais específica será a busca.");
					messages.add("Para listar todos os comandos disponíveis digite /commands que te mostro.");

					sendConversation(messages, silent, ctx.chatId());
				}).build();
	}

	private void sendConversation(List<String> messages, SilentSender silent, long chatId) {
		for (String message : messages) {
			silent.send(message, chatId);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public Ability monitorar() {
		return Ability.builder().name("monitorar").info("Adiciona um monitor para as palavras-chave escolhidas")
				.locality(USER).privacy(PUBLIC).action(ctx -> {
					if (isValidArgumentsQuantity(ctx)) {
						silent.send("Quantidade de palavras-chave inválida. Insira de 1 a 5 palavras-chave.",
								ctx.chatId());
						return;
					}

					List<Monitor> userMonitors = monitorRepository.findByUserId(ctx.user().id());
					if (CollectionUtils.isNotEmpty(userMonitors) && userMonitors.size() == MAX_MONITORS_BY_USER) {
						silent.send(
								"Você quer boletar muitas coisas hein. Já existem 10 monitores cadastrados. Apague algum para cadastrar um novo.",
								ctx.chatId());
						return;
					}

					criadorDeMonitor.criar(ctx.user().id(), ctx.chatId(), new ArrayList<>(asList(ctx.arguments())));
					silent.send(buildMonitorarFeedbackMessage(ctx), ctx.chatId());
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
						silent.send("Você ainda não possui monitores", ctx.chatId());
						return;
					}

					SendMessage message = new SendMessage().setChatId(ctx.chatId())
							.setText("Qual monitor você quer apagar?");

					InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
					List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

					for (Monitor monitor : userMonitors) {
						List<InlineKeyboardButton> rowInline = new ArrayList<>();

						rowInline.add(new InlineKeyboardButton().setText(monitor.getKeyWordsWithSeparator())
								.setCallbackData("apagar-" + monitor.id));

						rowsInline.add(rowInline);
					}

					markupInline.setKeyboard(rowsInline);
					message.setReplyMarkup(markupInline);

					silent.execute(message);
				}).build();
	}

	public Ability listarMonitores() {
		return Ability.builder().name("listarMonitores").info("Lista todos os seus monitores").locality(USER)
				.privacy(PUBLIC).action(ctx -> {
					List<Monitor> userMonitors = monitorRepository.findByUserId(ctx.user().id());

					if (CollectionUtils.isEmpty(userMonitors)) {
						silent.send("Você ainda não possui monitores", ctx.chatId());
						return;
					}

					StringBuilder sb = new StringBuilder();
					for (Monitor monitor : userMonitors) {
						sb.append("* " + monitor.getKeyWordsWithSeparator()).append(System.lineSeparator());
					}

					silent.send(sb.toString(), ctx.chatId());
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
