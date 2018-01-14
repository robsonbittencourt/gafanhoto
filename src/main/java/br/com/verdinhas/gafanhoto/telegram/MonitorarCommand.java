package br.com.verdinhas.gafanhoto.telegram;

import static java.lang.System.lineSeparator;
import static org.telegram.abilitybots.api.objects.Locality.USER;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.MessageContext;

import br.com.verdinhas.gafanhoto.monitor.CriadorDeMonitor;

@Component
public class MonitorarCommand extends BaseBot {

	@Autowired
	private CriadorDeMonitor criadorDeMonitor;

	public Ability monitorar() {
		return Ability.builder().name("monitorar").info("Adiciona um monitor para as palavras chaves escolhidas")
				.locality(USER).privacy(PUBLIC).action(ctx -> {
					if (isValidArgumentsQuantity(ctx)) {
						silent.send("Quantidade de palavras chaves inválida. Insira de 1 a 5 palavras chaves.",
								ctx.chatId());
					} else {
						criadorDeMonitor.criar(ctx.user().id(), new ArrayList<>(Arrays.asList(ctx.arguments())));
						silent.send(buildMonitorarFeedbackMessage(ctx), ctx.chatId());
					}
				}).build();
	}

	private boolean isValidArgumentsQuantity(MessageContext ctx) {
		return ctx.arguments().length == 0 || ctx.arguments().length > 5;
	}

	private String buildMonitorarFeedbackMessage(MessageContext ctx) {
		StringBuilder sb = new StringBuilder();

		sb.append("Monitor criado com sucesso.");
		sb.append("Vou te avisar quando aparecer uma oferta que atenda as palavras chave:").append(lineSeparator());
		sb.append(addSeparators(ctx.arguments()));

		return sb.toString();
	}

	private String addSeparators(String[] words) {
		StringBuilder sb = new StringBuilder();

		for (String arg : words) {
			sb.append(arg).append(" - ");
		}

		String withSeparators = sb.toString();
		String withoutLastSeparator = withSeparators.substring(0, withSeparators.length() - 2);

		return withoutLastSeparator;
	}

}
