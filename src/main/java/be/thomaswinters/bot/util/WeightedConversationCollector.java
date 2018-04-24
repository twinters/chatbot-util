package be.thomaswinters.bot.util;

import be.thomaswinters.bot.data.IChatMessage;
import be.thomaswinters.wordcounter.WordCounter;

import java.util.function.BiFunction;

/**
 * Class to help collect conversations of chat messages with weights to every message
 */
public class WeightedConversationCollector {
    private final BiFunction<IChatMessage, Integer, Integer> weightMapper;
    private final int maxMessages;

    public WeightedConversationCollector(int maxMessages, BiFunction<IChatMessage, Integer, Integer> weightMapper) {
        this.maxMessages = maxMessages;
        this.weightMapper = weightMapper;
    }

    public WordCounter collectConversation(IChatMessage message) {
        WordCounter.Builder b = WordCounter.builder();
        IChatMessage currentMessage = message;
        for (int i = 0; i < maxMessages; i++) {
            b.addWeighted(message.getMessage(), weightMapper.apply(message, i));
            if (currentMessage.getPrevious().isPresent()) {
                currentMessage = currentMessage.getPrevious().get();
            } else {
                break;
            }
        }
        return b.build();
    }
}
