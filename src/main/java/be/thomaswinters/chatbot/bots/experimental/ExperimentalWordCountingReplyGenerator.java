package be.thomaswinters.chatbot.bots.experimental;

import be.thomaswinters.chatbot.bots.WordCounterBasedReplier;
import be.thomaswinters.chatbot.bots.data.StringWordCounter;
import be.thomaswinters.chatbot.util.ConversationCollector;
import be.thomaswinters.generator.generators.IGenerator;
import be.thomaswinters.wordcounter.WordCounter;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;

public class ExperimentalWordCountingReplyGenerator extends WordCounterBasedReplier {

    public static final WordCounterWordToDoubleMapper FIRST_MAPPER = (word, wordCount, other, relativeTo, countBest) -> {
        double sameOccurrences = countBest.apply((double) wordCount.getCount(word), (double) other.getCount(word)) / other.getSize();
        double corpusOccurrences = ((double) relativeTo.getCount(word) + 1d);// / relativeTo.getSize();
        return (sameOccurrences) / (corpusOccurrences);
    };
    public static final WordCounterWordToDoubleMapper SECOND_MAPPER = (word, wordCount, other, relativeTo, countBest) -> {
        double sameOccurrences = countBest.apply((double) wordCount.getCount(word), (double) other.getCount(word));
        double corpusOccurrences = Math.pow(relativeTo.getCount(word), 2);
        return (sameOccurrences) / (corpusOccurrences);
    };

    private final WordCounterWordToDoubleMapper mapper;


    public ExperimentalWordCountingReplyGenerator(IGenerator<String> tweetGenerator,
                                                  WordCounter corpusWordCounter, int replyGenerations,
                                                  ConversationCollector weightedConversationCollector,
                                                  WordCounterWordToDoubleMapper mapper) {
        super(tweetGenerator, corpusWordCounter, replyGenerations, weightedConversationCollector);
        this.mapper = mapper;
    }

    private static double invertIfLower(double input) {
        return Math.min(input, 1d / input);
    }

    public static double getRelativeAmountOfSameWordsAs(
            WordCounter wordCount, WordCounter other, WordCounter relativeTo,
            WordCounterWordToDoubleMapper mapper,
            BiFunction<Double, Double, Double> countBest) {

        return other.getWordCount().elementSet().stream()
                .filter(wordCount::contains)
                .mapToDouble(word -> mapper.map(word, wordCount, other, relativeTo, countBest))
                .sum();
    }

    public static double getRelativeAmountOfSameWordsAs(
            @NotNull WordCounter wordCount, @NotNull WordCounter other, @NotNull WordCounter relativeTo, WordCounterWordToDoubleMapper mapper) {
        return getRelativeAmountOfSameWordsAs(wordCount, other, relativeTo, mapper, Math::min);
    }

    public static double getRelativeAmountOfSameMaxWordsAs(
            @NotNull WordCounter wordCount, @NotNull WordCounter other, @NotNull WordCounter relativeTo, WordCounterWordToDoubleMapper mapper) {
        return getRelativeAmountOfSameWordsAs(wordCount, other, relativeTo, mapper, Math::max);
    }

    @Override
    public double calculateScore(StringWordCounter inputMessage, WordCounter corpusWordCounter, StringWordCounter proposed) {
        if (!proposed.getWc().containsAny(inputMessage.getWc().getElements())) {
            return 0;
        }

        double result = invertIfLower(((double) proposed.getText().length()) / ((double) inputMessage.getText().length()))
                * getRelativeAmountOfSameMaxWordsAs(proposed.getWc(), inputMessage.getWc(), corpusWordCounter, mapper);
//        System.out.println(proposed.getText() + " -> " + result);
        return result;
    }


    /*-********************************************-*/


}
