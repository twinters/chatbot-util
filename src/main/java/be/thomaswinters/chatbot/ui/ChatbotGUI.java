package be.thomaswinters.chatbot.ui;

import be.thomaswinters.chatbot.IChatBot;
import be.thomaswinters.chatbot.bots.TextGeneratorChatBotAdaptor;
import be.thomaswinters.chatbot.data.ChatBotChatBox;
import be.thomaswinters.chatbot.data.ChatBox;
import be.thomaswinters.chatbot.data.IChatMessage;
import be.thomaswinters.chatbot.data.IChatUser;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.stream.Collectors;

public class ChatbotGUI {
    private JTextField chatInput;
    private JPanel mainPanel;
    private JLabel chatHistory;
    private JScrollPane scrollPane;

    private final ChatBotChatBox chatBotChatBox;

    private final List<String> lines = new ArrayList<>();

    public ChatbotGUI(IChatBot chatBot, String botName) {
        this.chatBotChatBox = new ChatBotChatBox(chatBot, botName, this::displayReaction);
        chatInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    chatBotChatBox.postReaction(chatBotChatBox.getUser(), chatInput.getText());
                    chatInput.setText("");
                }
            }
        });
        chatColours.put(chatBotChatBox.getUser(), "#103090");
        chatColours.put(chatBotChatBox.getChatBotUser(), "#903010");
//        chatHistory.setMaximumSize(new Dimension(scrollPane.getWidth(), -1));
    }

    public ChatbotGUI(IChatBot chatBot) {
        this(chatBot, "Bot");
    }

    private void addLine(String line) {
        lines.add(line);
        chatHistory.setText("<html>" + lines.stream().collect(Collectors.joining("<br>")) + "</html>");
        SwingUtilities.invokeLater(() -> {
                    scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
                    chatHistory.revalidate();
                }
        );
    }

    private final Map<IChatUser, String> chatColours = new HashMap<>();

    private void displayReaction(ChatBox chatBox, IChatMessage iChatMessage) {
        addLine("<font color='" + chatColours.get(iChatMessage.getUser()) + "'>" + iChatMessage.getUser() + ": </font>"
                + iChatMessage.getMessage());
    }

    public void run() {
        JFrame frame = new JFrame("Chatbot chatbox");
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        addLine("Chat with " + chatBotChatBox.getChatBotUser() + " activated. Say hello!");
        addLine("");
    }

    public static void main(String[] args) {
        new ChatbotGUI(new TextGeneratorChatBotAdaptor(() -> Optional.of("bonjour"))).run();
    }
}
