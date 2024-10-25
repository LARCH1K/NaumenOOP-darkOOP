package ru.urfu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BotMessageProcessorTest {

    @Test
    public void testProcessYourMessage() {
        BotMessageProcessor botMessageProcessor = new BotMessageProcessor();
        String message = botMessageProcessor.processYourMessage("test");
        assertEquals("Ваше сообщение: test", message);
    }
}
