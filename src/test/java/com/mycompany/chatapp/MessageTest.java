
package com.mycompany.chatapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testMessageIDIsGeneratedCorrectly() {
        Message message = new Message(
                1, "+27718693002", "Hi Mike");

        assertNotNull(message.getMessageID());
        assertEquals(10, message.getMessageID().length());
        assertTrue(message.checkMessageID());
    }

    @Test
    public void testValidRecipientNumber() {
        Message message = new Message(
                1, "+27718693002", "Hello");

        assertEquals(
                "Cell phone number successfully captured.",
                message.checkRecipientCell());
    }

    @Test
    public void testInvalidRecipientNumber() {
        Message message = new Message(
                1, "08575975889", "Hello");

        assertEquals(
                "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                message.checkRecipientCell());
    }

    @Test
    public void testMessageWithin250Characters() {
        Message message = new Message(
                1, "+27718693002", "Hello there");

        assertEquals(
                "Message ready to send.",
                message.checkMessageLength());
    }

    @Test
    public void testMessageExceeds250Characters() {
        String longMessage = "A".repeat(251);

        Message message = new Message(
                1, "+27718693002", longMessage);

        assertEquals(
                "Message exceeds 250 characters by 1; please reduce the size.",
                message.checkMessageLength());
    }

    @Test
    public void testMessageHashIsGenerated() {
        Message message = new Message(
                1, "+27718693002", "Hi Mike");

        assertNotNull(message.getMessageHash());
        assertFalse(message.getMessageHash().isEmpty());
        assertEquals(
                message.createMessageHash(),
                message.getMessageHash());
    }

    @Test
    public void testMessageNumberIsCorrect() {
        Message message = new Message(
                2, "+27718693002", "Hello there");

        assertEquals(2, message.getMessageNumber());
    }

    @Test
    public void testMessageDetailsAreStored() {
        Message message = new Message(
                1, "+27718693002", "Hello there");

        assertEquals("+27718693002", message.getRecipient());
        assertEquals("Hello there", message.getMessage());
    }
    
    @Test
    public void testExactMessageHashFormat() {
        Message message = new Message(
                0, "+27718693002", "Hi Mike");

        assertEquals(
                message.getMessageID().substring(0, 2)
                        + ":0:HIMIKE",
                message.getMessageHash());
    }
    @Test
public void testMessageIDContainsOnlyDigits() {
    Message message = new Message(
            0, "+27812345678", "Hello there");

    assertTrue(message.getMessageID().matches("\\d{10}"));
}
@Test
public void testMessageLengthExactly250Characters() {
    String text = "A".repeat(250);
    Message message = new Message(
            0, "+27812345678", text);

    assertEquals("Message ready to send.",
            message.checkMessageLength());
}
@Test
public void testMessageLength251Characters() {
    String text = "A".repeat(251);
    Message message = new Message(
            0, "+27812345678", text);

    assertEquals(
            "Message exceeds 250 characters by 1; please reduce the size.",
            message.checkMessageLength());
}
@Test
public void testEmptyMessageIsRejected() {
    Message message = new Message(
            0, "+27812345678", "");

    assertEquals("Please enter a message.",
            message.checkMessageLength());
}
@Test
public void testWhitespaceOnlyMessageIsRejected() {
    Message message = new Message(
            0, "+27812345678", "   ");

    assertEquals("Please enter a message.",
            message.checkMessageLength());
}
@Test
public void testRecipientWithWrongCountryCodeIsRejected() {
    Message message = new Message(
            0, "+12345678901", "Hello");

    assertEquals(
            "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
            message.checkRecipientCell());
}
@Test
public void testRecipientWithoutInternationalCodeIsRejected() {
    Message message = new Message(
            0, "0812345678", "Hello");

    assertEquals(
            "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
            message.checkRecipientCell());
}
@Test
public void testRecipientWithTooFewDigitsIsRejected() {
    Message message = new Message(
            0, "+2781234567", "Hello");

    assertEquals(
            "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
            message.checkRecipientCell());
}
}
