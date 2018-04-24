package be.thomaswinters.chatbot.data;

import java.util.Optional;

public class ChatUser implements IChatUser {
    private final String name;

    public ChatUser(String name) {
        this.name = name;
    }

    @Override
    public Optional<String> getFullName() {
        return Optional.empty();
    }

    @Override
    public String getScreenName() {
        return name;
    }
}
