package be.thomaswinters.chatbot.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

public class ChatBox {
    private IChatMessage latestMessage = null;
    private List<BiConsumer<ChatBox, IChatMessage>> newChatConsumers = new ArrayList<>();

    public void postReaction(IChatUser user, String message) {
        IChatMessage chatMessage = new ChatMessage(Optional.ofNullable(latestMessage), message, user);
        this.latestMessage = chatMessage;
        newChatConsumers.stream().forEach(e -> e.accept(this, chatMessage));
    }

    public void addNewChatMessageListener(BiConsumer<ChatBox, IChatMessage> listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Listener can not be null");
        }
        this.newChatConsumers.add(listener);
    }

}
