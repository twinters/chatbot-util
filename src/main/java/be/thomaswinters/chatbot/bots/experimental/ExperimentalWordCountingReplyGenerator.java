package be.thomaswinters.chatbot.bots.experimental;

import be.thomaswinters.chatbot.bots.WordCounterBasedReplier;
import be.thomaswinters.chatbot.bots.data.StringWordCounter;
import be.thomaswinters.chatbot.util.ConversationCollector;
import be.thomaswinters.generator.generators.IGenerator;
import be.thomaswinters.wordcounter.WordCounter;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class ExperimentalWordCountingReplyGenerator extends WordCounterBasedReplier {

    public ExperimentalWordCountingReplyGenerator(IGenerator<String> tweetGenerator,
                                                  WordCounter corpusWordCounter, int replyGenerations,
                                                  ConversationCollector weightedConversationCollector) {
        super(tweetGenerator, corpusWordCounter, replyGenerations, weightedConversationCollector);
    }

    @Override
    public double calculateScore(StringWordCounter inputMessage, WordCounter corpusWordCounter, StringWordCounter proposed) {
        if (!proposed.getWc().containsAny(inputMessage.getWc().getElements())) {
            return 0;
        }

        double result = invertIfLower(((double) proposed.getText().length()) / ((double) inputMessage.getText().length()))
                * getRelativeAmountOfSameMaxWordsAs(proposed.getWc(), inputMessage.getWc(), corpusWordCounter);
        System.out.println(proposed.getText() + " -> " + result);
        return result;
    }

    private static double invertIfLower(double input) {
        return Math.min(input, 1d / input);
    }


    public static double getRelativeAmountOfSameWordsAs(
            @NotNull WordCounter wordCount, @NotNull WordCounter other, @NotNull WordCounter relativeTo,
            Function<Double, Function<Double, Double>> countBest) {

        return other.getWordCount().elementSet().stream()
                .filter(wordCount::contains)
                .mapToDouble(word -> {
                    double sameOccurrences = countBest.apply((double) wordCount.getCount(word)).apply((double) other.getCount(word));
                    double corpusOccurrences = Math.pow(relativeTo.getCount(word), 2);
                    return (sameOccurrences) / Math.max(1, corpusOccurrences);
                })
                .sum();
    }

    public static double getRelativeAmountOfSameWordsAs(
            @NotNull WordCounter wordCount, @NotNull WordCounter other, @NotNull WordCounter relativeTo) {
        return getRelativeAmountOfSameWordsAs(wordCount, other, relativeTo, e -> f -> Math.min(e, f));
    }

    public static double getRelativeAmountOfSameMaxWordsAs(
            @NotNull WordCounter wordCount, @NotNull WordCounter other, @NotNull WordCounter relativeTo) {
        return getRelativeAmountOfSameWordsAs(wordCount, other, relativeTo, e -> f -> Math.max(e, f));
    }


    /*-********************************************-*/


}
