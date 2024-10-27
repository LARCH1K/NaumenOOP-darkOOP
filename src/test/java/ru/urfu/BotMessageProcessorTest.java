package ru.urfu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Класс BotMessageProcessorTest содержит тесты для проверки функциональности класса BotMessageProcessor.
 */
public class BotMessageProcessorTest {

    /**
     * Проверка метода processYourMessage класса BotMessageProcessor.<br>
     * Этот тест отправляет строку "test" в метод processYourMessage и
     * проверяет, что возвращаемое сообщение соответствует ожидаемому
     * формату: "Ваше сообщение: test".
     */
    @Test
    public void testProcessYourMessage() {
        BotMessageProcessor botMessageProcessor = new BotMessageProcessor();
        String message = botMessageProcessor.processYourMessage("test");
        assertEquals("Ваше сообщение: test", message);
    }
}
