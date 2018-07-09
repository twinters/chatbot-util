package be.thomaswinters.chatbot;

import be.thomaswinters.chatbot.data.IChatMessage;
import be.thomaswinters.generator.generators.reacting.IReactingGenerator;
import be.thomaswinters.generator.streamgenerator.IStreamGenerator;

import java.util.Optional;

@FunctionalInterface
public interface IChatBot extends IReactingGenerator<String, IChatMessage> {
    Optional<String> generateReply(IChatMessage message);

    @Override
    default Optional<String> generateRelated(IChatMessage input) {
        return generateReply(input);
    }


    @Override
    default IStreamGenerator<String> reactToStreamGenerator(IStreamGenerator<IChatMessage> streamGenerator) {
        return () -> streamGenerator
                .generateStream()
                .map(message -> generateRelated(message)
                        .map(result -> result + (message.getURL().isPresent() ? " " + message.getURL().get() : "")))
                .filter(Optional::isPresent)
                .map(Optional::get);
    }
}
