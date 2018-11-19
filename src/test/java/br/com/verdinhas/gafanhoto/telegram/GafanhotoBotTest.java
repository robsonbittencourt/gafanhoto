package br.com.verdinhas.gafanhoto.telegram;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class GafanhotoBotTest {

    @Spy
    @InjectMocks
    private GafanhotoBot gafanhotoBot;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void shouldSendMessageWhenTelegramSuccess() throws TelegramApiException {
        doReturn(null).when(gafanhotoBot).execute(any(SendMessage.class));

        boolean messageSent = gafanhotoBot.sendMessageToUser(1L, "Hello");

        verify(gafanhotoBot).execute(any(SendMessage.class));
        assertTrue(messageSent);
    }

}