package be.thomaswinters.chatbot.bots.experimental;

import be.thomaswinters.wordcounter.WordCounter;

import java.util.function.BiFunction;

@FunctionalInterface
public interface WordCounterWordToDoubleMapper {
    Double map(String word,
            WordCounter wordCount, WordCounter other, WordCounter relativeTo,
            BiFunction<Double, Double, Double> countBest);
}
