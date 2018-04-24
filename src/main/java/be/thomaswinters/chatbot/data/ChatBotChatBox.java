package be.thomaswinters.chatbot.data;

import be.thomaswinters.chatbot.IChatBot;

import java.util.function.BiConsumer;
import java.util.stream.Stream;

public class ChatBotChatBox {
    private final IChatBot chatBot;
    private final ChatBox chatBox = new ChatBox();
    private final ChatUser user = new ChatUser("Me");
    private final ChatUser botUser = new ChatUser("Bot");

    @SafeVarargs
    public ChatBotChatBox(IChatBot chatBot, BiConsumer<ChatBox, IChatMessage>... chatMessageConsumers) {
        this.chatBot = chatBot;
//        this.botUser = new ChatUser(chatBot.getClass().getSimpleName());
        Stream.of(chatMessageConsumers).forEach(this::addNewChatMessageListener);
        this.chatBox.addNewChatMessageListener(this::reactWithChatbot);
    }

    private void reactWithChatbot(ChatBox chatBox, IChatMessage iChatMessage) {
        if (iChatMessage.getUser().equals(user)) {
            chatBot.generateReply(iChatMessage).ifPresent(reply -> chatBox.postReaction(botUser, reply));
        }
    }

    public void postReaction(IChatUser user, String message) {
        chatBox.postReaction(user, message);
    }

    public void addNewChatMessageListener(BiConsumer<ChatBox, IChatMessage> listener) {
        chatBox.addNewChatMessageListener(listener);
    }

    public ChatUser getUser() {
        return user;
    }

    public ChatUser getChatBotUser() {
        return botUser;
    }

    public IChatBot getChatBot() {
        return chatBot;
    }
}