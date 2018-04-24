package be.thomaswinters.chatbot.bots;

import be.thomaswinters.chatbot.IChatBot;
import be.thomaswinters.chatbot.data.IChatMessage;
import be.thomaswinters.generator.generators.IGenerator;

import java.util.Optional;

/**
 * Bot using the capabilities of the text generator chatbot to reply to people (without using the chat messages)
 */
public class TextGeneratorChatBotAdaptor implements IChatBot {
    private final IGenerator<String> bot;

    public TextGeneratorChatBotAdaptor(IGenerator<String> bot) {
        this.bot = bot;
    }

    @Override
    public Optional<String> generateReply(IChatMessage message) {
        return bot.generate();
    }
}
