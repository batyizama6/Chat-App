/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */


package com.mycompany.chatapp;

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

            loggedIn = user.loginUser(
                    enteredUsername, enteredPassword);

            if (loggedIn) {
                System.out.println(user.returnLoginStatus(
                        enteredUsername, enteredPassword));
            } else {
                System.out.println(
                        "Username or password incorrect, please try again.");
            }
        }

        System.out.println("\nWelcome to QuickChat.");

        MessageManager manager = new MessageManager();

        int choice = 0;
        int messageNumber = 0;

        // Main menu: option 3 exits the application
        while (choice != 3) {

            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1) Send Messages");
            System.out.println("2) Show Recently Sent Messages");
            System.out.println("3) Quit");
            System.out.println("4) Stored Messages");
            System.out.print("Choose an option: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            switch (choice) {

                // OPTION 1: SEND MESSAGES
                case 1:

                    System.out.print(
                            "How many messages would you like to send? ");

                    int numberOfMessages;

                    try {
                        numberOfMessages = Integer.parseInt(
                                scanner.nextLine());

                        if (numberOfMessages < 1) {
                            System.out.println(
                                    "Enter at least one message.");
                            break;
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Enter a valid number.");
                        break;
                    }

                    for (int i = 0; i < numberOfMessages; i++) {

                        System.out.println("\nMessage " + (i + 1));

                        System.out.print(
                                "Enter recipient number (+27XXXXXXXXX): ");
                        String recipient = scanner.nextLine();

                        System.out.print("Enter your message: ");
                        String messageText = scanner.nextLine();

                        Message message = new Message(
                                messageNumber++,
                                user.getCellPhone(),
                                recipient,
                                messageText);

                        if (!message.checkMessageID()) {
                            System.out.println("Invalid message ID.");
                            continue;
                        }

                        if (!message.checkRecipientCell().startsWith(
                                "Cell phone number successfully")) {

                            System.out.println(
                                    message.checkRecipientCell());
                            continue;
                        }

                        if (!message.checkMessageLength().equals(
                                "Message ready to send.")) {

                            System.out.println(
                                    message.checkMessageLength());
                            continue;
                        }

                        System.out.println("\n1) Send Message");
                        System.out.println("2) Disregard Message");
                        System.out.println("3) Store Message");
                        System.out.print("Choose an action: ");

                        String action = scanner.nextLine();

                        switch (action) {

                            case "1":

                                manager.addSentMessage(message);

                                System.out.println(
                                        "Message sent successfully.");

                                System.out.println("Message ID: "
                                        + message.getMessageID());

                                System.out.println("Message Hash: "
                                        + message.getMessageHash());

                                break;

                            case "2":

                                manager.addDisregardedMessage(message);

                                System.out.println(
                                        "Message disregarded.");

                                break;

                            case "3":

                                manager.addStoredMessage(message);

                                System.out.println(
                                        "Message stored successfully.");

                                break;

                            default:
                                System.out.println("Invalid action.");
                        }
                    }

                    break;

                // OPTION 2: DISPLAY SENT MESSAGES
                case 2:

                    Message[] sentMessages = manager.getSentMessages();

                    if (sentMessages.length == 0) {

                        System.out.println(
                                "No sent messages to display.");

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

                // OPTION 3: QUIT
                case 3:

                    System.out.println(
                            "Thank you for using QuickChat.");

                    break;

                // OPTION 4: STORED MESSAGES
                case 4:

                    storedMessagesMenu(scanner, manager);

                    break;

                default:

                    System.out.println(
                            "Please select an option from 1 to 4.");
            }
        }

        System.out.println("Total messages sent: "
                + Message.returnTotalMessages());

        scanner.close();
    }

    // STORED MESSAGES SUBMENU
    public static void storedMessagesMenu(
            Scanner scanner, MessageManager manager) {

        int option = 0;

        while (option != 7) {

            System.out.println("\n=== STORED MESSAGES ===");
            System.out.println("1) Display senders and recipients");
            System.out.println("2) Display longest stored message");
            System.out.println("3) Search by message ID");
            System.out.println("4) Search by recipient");
            System.out.println("5) Delete message using hash");
            System.out.println("6) Display full report");
            System.out.println("7) Return to main menu");
            System.out.print("Choose an option: ");

            try {
                option = Integer.parseInt(scanner.nextLine());

            } catch (NumberFormatException e) {

                System.out.println("Enter a valid number.");
                continue;
            }

            switch (option) {

                // SUBMENU OPTION 1
                case 1:

                    System.out.println(
                            manager.displayStoredSendersAndRecipients());

                    break;

                // SUBMENU OPTION 2
                case 2:

                    Message longest =
                            manager.getLongestStoredMessage();

                    if (longest == null) {

                        System.out.println(
                                "No stored messages available.");

                    } else {

                        System.out.println("Longest stored message:");
                        System.out.println(longest.getMessage());
                    }

                    break;

                // SUBMENU OPTION 3
                case 3:

                    System.out.print("Enter message ID: ");
                    String messageID = scanner.nextLine();

                    Message found =
                            manager.searchMessageByID(messageID);

                    if (found == null) {

                        System.out.println("Message not found.");

                    } else {

                        System.out.println("Recipient: "
                                + found.getRecipient());

                        System.out.println("Message: "
                                + found.getMessage());
                    }

                    break;

                // SUBMENU OPTION 4
                case 4:

                    System.out.print("Enter recipient number: ");
                    String recipient = scanner.nextLine();

                    List<Message> results =
                            manager.searchMessagesByRecipient(recipient);

                    if (results.isEmpty()) {

                        System.out.println("No messages found.");

                    } else {

                        for (Message message : results) {

                            System.out.println("\nMessage: "
                                    + message.getMessage());
                        }
                    }

                    break;

                // SUBMENU OPTION 5
                case 5:

                    System.out.print("Enter message hash: ");
                    String hash = scanner.nextLine();

                    if (manager.deleteMessageByHash(hash)) {

                        System.out.println(
                                "Message successfully deleted.");

                    } else {

                        System.out.println("Message not found.");
                    }

                    break;

                // SUBMENU OPTION 6
                case 6:

                    System.out.println(manager.displayReport());

                    break;

                // SUBMENU OPTION 7
                case 7:

                    System.out.println("Returning to main menu.");

                    break;

                default:

                    System.out.println(
                            "Please select an option from 1 to 7.");
            }
        }
    }
}


