package be.thomaswinters.chatbot;

import be.thomaswinters.chatbot.data.IChatMessage;

import java.util.Optional;

public interface IChatBot {
    Optional<String> generateReply(IChatMessage message);
}
