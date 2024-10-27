package ru.urfu;

/**
 * Класс для запуска приложения
 */
public class Application {

    public static void main(String[] args) {
        BotMessageProcessor botMessageProcessor = new BotMessageProcessor();
        String telegramBotName = System.getenv("telegram_botName");
        String telegramToken = System.getenv("telegram_token");
        new TelegramBot(telegramBotName, telegramToken, botMessageProcessor)
                .start();

        String discordToken = System.getenv("discord_token");
        new DiscordBot(discordToken, botMessageProcessor)
                .start();

        // сколько угодно чат платформ и все должны работать одинаково
    }

}
