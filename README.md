Chat App

This project is a console-based Java Chat Application developed as part of my Programming Portfolio of Evidence.

Registration and Login:

Part 1 of the application allows a user to register and log in using their personal details.

The application includes:

- Username validation
- Password complexity validation
- South African cellphone number validation
- User registration
- User login
- Login status messages

The username must contain an underscore and must not be more than five characters long. The password must contain at least eight characters, a capital letter,
a number, and a special character. The cellphone number is validated using a regular expression to ensure that it uses the South African international country code.

Testing:

JUnit 5 is used to test the application's registration, validation, and login functionality. Tests are included for both valid and invalid input.

Automated Testing

GitHub Actions and Maven are used to automatically build and test the project whenever changes are pushed to the repository.

Technologies Used:

- Java 21
- Apache Maven
- JUnit 5
- Git
- GitHub
- GitHub Actions
- Apache NetBeans
