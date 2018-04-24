package be.thomaswinters.chatbot.ui;

import be.thomaswinters.chatbot.IChatBot;
import be.thomaswinters.chatbot.data.ChatBotChatBox;
import be.thomaswinters.chatbot.data.ChatBox;
import be.thomaswinters.chatbot.data.IChatMessage;

import java.util.Scanner;

public class ChatbotCLI {
    private final ChatBotChatBox chatBotChatBox;

    public ChatbotCLI(IChatBot chatBot) {
        this.chatBotChatBox = new ChatBotChatBox(chatBot);
        this.chatBotChatBox.addNewChatMessageListener(this::displayReaction);
    }

    private void displayReaction(ChatBox chatBox, IChatMessage iChatMessage) {
        if (!iChatMessage.getUser().equals(chatBotChatBox.getUser())) {
            System.out.println(iChatMessage);
        }
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Chat with " + chatBotChatBox.getChatBot() + " activated. Say hello!");
        while (true) {
            String line = sc.nextLine();
            chatBotChatBox.postReaction(chatBotChatBox.getUser(), line);
        }
    }
}
