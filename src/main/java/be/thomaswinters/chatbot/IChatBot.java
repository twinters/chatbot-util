package be.thomaswinters.chatbot;

import be.thomaswinters.chatbot.data.IChatMessage;
import be.thomaswinters.generator.generators.reacting.IReactingGenerator;

import java.util.Optional;

@FunctionalInterface
public interface IChatBot extends IReactingGenerator<String,IChatMessage> {
    Optional<String> generateReply(IChatMessage message);

    @Override
    default Optional<String> generateRelated(IChatMessage input) {
        return generateReply(input);
    }
}
