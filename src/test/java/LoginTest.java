
package com.mycompany.chatapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    @Test
public void testCheckUserName() {
    Login instance = new Login(
            "John",
            "Smith",
            "kyl_1",
            "Ch&&sec@ke99!",
            "+27838968976"
    );

    assertTrue(instance.checkUserName());
}

@Test
public void testCheckUserNameIncorrect() {
    Login instance = new Login(
            "John",
            "Smith",
            "kyle !!!!!!!",
            "Ch&&sec@ke99!",
            "+27838968976"
    );

    assertFalse(instance.checkUserName());
}
    @Test
public void testCheckPasswordComplexity() {
    Login instance = new Login(
            "John",
            "Smith",
            "kyl_1",
            "Ch&&sec@ke99!",
            "+27838968976"
    );

    assertTrue(instance.checkPasswordComplexity());
}

@Test
public void testCheckPasswordComplexityIncorrect() {
    Login instance = new Login(
            "John",
            "Smith",
            "kyl_1",
            "password",
            "+27838968976"
    );

    assertFalse(instance.checkPasswordComplexity());
}

    @Test
public void testCheckCellPhoneNumber() {
    Login instance = new Login(
            "John",
            "Smith",
            "kyl_1",
            "Ch&&sec@ke99!",
            "+27838968976"
    );

    assertTrue(instance.checkCellPhoneNumber());
}

@Test
public void testCheckCellPhoneNumberIncorrect() {
    Login instance = new Login(
            "John",
            "Smith",
            "kyl_1",
            "Ch&&sec@ke99!",
            "08966553"
    );

    assertFalse(instance.checkCellPhoneNumber());
}

    @Test
public void testLoginUserSuccessful() {
    Login instance = new Login(
            "John",
            "Smith",
            "kyl_1",
            "Ch&&sec@ke99!",
            "+27838968976"
    );

    assertTrue(instance.loginUser("kyl_1", "Ch&&sec@ke99!"));
}

@Test
public void testLoginUserFailed() {
    Login instance = new Login(
            "John",
            "Smith",
            "kyl_1",
            "Ch&&sec@ke99!",
            "+27838968976"
    );

    assertFalse(instance.loginUser("wrong_user", "wrong_password"));
}

    @Test
public void testReturnLoginStatusSuccessful() {
    Login instance = new Login(
            "John",
            "Smith",
            "kyl_1",
            "Ch&&sec@ke99!",
            "+27838968976"
    );

    assertEquals(
            "Welcome John, Smith it is great to see you again.",
            instance.returnLoginStatus("kyl_1", "Ch&&sec@ke99!")
    );
}

@Test
public void testReturnLoginStatusFailed() {
    Login instance = new Login(
            "John",
            "Smith",
            "kyl_1",
            "Ch&&sec@ke99!",
            "+27838968976"
    );

    assertEquals(
            "Username or password incorrect, please try again.",
            instance.returnLoginStatus("wrong_user", "wrong_password")
    );
}

@Test
public void testIncorrectUsernameMessage() {
    Login instance = new Login(
            "John",
            "Smith",
            "kyle !!!!!!!",
            "Ch&&sec@ke99!",
            "+27838968976"
    );

    assertEquals(
            "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.",
            instance.registerUser()
    );
}

@Test
public void testIncorrectPasswordMessage() {
    Login instance = new Login(
            "John",
            "Smith",
            "kyl_1",
            "password",
            "+27838968976"
    );

    assertEquals(
            "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.",
            instance.registerUser()
    );
}

@Test
public void testIncorrectCellPhoneMessage() {
    Login instance = new Login(
            "John",
            "Smith",
            "kyl_1",
            "Ch&&sec@ke99!",
            "08966553"
    );

    assertEquals(
            "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.",
            instance.registerUser()
    );
}

@Test
public void testSuccessfulRegistration() {
    Login instance = new Login(
            "John",
            "Smith",
            "kyl_1",
            "Ch&&sec@ke99!",
            "+27838968976"
    );

    assertEquals(
            "Registration successful.",
            instance.registerUser()
    );
}
}