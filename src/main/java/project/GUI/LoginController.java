package project.GUI;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import project.HashPassword;
import project.LoginMessage;
import project.model.UserRole;

public class LoginController {

    @FXML private Label resultLabel;
    @FXML private TextField loginField;
    @FXML private PasswordField passwordField;
    @FXML private Button submitButton;

    private LoginMessage answer = null;
    private UserRole role = null;

    @FXML
    private void submit() throws NoSuchAlgorithmException, IOException, ClassNotFoundException {

      //  Client.outputstream.writeObject(loginField.getText());
     //   Client.outputstream.flush();

        var hash = HashPassword.getHash(passwordField.getText());
     /*   Client.outputstream.writeInt(hash.length);
        Client.outputstream.flush();
        Client.outputstream.write(hash, 0, hash.length);
        Client.outputstream.flush();*/

        loginField.setDisable(true);
        passwordField.setDisable(true);
        submitButton.setDisable(true);

        new Thread(() -> {

           /* try {
                answer = (LoginMessage)Client.inputstream.readObject();
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();

                return;
            }*/

            if (answer == LoginMessage.Success) {

            /*    try {
                    role = (UserRole)Client.inputstream.readObject();
                } catch (ClassNotFoundException | IOException e1) {
                    e1.printStackTrace();

                    return;
                }*/


                Platform.runLater(() -> {
                    var fxml = role == UserRole.Admin ? "adminmenu" : "studentmenu";

                  /*  try {

                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                });

            }

            Platform.runLater(() -> {
                resultLabel.setText(messageToString());
                loginField.setDisable(false);
                passwordField.setDisable(false);
                submitButton.setDisable(false);
            });
        }).start();

    }

    private String messageToString() {
        return switch (answer) {
            case Success -> "Успешный вход";
            case WrongLogin -> "Неправильный логин";
            case WrongPassword -> "Неправильный пароль";
        };
    }
}

