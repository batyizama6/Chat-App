/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.chatapp;

import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.*;

public class MessageManagerTest {

    @TempDir
    Path tempDir;

    private MessageManager manager;

    private Message message1;
    private Message message2;
    private Message message3;
    private Message message4;
    private Message message5;

    @BeforeEach
    public void setUp() {

        manager = new MessageManager(
                tempDir.resolve("messages.json").toString());

        // Test Message 1 - Sent
        message1 = new Message(
                1, "0838884567",
                "+27834557896",
                "Did you get the cake?");

        // Test Message 2 - Stored
        message2 = new Message(
                2, "0838884567",
                "+27838884567",
                "Where are you? You are late! I have asked you to be on time.");

        // Test Message 3 - Disregarded
        message3 = new Message(
                3, "0838884567",
                "+27834484567",
                "Yohoooo, I am at your gate.");

        // Test Message 4 - Sent
        message4 = new Message(
                4, "0838884567",
                "+27838884567",
                "It is dinner time!");

        // Test Message 5 - Stored
        message5 = new Message(
                5, "0838884567",
                "+27838884567",
                "Ok, I am leaving without you.");

        manager.addSentMessage(message1);
        manager.addStoredMessage(message2);
        manager.addDisregardedMessage(message3);
        manager.addSentMessage(message4);
        manager.addStoredMessage(message5);
    }

    // Test 1: Sent Messages array correctly populated
    @Test
    public void testSentMessagesArrayCorrectlyPopulated() {

        Message[] sentMessages = manager.getSentMessages();

        assertEquals(2, sentMessages.length);

        assertEquals(
                "Did you get the cake?",
                sentMessages[0].getMessage());

        assertEquals(
                "It is dinner time!",
                sentMessages[1].getMessage());
    }

    // Test 2: Display the longest stored message
    @Test
    public void testDisplayLongestStoredMessage() {

        Message longest = manager.getLongestStoredMessage();

        assertNotNull(longest);

        assertEquals(
                "Where are you? You are late! I have asked you to be on time.",
                longest.getMessage());
    }

    // Test 3: Search for a message using its ID
    @Test
    public void testSearchForMessageID() {

        String id = message4.getMessageID();

        Message result = manager.searchMessageByID(id);

        assertNotNull(result);

        assertEquals(
                "+27838884567",
                result.getRecipient());

        assertEquals(
                "It is dinner time!",
                result.getMessage());
    }

    // Test 4: Search sent and stored messages by recipient
    @Test
    public void testSearchMessagesByRecipient() {

        List<Message> results =
                manager.searchMessagesByRecipient("+27838884567");

        assertEquals(3, results.size());

        assertTrue(results.stream().anyMatch(
                m -> m.getMessage().equals(
                        "Where are you? You are late! I have asked you to be on time.")));

        assertTrue(results.stream().anyMatch(
                m -> m.getMessage().equals("It is dinner time!")));

        assertTrue(results.stream().anyMatch(
                m -> m.getMessage().equals(
                        "Ok, I am leaving without you.")));
    }

    // Test 5: Delete a message using its hash
    @Test
    public void testDeleteMessageUsingMessageHash() {

        String hash = message2.getMessageHash();

        boolean deleted = manager.deleteMessageByHash(hash);

        assertTrue(deleted);

        Message[] storedMessages = manager.getStoredMessages();

        assertEquals(1, storedMessages.length);

        assertFalse(
                manager.deleteMessageByHash(hash));
    }

    // Test 6: Display the full stored messages report
    @Test
    public void testDisplayReport() {

        String report = manager.displayReport();

        assertTrue(report.contains(message2.getMessageID()));
        assertTrue(report.contains(message2.getMessageHash()));
        assertTrue(report.contains("+27838884567"));
        assertTrue(report.contains(
                "Where are you? You are late! I have asked you to be on time."));

        assertTrue(report.contains(message5.getMessageID()));
        assertTrue(report.contains(message5.getMessageHash()));
        assertTrue(report.contains("Ok, I am leaving without you."));
    }
    //Test 7: 
@Test
public void testAllArraysCorrectlyPopulated() {

    assertEquals(2, manager.getSentMessages().length);
    assertEquals(1, manager.getDisregardedMessages().length);
    assertEquals(2, manager.getStoredMessages().length);

    assertEquals(5, manager.getMessageHashes().length);
    assertEquals(5, manager.getMessageIDs().length);
}
//Test 8: checks that JSON data can actually be read back
@Test
public void testStoredMessagesLoadFromJSON() {

    String filePath = tempDir.resolve("messages.json").toString();

    MessageManager loadedManager = new MessageManager(filePath);

    assertEquals(2, loadedManager.getStoredMessages().length);
}
}
  

