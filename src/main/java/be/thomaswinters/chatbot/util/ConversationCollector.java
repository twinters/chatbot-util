package be.thomaswinters.chatbot.util;

import be.thomaswinters.chatbot.data.IChatMessage;
import be.thomaswinters.wordcounter.WordCounter;

import java.util.function.BiFunction;

/**
 * Class to help collect conversations of chat messages with weights to every message
 */
public class ConversationCollector {
    public static final BiFunction<IChatMessage, Integer, Integer> MAP_TO_ONE = (i, j) -> 1;

    private final String userName;
    private final int maxMessages;
    private final BiFunction<IChatMessage, Integer, Integer> ownMessagesWeightMapper;
    private final BiFunction<IChatMessage, Integer, Integer> otherMessagesWeightMapper;

    public ConversationCollector(String userName, int maxMessages,
                                 BiFunction<IChatMessage, Integer, Integer> ownMessagesWeightMapper,
                                 BiFunction<IChatMessage, Integer, Integer> otherMessagesWeightMapper) {
        this.userName = userName.toLowerCase();
        this.maxMessages = maxMessages;
        this.ownMessagesWeightMapper = ownMessagesWeightMapper;
        this.otherMessagesWeightMapper = otherMessagesWeightMapper;
    }

    public ConversationCollector(String username, int maxMessages) {
        this(username, maxMessages, MAP_TO_ONE, MAP_TO_ONE);
    }

    public WordCounter collectConversation(IChatMessage message) {
        WordCounter.Builder b = WordCounter.builder();
        IChatMessage currentMessage = message;
        for (int i = 0; i < maxMessages; i++) {

            BiFunction<IChatMessage, Integer, Integer> weightMapper =
                    currentMessage.getUser().getScreenName().toLowerCase().equals(userName) ?
                            ownMessagesWeightMapper : otherMessagesWeightMapper;

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
