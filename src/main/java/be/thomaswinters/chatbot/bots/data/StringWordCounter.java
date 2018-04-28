package be.thomaswinters.chatbot.bots.data;

import be.thomaswinters.wordcounter.WordCounter;

public class StringWordCounter {

    private final String text;
    private final WordCounter wc;

    public StringWordCounter(String text, WordCounter wordCounter) {
        this.text = text;
        this.wc = wordCounter;
    }

    public StringWordCounter(String text) {
        this(text, new WordCounter(text));
    }

    public WordCounter getWc() {
        return wc;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}
