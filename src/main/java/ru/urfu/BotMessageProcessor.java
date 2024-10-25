package ru.urfu;

/**
 * Класс BotMessageProcessor предназначен для обработки сообщений
 */
public class BotMessageProcessor {

    /**
     * Обрабатывает входящее сообщение, добавляя к нему фиксированный текст.
     *
     * @param message входящее сообщение, которое требуется обработать.
     * @return обработанное сообщение, содержащее текст "Ваше сообщение:" и текст исходного сообщения.
     */
    public String processYourMessage(String message) {
        return "Ваше сообщение: " + message;
    }
}
