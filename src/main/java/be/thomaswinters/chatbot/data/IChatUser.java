package be.thomaswinters.chatbot.data;

import java.util.Optional;

public interface IChatUser {
    Optional<String> getFullName();

    String getScreenName();

    long getId();
}
