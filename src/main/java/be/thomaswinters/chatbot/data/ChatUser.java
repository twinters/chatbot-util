package be.thomaswinters.chatbot.data;

import java.util.Optional;

public class ChatUser implements IChatUser {
    private final String name;
    private final String fullName;
    private final long id;

    public ChatUser(String name, String fullName, long id) {
        this.name = name;
        this.fullName = fullName;
        this.id = id;
    }
    public ChatUser(String name) {
        this(name, null, -1);
    }


    @Override
    public Optional<String> getFullName() {
        return Optional.ofNullable(fullName);
    }

    @Override
    public String getScreenName() {
        return name;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }
}
