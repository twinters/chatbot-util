package be.thomaswinters.chatbot.data;

import java.util.Optional;

public interface IChatMessage {

    @Deprecated
    default String getMessage() {
        return getText();
    }

    String getText();

    Optional<IChatMessage> getPrevious();

    IChatUser getUser();

    Optional<String> getURL();
}
