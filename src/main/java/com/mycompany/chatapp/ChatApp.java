/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */


package com.mycompany.chatapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChatApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Chat App Registration ===");

        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter your surname: ");
        String surname = scanner.nextLine();

        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        System.out.print("Enter your South African cell phone number: ");
        String cellPhone = scanner.nextLine();

        Login user = new Login(
                firstName, surname, username, password, cellPhone);

        String registrationResult = user.registerUser();
        System.out.println("\n" + registrationResult);

        if (!registrationResult.equals("Registration successful.")) {
            System.out.println("Please restart and register with valid details.");
            scanner.close();
            return;
        }

        boolean loggedIn = false;

        while (!loggedIn) {
            System.out.println("\n=== Login ===");

            System.out.print("Enter your username: ");
            String enteredUsername = scanner.nextLine();

            System.out.print("Enter your password: ");
            String enteredPassword = scanner.nextLine();

            loggedIn = user.loginUser(enteredUsername, enteredPassword);

            if (loggedIn) {
                System.out.println(user.returnLoginStatus(
                        enteredUsername, enteredPassword));
            } else {
                System.out.println(
                        "Username or password incorrect, please try again.");
            }
        }

        System.out.println("\nWelcome to QuickChat.");

        List<Message> sentMessages = new ArrayList<>();
        List<Message> storedMessages = new ArrayList<>();
        int choice = 0;

        while (choice != 3) {
            System.out.println("\n1) Send Messages");
            System.out.println("2) Show Recently Sent Messages");
            System.out.println("3) Quit");
            System.out.print("Choose an option: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter 1, 2 or 3.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("How many messages would you like to send? ");

                    int numberOfMessages;

                    try {
                        numberOfMessages = Integer.parseInt(scanner.nextLine());

                        if (numberOfMessages < 1) {
                            System.out.println("Enter at least one message.");
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid number.");
                        break;
                    }

                    for (int i = 0; i < numberOfMessages; i++) {
                        System.out.println("\nMessage " + (i + 1));

                        System.out.print("Enter recipient number (+27XXXXXXXXX): ");
                        String recipient = scanner.nextLine();

                        System.out.print("Enter your message: ");
                        String messageText = scanner.nextLine();

                        Message message = new Message(
                                i, recipient, messageText);

                        if (!message.checkMessageID()) {
                            System.out.println("Invalid message ID.");
                            continue;
                        }

                        if (!message.checkRecipientCell().startsWith(
                                "Cell phone number successfully")) {
                            System.out.println(message.checkRecipientCell());
                            continue;
                        }

                        if (!message.checkMessageLength().equals(
                                "Message ready to send.")) {
                            System.out.println(message.checkMessageLength());
                            continue;
                        }

                        System.out.println("\n1) Send Message");
                        System.out.println("2) Disregard Message");
                        System.out.println("3) Store Message");
                        System.out.print("Choose an action: ");

                        String action = scanner.nextLine();

                        switch (action) {
                            case "1":
                                sentMessages.add(message);
                                Message.incrementTotalMessages();
                                System.out.println("Message sent successfully.");
                                System.out.println("Message ID: "
                                        + message.getMessageID());
                                System.out.println("Message Hash: "
                                        + message.getMessageHash());
                                break;

                            case "2":
                                System.out.println("Message disregarded.");
                                break;

                            case "3":
    storedMessages.add(message);
    saveMessagesToJson(storedMessages);
    System.out.println("Message stored successfully.");
    break;
                            default:
                                System.out.println("Invalid action.");
                        }
                    }
                    break;

                case 2:
                    if (sentMessages.isEmpty()) {
                        System.out.println("No sent messages to display.");
                    } else {
                        for (Message message : sentMessages) {
                            System.out.println("\nMessage ID: "
                                    + message.getMessageID());
                            System.out.println("Recipient: "
                                    + message.getRecipient());
                            System.out.println("Message: "
                                    + message.getMessage());
                            System.out.println("Message Hash: "
                                    + message.getMessageHash());
                        }
                    }
                    break;

                case 3:
    System.out.println("Thank you for using QuickChat.");
    break;

    
                default:
                    System.out.println("Please select 1, 2 or 3.");
            }
        }

        System.out.println("Total messages sent: "
                + Message.returnTotalMessages());

        scanner.close();
    }

private static void saveMessagesToJson(List<Message> messages) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    try (FileWriter writer = new FileWriter("messages.json")) {
        gson.toJson(messages, writer);
        System.out.println("Messages saved to messages.json.");
    } catch (IOException e) {
        System.out.println("Error saving messages: " + e.getMessage());
    }
}
}

