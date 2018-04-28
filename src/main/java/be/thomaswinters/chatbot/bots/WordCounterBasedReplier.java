package be.thomaswinters.chatbot.bots;

import be.thomaswinters.chatbot.IChatBot;
import be.thomaswinters.chatbot.bots.data.StringWordCounter;
import be.thomaswinters.chatbot.data.IChatMessage;
import be.thomaswinters.chatbot.util.ConversationCollector;
import be.thomaswinters.generator.generators.IGenerator;
import be.thomaswinters.wordcounter.WordCounter;

import java.util.Comparator;
import java.util.Optional;

public abstract class WordCounterBasedReplier implements IChatBot {

    private final IGenerator<String> tweetGenerator;
    private final WordCounter corpusWordCounter;
    private final ConversationCollector weightedConversationCollector;
    private final int replyGenerations;

    public WordCounterBasedReplier(IGenerator<String> tweetGenerator,
                                   WordCounter corpusWordCounter, int replyGenerations,
                                   ConversationCollector weightedConversationCollector) {
        this.tweetGenerator = tweetGenerator;
        this.corpusWordCounter = corpusWordCounter;
        this.replyGenerations = replyGenerations;
        this.weightedConversationCollector = weightedConversationCollector;
    }

    @Override
    public Optional<String> generateReply(IChatMessage message) {
        return replyGenerator(message).generate();
    }

    public IGenerator<String> replyGenerator(IChatMessage chatMessage) {
        StringWordCounter inputMessageWc = new StringWordCounter(
                chatMessage.getMessage(),
                weightedConversationCollector.collectConversation(chatMessage));

        return tweetGenerator
                .mapToDifferent(StringWordCounter::new)
                .max(replyGenerations,
                        Comparator.comparingDouble(e -> calculateScore(inputMessageWc, corpusWordCounter, e)))
                .mapToDifferent(StringWordCounter::getText);
    }

    public abstract double calculateScore(StringWordCounter inputMessage,
                                          WordCounter corpusWordCounter, StringWordCounter proposed);
}