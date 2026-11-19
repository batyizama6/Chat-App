
package com.mycompany.chatapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MessageManager {

    private final List<Message> sentMessages = new ArrayList<>();
    private final List<Message> disregardedMessages = new ArrayList<>();
    private final List<Message> storedMessages = new ArrayList<>();

    private final String fileName;

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public MessageManager() {
        this("messages.json");
    }

    public MessageManager(String fileName) {
        this.fileName = fileName;
        loadStoredMessages();
    }

    // Add a sent message
    public void addSentMessage(Message message) {
        sentMessages.add(message);
        Message.incrementTotalMessages();
    }

    // Add a disregarded message
    public void addDisregardedMessage(Message message) {
        disregardedMessages.add(message);
    }

    // Add and save a stored message
    public void addStoredMessage(Message message) {
        storedMessages.add(message);
        saveMessagesToJson();
    }

    // Populate the required arrays dynamically
    public Message[] getSentMessages() {
        return sentMessages.toArray(new Message[0]);
    }

    public Message[] getDisregardedMessages() {
        return disregardedMessages.toArray(new Message[0]);
    }

    public Message[] getStoredMessages() {
        return storedMessages.toArray(new Message[0]);
    }

    public String[] getMessageHashes() {
        List<String> hashes = new ArrayList<>();

        for (Message message : getAllMessages()) {
            hashes.add(message.getMessageHash());
        }

        return hashes.toArray(new String[0]);
    }

    public String[] getMessageIDs() {
        List<String> ids = new ArrayList<>();

        for (Message message : getAllMessages()) {
            ids.add(message.getMessageID());
        }

        return ids.toArray(new String[0]);
    }

    private List<Message> getAllMessages() {
        List<Message> allMessages = new ArrayList<>();

        allMessages.addAll(sentMessages);
        allMessages.addAll(disregardedMessages);
        allMessages.addAll(storedMessages);

        return allMessages;
    }

    // Display sender and recipient of stored messages
    public String displayStoredSendersAndRecipients() {
        StringBuilder result = new StringBuilder();

        for (Message message : storedMessages) {
            result.append("Sender: ")
                    .append(message.getSender())
                    .append("\nRecipient: ")
                    .append(message.getRecipient())
                    .append("\n\n");
        }

        return result.length() == 0
                ? "No stored messages found."
                : result.toString();
    }

    // Find the longest stored message
    public Message getLongestStoredMessage() {
        Message longest = null;

        for (Message message : storedMessages) {
            if (longest == null
                    || message.getMessage().length()
                    > longest.getMessage().length()) {
                longest = message;
            }
        }

        return longest;
    }

    // Search for a message using its ID
    public Message searchMessageByID(String messageID) {
        for (Message message : getAllMessages()) {
            if (message.getMessageID().equals(messageID)) {
                return message;
            }
        }

        return null;
    }

    // Search sent and stored messages by recipient
    public List<Message> searchMessagesByRecipient(String recipient) {
        List<Message> results = new ArrayList<>();

        for (Message message : sentMessages) {
            if (message.getRecipient().equals(recipient)) {
                results.add(message);
            }
        }

        for (Message message : storedMessages) {
            if (message.getRecipient().equals(recipient)) {
                results.add(message);
            }
        }

        return results;
    }

    // Delete a stored message using its hash
    public boolean deleteMessageByHash(String messageHash) {
        boolean deleted = storedMessages.removeIf(
                message -> message.getMessageHash().equals(messageHash));

        if (deleted) {
            saveMessagesToJson();
        }

        return deleted;
    }

    // Generate a report of all stored messages
    public String displayReport() {
        StringBuilder report = new StringBuilder();

        for (Message message : storedMessages) {
            report.append("\nMessage ID: ")
                    .append(message.getMessageID())
                    .append("\nMessage Hash: ")
                    .append(message.getMessageHash())
                    .append("\nSender: ")
                    .append(message.getSender())
                    .append("\nRecipient: ")
                    .append(message.getRecipient())
                    .append("\nMessage: ")
                    .append(message.getMessage())
                    .append("\n-------------------------\n");
        }

        return report.length() == 0
                ? "No stored messages available."
                : report.toString();
    }

    // Save stored messages to JSON
    public void saveMessagesToJson() {
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(storedMessages, writer);
        } catch (IOException e) {
            System.out.println("Error saving messages: " + e.getMessage());
        }
    }

    // Read stored messages from JSON
    private void loadStoredMessages() {
        java.io.File file = new java.io.File(fileName);

        if (!file.exists() || file.length() == 0) {
            return;
        }

        try (FileReader reader = new FileReader(file)) {
            Type listType = new TypeToken<ArrayList<Message>>() {}.getType();

            List<Message> loaded = gson.fromJson(reader, listType);

            if (loaded != null) {
                storedMessages.addAll(loaded);
            }

        } catch (IOException e) {
            System.out.println("Error reading messages: " + e.getMessage());
        }
    }
}
