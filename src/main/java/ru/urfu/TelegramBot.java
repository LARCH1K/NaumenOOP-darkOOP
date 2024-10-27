package ru.urfu;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * Телеграм бот
 */
public class TelegramBot extends TelegramLongPollingBot {

    /**
     * Имя бота в Telegram
     */
    private final String telegramBotName;

    /**
     * Обработчик сообщений
     */
    private final BotMessageProcessor botMessageProcessor;

    /**
     * Конструктор для создания экземпляра TelegramBot.
     *
     * @param telegramBotName Имя бота в Telegram
     * @param token токен для аутентификации бота в Telegram
     * @param botMessageProcessor обработчик сообщений
     */
    public TelegramBot(String telegramBotName, String token, BotMessageProcessor botMessageProcessor) {
        super(token);
        this.telegramBotName = telegramBotName;
        this.botMessageProcessor = botMessageProcessor;
    }

    /**
     * Запуск бота
     */
    public void start() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(this);
            System.out.println("Telegram бот запущен");
        } catch (TelegramApiException e) {
            throw new RuntimeException("Не удалось запустить телеграм бота", e);
        }
    }

    /**
     * Обрабатывает входящие обновления от Telegram.
     * @param update обновление, полученное от Telegram
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message updateMessage = update.getMessage();
            Long chatId = updateMessage.getChatId();
            String messageFromUser = updateMessage.getText();
            String answerMessage = botMessageProcessor.processYourMessage(messageFromUser);
            sendMessage(chatId.toString(), answerMessage);
        }
    }

    /**
     * Отправить сообщение
     * @param chatId идентификатор чата
     * @param message текст сообщения
     */
    public void sendMessage(String chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            System.err.println("Не удалось отправить сообщение. " + e.getMessage());
        }
    }

    /**
     * Возвращает имя бота.
     * @return имя бота в Telegram
     */
    @Override
    public String getBotUsername() {
        return telegramBotName;
    }
}
