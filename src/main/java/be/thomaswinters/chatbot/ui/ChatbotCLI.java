package be.thomaswinters.chatbot.ui;

import be.thomaswinters.chatbot.IChatBot;
import be.thomaswinters.chatbot.data.ChatBox;
import be.thomaswinters.chatbot.data.ChatUser;
import be.thomaswinters.chatbot.data.IChatMessage;

import java.util.Scanner;

public class ChatbotCLI {
    private final IChatBot chatBot;
    private final ChatBox chatBox = new ChatBox();
    private final ChatUser user = new ChatUser("Me");
    private final ChatUser botUser;

    public ChatbotCLI(IChatBot chatBot) {
        this.chatBot = chatBot;
        this.botUser = new ChatUser(chatBot.getClass().getSimpleName());
        this.chatBox.addNewChatMessageListener(this::displayReaction);
        this.chatBox.addNewChatMessageListener(this::reactWithChatbot);
    }

    private void displayReaction(ChatBox chatBox, IChatMessage iChatMessage) {
        if (!iChatMessage.getUser().equals(user)) {
            System.out.println(iChatMessage);
        }
    }

    private void reactWithChatbot(ChatBox chatBox, IChatMessage iChatMessage) {
        if (iChatMessage.getUser().equals(user)) {
            chatBot.generateReply(iChatMessage).ifPresent(reply -> chatBox.postReaction(botUser, reply));
        }
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Chat with " + botUser.getScreenName() + " activated. Say hello!");
        while (true) {
            String line = sc.nextLine();
            chatBox.postReaction(user, line);
        }
    }
}
