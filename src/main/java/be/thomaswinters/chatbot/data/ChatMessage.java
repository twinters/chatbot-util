package be.thomaswinters.chatbot.data;

import java.util.Optional;

public class ChatMessage implements IChatMessage {
    private final Optional<IChatMessage> previous;
    private final String message;
    private final IChatUser user;

    public ChatMessage(Optional<IChatMessage> previous, String message, IChatUser user) {
        this.previous = previous;
        this.message = message;
        this.user = user;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Optional<IChatMessage> getPrevious() {
        return previous;
    }

    @Override
    public IChatUser getUser() {
        return user;
    }

    @Override
    public String toString() {
        return user.getScreenName() + ": " + message;
    }
}
