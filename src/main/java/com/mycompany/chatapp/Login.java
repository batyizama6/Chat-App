/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapp;

/**
 *
 * @author batyi
 */
public class Login {

    private String firstName;
    private String surname;
    private String username;
    private String password;
    private String cellPhone;

    public Login(String firstName, String surname, String username, String password, String cellPhone) {
    this.firstName = firstName;
    this.surname = surname;
    this.username = username;
    this.password = password;
    this.cellPhone = cellPhone;
}
    public boolean checkUserName() {
    return username.contains("_") && username.length() <= 5;
}
   public boolean checkPasswordComplexity() {
    boolean hasCapitalLetter = false;
    boolean hasNumber = false;
    boolean hasSpecialCharacter = false;

    for (int i = 0; i < password.length(); i++) {
        char character = password.charAt(i);

        if (Character.isUpperCase(character)) {
            hasCapitalLetter = true;
        }

        if (Character.isDigit(character)) {
            hasNumber = true;
        }

        if (!Character.isLetterOrDigit(character)) {
            hasSpecialCharacter = true;
        }
    }

    return password.length() >= 8
            && hasCapitalLetter
            && hasNumber
            && hasSpecialCharacter;
}
   // Regular expression adapted from:
// Stack Overflow, "Validate South Africa Cell Phone Number",
// https://stackoverflow.com/questions/4058001/validate-south-africa-cell-phone-number
// Accessed: 18 September 2026.
   public boolean checkCellPhoneNumber() {
    String cellPhoneRegex = "^\\+27\\d{9}$";
    return cellPhone.matches(cellPhoneRegex);
}
   public String registerUser() {
    if (!checkUserName()) {
        return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters"
                + " in length.";
    }

    if (!checkPasswordComplexity()) {
        return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number,"
                + " and a special character.";
    }

    if (!checkCellPhoneNumber()) {
        return "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
    }

    return "Registration successful.";
}
   public boolean loginUser(String enteredUsername, String enteredPassword) {
    return username.equals(enteredUsername)
            && password.equals(enteredPassword);
}
   public String returnLoginStatus(String enteredUsername, String enteredPassword) {
    if (loginUser(enteredUsername, enteredPassword)) {
        return "Welcome " + firstName + ", " + surname + " it is great to see you again.";
    } else {
        return "Username or password incorrect, please try again.";
    }
}
}
    

