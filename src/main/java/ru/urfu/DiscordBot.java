package ru.urfu;

import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;

/**
 * Дискорд бот
 */
public class DiscordBot {

    /**
     * Токен для аутентификации бота в Discord
     */
    private final String token;

    /**
     * Обработчик сообщений
     */
    private final BotMessageProcessor botMessageProcessor;

    /**
     * Клиент для взаимодействия с Discord
     */
    private GatewayDiscordClient client;

    /**
     * Конструктор для создания экземпляра DiscordBot.
     *
     * @param token токен для аутентификации бота в Discord
     * @param botMessageProcessor обработчик сообщений
     */
    public DiscordBot(String token, BotMessageProcessor botMessageProcessor) {
        this.token = token;
        this.botMessageProcessor = botMessageProcessor;
    }

    /**
     * Запуск бота
     */
    public void start() {
        client = DiscordClient.create(token).login().block();
        if (client == null) {
            throw new RuntimeException("Ошибка при входе в Discord");
        }
        client.on(MessageCreateEvent.class)
                .doOnError(throwable -> {
                    throw new RuntimeException("Ошибка при работе Discord бота", throwable);
                })
                .subscribe(event -> {
                    Message eventMessage = event.getMessage();
                    if (eventMessage.getAuthor().map(user -> !user.isBot()).orElse(false)) {
                        String chatId = eventMessage.getChannelId().asString();
                        String messageFromUser = eventMessage.getContent();
                        String answerMessage = botMessageProcessor.processYourMessage(messageFromUser);
                        sendMessage(chatId, answerMessage);
                    }
                });
        System.out.println("Discord бот запущен");
        client.onDisconnect().block();
    }

    /**
     * Отправить сообщение
     * @param chatId идентификатор чата
     * @param message текст сообщения
     */
    public void sendMessage(String chatId, String message) {
        Snowflake channelId = Snowflake.of(chatId);
        MessageChannel channel = client.getChannelById(channelId).ofType(MessageChannel.class).block();
        if (channel != null) {
            channel.createMessage(message).block();
        } else {
            System.err.println("Канал не найден");
        }
    }
}
