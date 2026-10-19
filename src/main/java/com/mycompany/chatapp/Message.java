
package com.mycompany.chatapp;

import java.util.Random;

public class Message {

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String message;
    private String messageHash;

    private static int totalMessages = 0;

    public Message() {
    }

    public Message(int messageNumber, String recipient, String message) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.message = message;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    private String generateMessageID() {
        Random random = new Random();
        long number = random.nextLong(1_000_000_000L, 10_000_000_000L);
        return String.valueOf(number);
    }

    public boolean checkMessageID() {
        return messageID != null
                && messageID.length() <= 10;
    }

    public String checkRecipientCell() {
        if (recipient != null
                && recipient.matches("^\\+27\\d{9}$")) {
            return "Cell phone number successfully captured.";
        }

        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    public String checkMessageLength() {
   if (message == null || message.trim().isEmpty()) {
            return "Please enter a message.";
        }

        if (message.length() <= 250) {
            return "Message ready to send.";
        }

        int excess = message.length() - 250;
        return "Message exceeds 250 characters by " + excess
                + "; please reduce the size.";
    }

    public String createMessageHash() {
        if (messageID == null || message == null
                || message.trim().isEmpty()) {
            return "";
        }

        String[] words = message.trim().split("\\s+");

        String firstWord = words[0].replaceAll("[^a-zA-Z0-9]", "");
        String lastWord = words[words.length - 1]
                .replaceAll("[^a-zA-Z0-9]", "");

        String firstTwoID = messageID.substring(0, 2);

        return (firstTwoID + ":" + messageNumber + ":"
                + firstWord + lastWord).toUpperCase();
    }

    public String getMessageID() {
        return messageID;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public static int returnTotalMessages() {
        return totalMessages;
    }

    public static void incrementTotalMessages() {
        totalMessages++;
    }
}
