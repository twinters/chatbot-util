package be.thomaswinters.chatbot.data;

import be.thomaswinters.chatbot.IChatBot;

import java.util.function.BiConsumer;

public class ChatBotChatBox {
    private final IChatBot chatBot;
    private final ChatBox chatBox = new ChatBox();
    private final ChatUser user = new ChatUser("Me");
    private final ChatUser botUser;

    public ChatBotChatBox(IChatBot chatBot) {
        this.chatBot = chatBot;
        this.botUser = new ChatUser(chatBot.getClass().getSimpleName());
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

    public IChatBot getChatBot() {
        return chatBot;
    }
}
