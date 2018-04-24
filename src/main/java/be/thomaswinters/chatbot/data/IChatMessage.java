package be.thomaswinters.chatbot.data;

import java.util.Optional;

public interface IChatMessage {

    String getMessage();

    Optional<IChatMessage> getPrevious();

    IChatUser getUser();
}
